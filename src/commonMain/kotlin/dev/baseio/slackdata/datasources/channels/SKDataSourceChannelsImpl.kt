package dev.baseio.slackdata.datasources.channels

import database.SlackChannel
import dev.baseio.database.SlackDB
import dev.baseio.slackdomain.CoroutineDispatcherProvider
import dev.baseio.slackdata.local.asFlow
import dev.baseio.slackdata.local.mapToList
import dev.baseio.slackdata.mapper.EntityMapper
import dev.baseio.slackdomain.datasources.local.channels.SKDataSourceChannels
import dev.baseio.slackdomain.model.channel.DomainLayerChannels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SKDataSourceChannelsImpl(private val slackChannelDao: SlackDB,
                               private val SKChannelMapper: EntityMapper<DomainLayerChannels.SKChannel, SlackChannel>,
                               private val coroutineMainDispatcherProvider: CoroutineDispatcherProvider
) : SKDataSourceChannels {
  override fun fetchChannelsOrByName(params: String?): Flow<List<DomainLayerChannels.SKChannel>> {
    val flow = params?.takeIf { it.isNotEmpty() }?.let {
      slackChannelDao.slackDBQueries.selectAllChannelsByName(params).asFlow()
        .mapToList(coroutineMainDispatcherProvider.default)
    } ?: run {
      slackChannelDao.slackDBQueries.selectAllChannels().asFlow().mapToList(coroutineMainDispatcherProvider.default)
    }
    return flow.map {
      it.map { message ->
        SKChannelMapper.mapToDomain(message)
      }
    }
  }

  override suspend fun channelCount(): Long {
    return slackChannelDao.slackDBQueries.countChannels().executeAsOne()
  }

  override fun fetchChannels(): Flow<List<DomainLayerChannels.SKChannel>> {
    return slackChannelDao.slackDBQueries.selectAllChannels().asFlow()
      .mapToList(coroutineMainDispatcherProvider.default)
      .map { list -> dbToDomList(list) }
  }

  private fun dbToDomList(list: List<SlackChannel>) =
    list.map { channel -> SKChannelMapper.mapToDomain(channel) }

  override suspend fun getChannel(uuid: String): DomainLayerChannels.SKChannel {
    val dbSlack = slackChannelDao.slackDBQueries.selectChannelById(uuid).executeAsOne()
    return SKChannelMapper.mapToDomain(dbSlack)
  }


}