package dev.baseio.slackdomain.datasources.users

import dev.baseio.slackdomain.model.users.DomainLayerUsers

interface SKDataSourceCreateUsers {
  suspend fun createUsers(users:List<DomainLayerUsers.SKUser>)
}