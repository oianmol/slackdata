package dev.baseio.slackdomain.usecases.chat


import dev.baseio.slackdomain.model.message.DomainLayerMessages
import dev.baseio.slackdomain.datasources.local.messages.SKDataSourceMessages
import dev.baseio.slackdomain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow

class UseCaseFetchMessages(private val SKDataSourceMessages: SKDataSourceMessages) :
  BaseUseCase<List<DomainLayerMessages.SKMessage>, String> {
  override fun performStreaming(userId: String): Flow<List<DomainLayerMessages.SKMessage>> {
    return SKDataSourceMessages.fetchMessages(userId)
  }
}
