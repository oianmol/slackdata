package dev.baseio.slackdata.datasources.workspaces

import dev.baseio.database.SlackDB
import dev.baseio.slackdomain.CoroutineDispatcherProvider
import dev.baseio.slackdomain.datasources.workspaces.SKDataSourceCreateWorkspaces
import dev.baseio.slackdomain.model.workspaces.DomainLayerWorkspaces
import kotlinx.coroutines.withContext

class SKDataSourceCreateWorkspacesImpl(
  private val slackDB: SlackDB,
  private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : SKDataSourceCreateWorkspaces {
  override suspend fun saveWorkspaces(list: List<DomainLayerWorkspaces.SKWorkspace>) {
    return withContext(coroutineDispatcherProvider.io) {
      list.map { skWorkspace ->
        slackDB.slackDBQueries.insertWorkspace(
          skWorkspace.uuid,
          skWorkspace.name,
          skWorkspace.domain,
          skWorkspace.picUrl
        )
      }
    }
  }
}