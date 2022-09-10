package dev.baseio.slackdomain.domain.repository

import dev.baseio.slackdomain.domain.model.message.DomainLayerMessages
import kotlinx.coroutines.flow.Flow

interface ChannelLastMessageRepository {
  fun fetchChannels(): Flow<List<DomainLayerMessages.LastMessage>>
}