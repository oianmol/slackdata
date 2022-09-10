package dev.baseio.slackdomain.domain.usecases.channels

import dev.baseio.slackdomain.domain.model.users.DomainLayerUsers
import dev.baseio.slackdomain.domain.repository.UsersRepository
import dev.baseio.slackdomain.domain.usecases.BaseUseCase

class UseCaseFetchUsers(private val usersRepository: UsersRepository) :
  BaseUseCase<List<DomainLayerUsers.SlackUser>, Int> {
  override suspend fun perform(params: Int): List<DomainLayerUsers.SlackUser> {
    return usersRepository.getUsers(params)
  }
}