package dev.baseio.slackdomain.domain.usecases.channels

import dev.baseio.slackdomain.domain.repository.ChannelsRepository
import dev.baseio.slackdomain.domain.usecases.BaseUseCase

class UseCaseFetchChannelCount(private val channelsRepository: ChannelsRepository) : BaseUseCase<Int,Unit> {
  override suspend fun perform(): Int {
    return channelsRepository.channelCount().toInt()
  }
}