package dev.baseio.slackserver.data

import com.squareup.sqldelight.Query
import database.SkChannel
import kotlinx.coroutines.flow.Flow

interface ChannelsDataSource {
  fun getChannels(): Flow<Query<SkChannel>>
}