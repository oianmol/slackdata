package dev.baseio.slackdomain.datasources.workspaces

import dev.baseio.slackdomain.model.workspaces.DomainLayerWorkspaces
import kotlinx.coroutines.flow.Flow

interface SKDataSourceWorkspaces {
  suspend fun workspacesCount(): Long
  suspend fun getWorkspace(uuid: String): DomainLayerWorkspaces.SKWorkspace?
  fun fetchWorkspaces(): Flow<List<DomainLayerWorkspaces.SKWorkspace>>
}