package dev.baseio.slackdata.datasources.messages

import database.SlackMessage
import dev.baseio.database.SlackDB
import dev.baseio.slackdomain.CoroutineDispatcherProvider
import dev.baseio.slackdata.local.asFlow
import dev.baseio.slackdata.local.mapToList
import dev.baseio.slackdata.mapper.EntityMapper
import dev.baseio.slackdomain.model.message.DomainLayerMessages
import dev.baseio.slackdomain.datasources.local.messages.SKDataSourceMessages
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class SlackSKDataSourceMessagesImpl constructor(
  private val slackMessageDao: SlackDB,
  private val entityMapper: EntityMapper<DomainLayerMessages.SKMessage, SlackMessage>,
  private val coroutineMainDispatcherProvider: CoroutineDispatcherProvider
) : SKDataSourceMessages {
  override fun fetchMessages(userId: String): Flow<List<DomainLayerMessages.SKMessage>> {
    return slackMessageDao.slackDBQueries.selectAllMessagesByUserId(userId)
      .asFlow()
      .flowOn(coroutineMainDispatcherProvider.io)
      .mapToList(coroutineMainDispatcherProvider.default)
      .map { slackMessages ->
        slackMessages
          .map { slackMessage -> entityMapper.mapToDomain(slackMessage) }
      }
      .flowOn(coroutineMainDispatcherProvider.default)
  }

  override suspend fun sendMessage(params: DomainLayerMessages.SKMessage): DomainLayerMessages.SKMessage {
    return withContext(coroutineMainDispatcherProvider.io) {
      slackMessageDao.slackDBQueries.insertMessage(
        params.uuid,
        params.channelId,
        params.message,
        params.userId,
        params.createdBy,
        params.createdDate,
        params.modifiedDate
      )
      params
    }
  }
}