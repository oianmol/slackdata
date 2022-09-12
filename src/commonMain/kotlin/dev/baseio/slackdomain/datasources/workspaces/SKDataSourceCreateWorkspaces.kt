package dev.baseio.slackdomain.datasources.workspaces

import dev.baseio.slackdomain.model.workspaces.DomainLayerWorkspaces

interface SKDataSourceCreateWorkspaces {
  suspend fun saveWorkspaces(list: List<DomainLayerWorkspaces.SKWorkspace>)
}