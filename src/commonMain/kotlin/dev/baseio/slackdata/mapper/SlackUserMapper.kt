package dev.baseio.slackdata.mapper

import database.SlackUser
import dev.baseio.slackdomain.model.users.DomainLayerUsers

class SlackUserMapper : EntityMapper<DomainLayerUsers.SKUser, SlackUser> {
  override fun mapToDomain(entity: SlackUser): DomainLayerUsers.SKUser {
    return entity.toSkUser()
  }

  override fun mapToData(model: DomainLayerUsers.SKUser): SlackUser {
    return model.toDBSlackUser()
  }
}

fun DomainLayerUsers.SKUser.toDBSlackUser(): SlackUser {
  return SlackUser(
    uuid, gender, name, location, email, username,
    this.userSince,
    phone,
    avatarUrl
  )
}

fun SlackUser.toSkUser(): DomainLayerUsers.SKUser {
  return DomainLayerUsers.SKUser(
    uuid, gender, name, location, email, username,
    userSince,
    phone,
    avatarUrl
  )
}
