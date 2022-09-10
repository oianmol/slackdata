package dev.baseio.slackdomain.domain.usecases.channels


import dev.baseio.slackdomain.domain.model.channel.DomainLayerChannels
import dev.baseio.slackdomain.domain.model.message.DomainLayerMessages
import dev.baseio.slackdomain.domain.repository.ChannelLastMessageRepository
import dev.baseio.slackdomain.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow

class UseCaseFetchChannelsWithLastMessage(private val channelLastMessageRepository: ChannelLastMessageRepository) :
  BaseUseCase<List<DomainLayerMessages.LastMessage>, Unit> {

  override fun performStreaming(params: Unit?): Flow<List<DomainLayerMessages.LastMessage>> {
    return channelLastMessageRepository.fetchChannels()
  }

}