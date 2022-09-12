package dev.baseio.slackdata.datasources.channels

import database.SlackChannel
import database.SlackMessage
import dev.baseio.database.SlackDB
import dev.baseio.slackdomain.CoroutineDispatcherProvider
import dev.baseio.slackdata.local.asFlow
import dev.baseio.slackdata.local.mapToList
import dev.baseio.slackdata.mapper.EntityMapper
import dev.baseio.slackdomain.model.channel.DomainLayerChannels
import dev.baseio.slackdomain.model.message.DomainLayerMessages
import dev.baseio.slackdomain.datasources.channels.SKDataSourceChannelLastMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SlackSKDataSourceChannelLastMessage constructor(
  private val slackChannelDao: SlackDB,
  private val messagesMapper: EntityMapper<DomainLayerMessages.SKMessage, SlackMessage>,
  private val SKChannelMapper: EntityMapper<DomainLayerChannels.SKChannel, SlackChannel>,
  private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : SKDataSourceChannelLastMessage {
  override fun fetchChannels(): Flow<List<DomainLayerMessages.SKLastMessage>> {
    val chatPager = slackChannelDao.slackDBQueries.selectChannelsWithLastMessage()
      .asFlow()
      .mapToList(coroutineDispatcherProvider.default)
    return chatPager.map {
      it.map { channelsWithLastMessage ->
        val channel = slackChannelDao.slackDBQueries.selectChannelById(channelsWithLastMessage.channelId!!).executeAsOne()
        val message =
          SlackMessage(channelsWithLastMessage.uid, channelsWithLastMessage.channelId, channelsWithLastMessage.message, channelsWithLastMessage.fromUser, channelsWithLastMessage.createdBy, channelsWithLastMessage.createdDate, channelsWithLastMessage.modifiedDate)
        DomainLayerMessages.SKLastMessage(
          SKChannelMapper.mapToDomain(channel),
          messagesMapper.mapToDomain(message)
        )
      }
    }
  }
}