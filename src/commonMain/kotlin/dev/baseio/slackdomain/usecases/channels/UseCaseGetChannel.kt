package dev.baseio.slackdomain.usecases.channels

import dev.baseio.slackdomain.datasources.local.channels.SKDataSourceChannels
import dev.baseio.slackdomain.model.channel.DomainLayerChannels
import dev.baseio.slackdomain.usecases.BaseUseCase

class UseCaseGetChannel(private val skDataSourceChannels: SKDataSourceChannels) :
  BaseUseCase<DomainLayerChannels.SKChannel, String> {
  override suspend fun perform(params: String): DomainLayerChannels.SKChannel? {
    return skDataSourceChannels.getChannel(uuid = params)
  }
}