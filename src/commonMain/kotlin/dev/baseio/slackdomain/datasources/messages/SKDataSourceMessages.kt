package dev.baseio.slackdomain.datasources.messages


import dev.baseio.slackdomain.model.message.DomainLayerMessages
import kotlinx.coroutines.flow.Flow

interface SKDataSourceMessages {
  fun fetchMessages(userId: String): Flow<List<DomainLayerMessages.SKMessage>>
  suspend fun sendMessage(params: DomainLayerMessages.SKMessage): DomainLayerMessages.SKMessage
}