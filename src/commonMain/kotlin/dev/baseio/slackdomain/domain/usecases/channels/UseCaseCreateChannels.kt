package dev.baseio.slackdomain.domain.usecases.channels

import dev.baseio.slackdomain.domain.model.users.DomainLayerUsers
import dev.baseio.slackdomain.domain.repository.ChannelsRepository
import dev.baseio.slackdomain.domain.usecases.BaseUseCase

class UseCaseCreateChannels(private val channelsRepository: ChannelsRepository) :
  BaseUseCase<Unit, List<DomainLayerUsers.SlackUser>> {
  override suspend fun perform(params: List<DomainLayerUsers.SlackUser>) {
    return channelsRepository.saveOneToOneChannels(params)
  }
}