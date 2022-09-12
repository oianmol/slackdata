package dev.baseio.slackdata.datasources.workspaces

import database.SlackWorkspaces
import dev.baseio.database.SlackDB
import dev.baseio.slackdata.local.asFlow
import dev.baseio.slackdata.local.mapToList
import dev.baseio.slackdata.mapper.EntityMapper
import dev.baseio.slackdomain.CoroutineDispatcherProvider
import dev.baseio.slackdomain.datasources.workspaces.SKDataSourceWorkspaces
import dev.baseio.slackdomain.model.workspaces.DomainLayerWorkspaces
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SKDataSourceWorkspacesImpl(
  private val slackDB: SlackDB,
  private val entityMapper: EntityMapper<DomainLayerWorkspaces.SKWorkspace, SlackWorkspaces>,
  private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : SKDataSourceWorkspaces {
  override suspend fun workspacesCount(): Long {
    return withContext(coroutineDispatcherProvider.io) {
      slackDB.slackDBQueries.countWorkspaces().executeAsOneOrNull() ?: 0
    }
  }

  override suspend fun getWorkspace(uuid: String): DomainLayerWorkspaces.SKWorkspace? {
    return withContext(coroutineDispatcherProvider.io) {
      slackDB.slackDBQueries.selectWorkspaceById(uuid).executeAsOneOrNull()?.let { slackWorkspace ->
        entityMapper.mapToDomain(slackWorkspace)
      }
    }
  }

  override fun fetchWorkspaces(): Flow<List<DomainLayerWorkspaces.SKWorkspace>> {
    return slackDB.slackDBQueries.selectAllWorkspaces().asFlow()
      .mapToList(coroutineDispatcherProvider.default)
      .map { list -> list.map { entityMapper.mapToDomain(it) } }
  }
}