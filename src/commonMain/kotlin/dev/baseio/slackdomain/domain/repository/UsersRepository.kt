package dev.baseio.slackdomain.domain.repository

import dev.baseio.slackdomain.domain.model.users.DomainLayerUsers

interface UsersRepository {
  suspend fun getUsers(count: Int): List<DomainLayerUsers.SlackUser>
}