package dev.baseio.slackdomain.datasources.channels


import dev.baseio.slackdomain.model.channel.DomainLayerChannels
import dev.baseio.slackdomain.model.users.DomainLayerUsers

interface SKDataSourceCreateChannel {
  suspend fun saveChannel(params: DomainLayerChannels.SKChannel): DomainLayerChannels.SKChannel?
  suspend fun saveOneToOneChannels(params: List<DomainLayerUsers.SKUser>)
  suspend fun saveChannels(channels: MutableList<DomainLayerChannels.SKChannel>)
}

