package dev.baseio.slackdomain.domain.repository


import dev.baseio.slackdomain.domain.model.message.DomainLayerMessages
import kotlinx.coroutines.flow.Flow

interface MessagesRepository {
  fun fetchMessages(params: String?): Flow<List<DomainLayerMessages.SlackMessage>>
  suspend fun sendMessage(params: DomainLayerMessages.SlackMessage): DomainLayerMessages.SlackMessage
}