package dev.baseio.slackdomain.model.message

import dev.baseio.slackdomain.model.channel.DomainLayerChannels


interface DomainLayerMessages {
  data class SKMessage(
    val uuid: String,
    val channelId: String,
    val message: String,
    val userId: String,
    val createdBy: String,
    val createdDate: Long,
    val modifiedDate: Long,
  )

  data class SKLastMessage(
    val channel: DomainLayerChannels.SKChannel,
    val message: SKMessage
  )
}