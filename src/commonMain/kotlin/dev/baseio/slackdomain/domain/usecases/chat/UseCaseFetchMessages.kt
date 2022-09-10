package dev.baseio.slackdomain.domain.usecases.chat


import dev.baseio.slackdomain.domain.model.message.DomainLayerMessages
import dev.baseio.slackdomain.domain.repository.MessagesRepository
import dev.baseio.slackdomain.domain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow

class UseCaseFetchMessages(private val messagesRepository: MessagesRepository) :
  BaseUseCase<List<DomainLayerMessages.SlackMessage>, String> {
  override fun performStreaming(params: String?): Flow<List<DomainLayerMessages.SlackMessage>> {
    return messagesRepository.fetchMessages(params)
  }
}
