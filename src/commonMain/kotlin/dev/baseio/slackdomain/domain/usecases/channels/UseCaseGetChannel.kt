package dev.baseio.slackdomain.domain.usecases.channels

import dev.baseio.slackdomain.domain.model.channel.DomainLayerChannels
import dev.baseio.slackdomain.domain.repository.ChannelsRepository
import dev.baseio.slackdomain.domain.usecases.BaseUseCase

class UseCaseGetChannel(private val channelsRepository: ChannelsRepository) :
  BaseUseCase<DomainLayerChannels.SlackChannel, String> {
  override suspend fun perform(params: String): DomainLayerChannels.SlackChannel? {
    return channelsRepository.getChannel(uuid = params)
  }
}