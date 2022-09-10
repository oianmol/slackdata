package dev.baseio.slackdomain.domain.usecases.chat

import dev.baseio.slackdomain.domain.model.message.DomainLayerMessages
import dev.baseio.slackdomain.domain.repository.MessagesRepository
import dev.baseio.slackdomain.domain.usecases.BaseUseCase

class UseCaseSendMessage(private val messagesRepository: MessagesRepository) :BaseUseCase<DomainLayerMessages.SlackMessage, DomainLayerMessages.SlackMessage>{
  override suspend fun perform(params: DomainLayerMessages.SlackMessage): DomainLayerMessages.SlackMessage {
    return messagesRepository.sendMessage(params)
  }
}
