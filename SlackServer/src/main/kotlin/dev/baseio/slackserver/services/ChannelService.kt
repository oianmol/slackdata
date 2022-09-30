package dev.baseio.slackserver.services

import database.SkChannel
import dev.baseio.slackdata.protos.ChannelsServiceGrpcKt
import dev.baseio.slackdata.protos.Empty
import dev.baseio.slackdata.protos.SKChannel
import dev.baseio.slackdata.protos.SKChannels
import dev.baseio.slackdata.protos.SKWorkspaces
import dev.baseio.slackserver.data.ChannelsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class ChannelService(
  coroutineContext: CoroutineContext = Dispatchers.IO,
  private val channelsDataSource: ChannelsDataSource
) :
  ChannelsServiceGrpcKt.ChannelsServiceCoroutineImplBase(coroutineContext) {
  override fun getChannels(request: Empty): Flow<SKChannels> {
    return channelsDataSource.getChannels().map {
      val channels = it.executeAsList().map { dbChannel ->
        dbChannel.toGRPC()
      }
      SKChannels.newBuilder()
        .addAllChannels(channels)
        .build()
    }
  }
}

private fun SkChannel.toGRPC(): SKChannel {
  return SKChannel.newBuilder()
    .setUuid(this.uuid)
    .setAvatarUrl(this.avatarUrl)
    .setName(this.name)
    .setCreatedDate(this.createdDate.toLong())
    .setIsMuted(this.isMuted == 1)
    .setIsPrivate(this.isPrivate == 1)
    .setIsStarred(this.isStarred == 1)
    .setIsOneToOne(this.isOneToOne == 1)
    .setIsShareOutSide(this.isShareOutSide == 1)
    .setWorkspaceId(this.workspaceId)
    .setModifiedDate(this.modifiedDate.toLong())
    .build()
}
