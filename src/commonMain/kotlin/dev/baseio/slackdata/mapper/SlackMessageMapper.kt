package dev.baseio.slackdata.mapper

import database.SlackMessage
import dev.baseio.slackdomain.model.message.DomainLayerMessages
class SlackMessageMapper constructor() : EntityMapper<DomainLayerMessages.SKMessage, SlackMessage> {
  override fun mapToDomain(entity: SlackMessage): DomainLayerMessages.SKMessage {
    return DomainLayerMessages.SKMessage(
      entity.uid,
      entity.channelId!!,
      entity.message!!,
      entity.uid,
      entity.createdBy!!,
      entity.createdDate!!,
      entity.modifiedDate!!
    )
  }

  override fun mapToData(model: DomainLayerMessages.SKMessage): SlackMessage {
    return SlackMessage(
      model.uuid,
      model.channelId,
      model.message,
      model.userId,
      model.createdBy,
      model.createdDate,
      model.modifiedDate,
    )
  }
}