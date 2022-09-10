package dev.baseio.slackdomain.domain.usecases.channels

import dev.baseio.slackdomain.domain.model.channel.DomainLayerChannels
import dev.baseio.slackdomain.domain.repository.ChannelsRepository
import dev.baseio.slackdomain.domain.usecases.BaseUseCase

class UseCaseCreateChannel(private val channelsRepository: ChannelsRepository) : BaseUseCase<DomainLayerChannels.SlackChannel, DomainLayerChannels.SlackChannel> {
  override suspend fun perform(params: DomainLayerChannels.SlackChannel): DomainLayerChannels.SlackChannel? {
    return channelsRepository.saveChannel(params)
  }
}