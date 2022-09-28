package dev.baseio.slackserver.services

import dev.baseio.slackdata.protos.*
import dev.baseio.slackserver.data.ChannelsDataSource
import dev.baseio.slackserver.data.WorkspaceDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class SlackService(
  coroutineContext: CoroutineContext = Dispatchers.IO,
  private val workspaceDataSource: WorkspaceDataSource,
  private val channelsDataSource: ChannelsDataSource
) :
  HelloServiceGrpcKt.HelloServiceCoroutineImplBase(coroutineContext) {

  override fun getWorkspaces(request: Empty): Flow<SKWorkspaces> {
    return workspaceDataSource.getWorkspaces().map {
      val workspaces = it.executeAsList().map { dbWorkspace ->
        sKWorkspace {
          // todo move this to extension function/mapper
          this.uuid = dbWorkspace.uuid
          this.domain = dbWorkspace.domain
          this.name = dbWorkspace.name
          this.picUrl = dbWorkspace.picUrl ?: ""// todo mark as optional
          this.lastSelected = false
        }
      }
      SKWorkspaces.newBuilder()
        .addAllWorkspaces(workspaces)
        .build()
    }
  }
}