package dev.baseio.slackdomain.datasources.local.channels

import dev.baseio.slackdomain.model.channel.DomainLayerChannels
import kotlinx.coroutines.flow.Flow

interface SKDataSourceChannels {
  suspend fun channelCount(): Long
  suspend fun getChannel(uuid: String): DomainLayerChannels.SKChannel?
  fun fetchChannels(): Flow<List<DomainLayerChannels.SKChannel>>
  fun fetchChannelsOrByName(params: String?): Flow<List<DomainLayerChannels.SKChannel>>
}