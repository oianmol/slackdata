package dev.baseio.slackdata.repository

import database.SlackChannel
import database.SlackMessage
import dev.baseio.database.SlackDB
import dev.baseio.slackdata.injection.dispatcher.CoroutineDispatcherProvider
import dev.baseio.slackdata.local.asFlow
import dev.baseio.slackdata.local.mapToList
import dev.baseio.slackdata.local.mapToOne
import dev.baseio.slackdata.mapper.EntityMapper
import dev.baseio.slackdomain.domain.model.channel.DomainLayerChannels
import dev.baseio.slackdomain.domain.model.message.DomainLayerMessages
import dev.baseio.slackdomain.domain.repository.ChannelLastMessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SlackChannelLastMessageRepository constructor(
  private val slackChannelDao: SlackDB,
  private val messagesMapper: EntityMapper<DomainLayerMessages.SlackMessage, SlackMessage>,
  private val slackChannelMapper: EntityMapper<DomainLayerChannels.SlackChannel, SlackChannel>,
  private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ChannelLastMessageRepository {
  override fun fetchChannels(): Flow<List<DomainLayerMessages.LastMessage>> {
    val chatPager = slackChannelDao.slackDBQueries.selectChannelsWithLastMessage()
      .asFlow()
      .mapToList(coroutineDispatcherProvider.default)
    return chatPager.map {
      it.map {
        val channel = slackChannelDao.slackDBQueries.selectChannelById(it.channelId!!).executeAsOne()
        val message =
          SlackMessage(it.uid, it.channelId, it.message, it.fromUser, it.createdBy, it.createdDate, it.modifiedDate)
        DomainLayerMessages.LastMessage(
          slackChannelMapper.mapToDomain(channel),
          messagesMapper.mapToDomain(message)
        )
      }
    }
  }
}