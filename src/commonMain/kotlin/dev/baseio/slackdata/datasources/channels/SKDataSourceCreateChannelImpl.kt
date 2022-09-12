package dev.baseio.slackdata.datasources.channels

import database.SlackChannel
import dev.baseio.database.SlackDB
import dev.baseio.slackdomain.CoroutineDispatcherProvider
import dev.baseio.slackdata.mapper.EntityMapper
import dev.baseio.slackdomain.model.channel.DomainLayerChannels
import dev.baseio.slackdomain.model.users.DomainLayerUsers
import dev.baseio.slackdomain.datasources.channels.SKDataSourceCreateChannel
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class SKDataSourceCreateChannelImpl(
  private val slackChannelDao: SlackDB,
  private val SKChannelMapper: EntityMapper<DomainLayerChannels.SKChannel, SlackChannel>,
  private val coroutineMainDispatcherProvider: CoroutineDispatcherProvider
) :
  SKDataSourceCreateChannel {

  override suspend fun saveChannels(channels: MutableList<DomainLayerChannels.SKChannel>) {
    channels.forEach { skChannel ->
      saveChannel(skChannel)
    }
  }

  override suspend fun saveOneToOneChannels(params: List<DomainLayerUsers.SKUser>) {
    return withContext(coroutineMainDispatcherProvider.io) {
      params.forEach {
        slackChannelDao.slackDBQueries.insertChannel(
          it.username,
          it.name,
          it.email,
          Clock.System.now().toEpochMilliseconds(),
          Clock.System.now().toEpochMilliseconds(),
          0L,
          0L,
          1L,
          0L,
          it.avatarUrl,
          1L
        )
      }
    }
  }

  override suspend fun saveChannel(params: DomainLayerChannels.SKChannel): DomainLayerChannels.SKChannel? {
    return withContext(coroutineMainDispatcherProvider.io) {
      slackChannelDao.slackDBQueries.insertChannel(
        params.uuid!!,
        params.name,
        "someelamil@sdffd.com",
        params.createdDate,
        params.modifiedDate,
        params.isMuted.let { if (it == true) 1L else 0L },
        params.isStarred.let { if (it == true) 1L else 0L },
        params.isPrivate.let { if (it == true) 1L else 0L },
        params.isShareOutSide.let { if (it == true) 1L else 0L },
        params.avatarUrl,
        params.isOneToOne.let { if (it == true) 1L else 0L }
      )
      slackChannelDao.slackDBQueries.selectChannelById(params.uuid).executeAsOne()
        .let { SKChannelMapper.mapToDomain(it) }
    }
  }
}
