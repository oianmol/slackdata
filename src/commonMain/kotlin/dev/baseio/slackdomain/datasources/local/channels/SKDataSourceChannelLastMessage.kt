package dev.baseio.slackdomain.datasources.local.channels

import dev.baseio.slackdomain.model.message.DomainLayerMessages
import kotlinx.coroutines.flow.Flow

interface SKDataSourceChannelLastMessage {
  fun fetchChannels(): Flow<List<DomainLayerMessages.SKLastMessage>>
}