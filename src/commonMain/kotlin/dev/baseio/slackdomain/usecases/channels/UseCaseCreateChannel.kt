package dev.baseio.slackdomain.usecases.channels

import dev.baseio.slackdomain.model.channel.DomainLayerChannels
import dev.baseio.slackdomain.datasources.channels.SKDataSourceCreateChannel
import dev.baseio.slackdomain.usecases.BaseUseCase

class UseCaseCreateChannel(private val SKDataSourceCreateChannel: SKDataSourceCreateChannel) :
  BaseUseCase<DomainLayerChannels.SKChannel, DomainLayerChannels.SKChannel> {
  override suspend fun perform(params: DomainLayerChannels.SKChannel): DomainLayerChannels.SKChannel? {
    return SKDataSourceCreateChannel.saveChannel(params)
  }
}