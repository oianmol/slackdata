package dev.baseio.slackserver.data.impl

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import database.SkChannel
import dev.baseio.SlackCloneDB
import dev.baseio.slackserver.data.ChannelsDataSource
import kotlinx.coroutines.flow.Flow

class ChannelsDataSourceImpl(private val slackCloneDB: SlackCloneDB) : ChannelsDataSource {
  override fun getChannels(): Flow<Query<SkChannel>> {
    return slackCloneDB.slackschemaQueries
      .selectAllChannels()
      .asFlow()
  }
}