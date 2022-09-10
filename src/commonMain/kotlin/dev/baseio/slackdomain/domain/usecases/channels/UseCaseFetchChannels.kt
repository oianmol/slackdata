package dev.baseio.slackdomain.domain.usecases.channels

import dev.baseio.slackdomain.domain.model.channel.DomainLayerChannels
import dev.baseio.slackdomain.domain.repository.ChannelsRepository
import dev.baseio.slackdomain.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow

class UseCaseFetchChannels(
  private val channelsRepository: ChannelsRepository,
) : BaseUseCase<List<DomainLayerChannels.SlackChannel>, Unit> {

  override fun performStreaming(params: Unit?): Flow<List<DomainLayerChannels.SlackChannel>> {
    return channelsRepository.fetchChannels()
  }

}
