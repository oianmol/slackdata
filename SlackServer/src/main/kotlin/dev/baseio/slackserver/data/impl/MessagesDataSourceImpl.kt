package dev.baseio.slackserver.data.impl

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import database.SkMessage
import dev.baseio.SlackCloneDB
import dev.baseio.slackserver.data.MessagesDataSource
import kotlinx.coroutines.flow.Flow

class MessagesDataSourceImpl(private val slackDB: SlackCloneDB) : MessagesDataSource {
  override fun saveMessage(request: SkMessage): SkMessage {
    TODO("Not yet implemented")
  }

  override fun getMessages(workspaceId:String,channelId:String): Flow<Query<SkMessage>> {
    return slackDB.slackschemaQueries
      .selectAllMessages(workspaceId = workspaceId,channelId=channelId)
      .asFlow()
  }
}