package dev.baseio.slackdata.mapper

import database.SlackChannel
import dev.baseio.slackdomain.model.channel.DomainLayerChannels

class SlackChannelMapper constructor() :
  EntityMapper<DomainLayerChannels.SKChannel, SlackChannel> {
  override fun mapToDomain(entity: SlackChannel): DomainLayerChannels.SKChannel {
    return DomainLayerChannels.SKChannel(
      isStarred = entity.isStarred == 1L,
      isPrivate = entity.isPrivate == 1L,
      uuid = entity.uid,
      name = entity.name,
      isMuted = entity.isMuted == 1L,
      createdDate = entity.createdDate,
      modifiedDate = entity.modifiedDate,
      isShareOutSide = entity.isShareOutSide == 1L,
      isOneToOne = entity.isOneToOne == 1L,
      avatarUrl = entity.photo,
      workspaceId = entity.workspaceId
    )
  }

  override fun mapToData(model: DomainLayerChannels.SKChannel): SlackChannel {
    return SlackChannel(
      uid = model.uuid ?: model.name!!,
      name = model.name,
      isStarred = model.isStarred.let { if (it == true) 1L else 0L },
      createdDate = model.createdDate,
      modifiedDate = model.modifiedDate,
      isPrivate = model.isPrivate.let { if (it == true) 1L else 0L },
      isShareOutSide = model.isShareOutSide.let { if (it == true) 1L else 0L },
      isOneToOne = model.isOneToOne.let { if (it == true) 1L else 0L },
      photo = model.avatarUrl, email = "", isMuted = model.isMuted.let { if (it == true) 1L else 0L },
      workspaceId = model.workspaceId
    )
  }
}