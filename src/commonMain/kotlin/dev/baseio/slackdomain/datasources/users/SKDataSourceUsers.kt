package dev.baseio.slackdomain.datasources.users

import dev.baseio.slackdomain.model.users.DomainLayerUsers
import dev.baseio.slackdomain.model.workspaces.DomainLayerWorkspaces

interface SKDataSourceUsers {
  suspend fun getUsers(workspace: DomainLayerWorkspaces.SKWorkspace): List<DomainLayerUsers.SKUser>
}