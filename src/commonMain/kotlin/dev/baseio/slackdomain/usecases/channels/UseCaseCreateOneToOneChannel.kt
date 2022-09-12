package dev.baseio.slackdomain.usecases.channels

import dev.baseio.slackdomain.model.users.DomainLayerUsers
import dev.baseio.slackdomain.datasources.channels.SKDataSourceCreateChannel
import dev.baseio.slackdomain.usecases.BaseUseCase

class UseCaseCreateOneToOneChannel(private val SKDataSourceCreateChannel: SKDataSourceCreateChannel) :
  BaseUseCase<Unit, List<DomainLayerUsers.SKUser>> {
  override suspend fun perform(params: List<DomainLayerUsers.SKUser>) {
    return SKDataSourceCreateChannel.saveOneToOneChannels(params)
  }
}