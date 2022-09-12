package dev.baseio.slackdomain.usecases.channels

import dev.baseio.slackdomain.datasources.channels.SKDataSourceChannels
import dev.baseio.slackdomain.usecases.BaseUseCase

class UseCaseFetchChannelCount(private val skDataSourceChannels: SKDataSourceChannels) :
  BaseUseCase<Int, Unit> {
  override suspend fun perform(): Int {
    return skDataSourceChannels.channelCount().toInt()
  }
}