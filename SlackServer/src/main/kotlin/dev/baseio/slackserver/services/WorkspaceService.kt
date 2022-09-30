package dev.baseio.slackserver.services

import database.SkWorkspace
import dev.baseio.slackdata.protos.*
import dev.baseio.slackserver.data.WorkspaceDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class WorkspaceService(
  coroutineContext: CoroutineContext = Dispatchers.IO,
  private val workspaceDataSource: WorkspaceDataSource
) :
  WorkspaceServiceGrpcKt.WorkspaceServiceCoroutineImplBase(coroutineContext) {

  override suspend fun saveWorkspace(request: SKWorkspace): SKWorkspace {
    return workspaceDataSource
      .saveWorkspace(request.toDBWorkspace())
      .toGRPC()
  }

  override fun getWorkspaces(request: Empty): Flow<SKWorkspaces> {
    return workspaceDataSource.getWorkspaces().map {
      val workspaces = it.executeAsList().map { dbWorkspace ->
        dbWorkspace.toGRPC()
      }
      SKWorkspaces.newBuilder()
        .addAllWorkspaces(workspaces)
        .build()
    }
  }
}

private fun SkWorkspace.toGRPC(): SKWorkspace {
  val dbWorkspace = this
  return SKWorkspace.newBuilder()
    .setUuid(dbWorkspace.uuid)
    .setName(dbWorkspace.name)
    .setDomain(dbWorkspace.domain)
    .setLastSelected(dbWorkspace.lastSelected == 1)
    .setPicUrl(dbWorkspace.picUrl)
    .build()
}

private fun SKWorkspace.toDBWorkspace(): SkWorkspace {
  return SkWorkspace(
    this.uuid,
    this.name,
    this.domain,
    this.picUrl,
    if (this.lastSelected) 1 else 0
  )
}
