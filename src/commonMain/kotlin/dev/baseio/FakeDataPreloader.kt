package dev.baseio

import database.SlackChannel
import dev.baseio.slackdata.mapper.EntityMapper
import dev.baseio.slackdomain.CoroutineDispatcherProvider
import dev.baseio.slackdomain.datasources.local.channels.SKDataSourceCreateChannels
import dev.baseio.slackdomain.datasources.local.users.SKDataSourceCreateUsers
import dev.baseio.slackdomain.datasources.local.workspaces.SKDataSourceCreateWorkspaces
import dev.baseio.slackdomain.model.channel.DomainLayerChannels
import dev.baseio.slackdomain.model.users.DomainLayerUsers
import dev.baseio.slackdomain.model.workspaces.DomainLayerWorkspaces
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class FakeDataPreloader(
  private val skDataSourceCreateWorkspaces: SKDataSourceCreateWorkspaces,
  private val skDataSourceCreateChannels: SKDataSourceCreateChannels,
  private val skDataSourceCreateUsers: SKDataSourceCreateUsers,
  private val slackUserChannelMapper: EntityMapper<DomainLayerUsers.SKUser, SlackChannel>,
  private val channelMapper: EntityMapper<DomainLayerChannels.SKChannel, SlackChannel>,
  private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) {
  suspend fun preload() {
    withContext(coroutineDispatcherProvider.io) {
      val workSpaces = getWorkSpaces()
      skDataSourceCreateWorkspaces.saveWorkspaces(workSpaces)

      val users = buildUsers(workSpaces)
      skDataSourceCreateUsers.saveUsers(users)

      val channels = buildChannelsForWorkspaces(workSpaces)
      // this creates one to one groups for users
      val channelsOneToOne = users.map { skUser ->
        slackUserChannelMapper.mapToData(skUser)
      }.map {
        channelMapper.mapToDomain(it)
      }
      channels.addAll(channelsOneToOne)

      skDataSourceCreateChannels.saveChannels(channels)
    }
  }

  private fun buildChannelsForWorkspaces(
    workSpaces: MutableList<DomainLayerWorkspaces.SKWorkspace>
  ): MutableList<DomainLayerChannels.SKChannel> {
    val channels = mutableListOf<DomainLayerChannels.SKChannel>()
    workSpaces.forEach { skWorkspace ->
      // for every workspace create 50 channels
      repeat(50) {
        channels.add(
          DomainLayerChannels.SKChannel(
            workspaceId = skWorkspace.uuid,
            uuid = "${it}_${skWorkspace.uuid}",
            name = "Channel #$it",
            createdDate = Clock.System.now().toEpochMilliseconds(),
            modifiedDate = Clock.System.now().toEpochMilliseconds(),
            isMuted = false,
            isPrivate = false,
            isStarred = false,
            isShareOutSide = false,
            isOneToOne = false,
            avatarUrl = null
          )
        )
      }


    }
    return channels
  }

  private fun buildUsers(
    workSpaces: MutableList<DomainLayerWorkspaces.SKWorkspace>,
  ): MutableList<DomainLayerUsers.SKUser> {
    val users = mutableListOf<DomainLayerUsers.SKUser>()
    workSpaces.forEach { skWorkspace ->
      users.addAll(getUsers(skWorkspace))
    }
    return users
  }


  private fun getUsers(skWorkspace: DomainLayerWorkspaces.SKWorkspace): MutableList<DomainLayerUsers.SKUser> {
    return mutableListOf<DomainLayerUsers.SKUser>().apply {
      repeat(15) {
        add(
          DomainLayerUsers.SKUser(
            uuid = "${it}_${skWorkspace.uuid}",
            workspaceId = skWorkspace.uuid,
            gender = null,
            name = "Anmol Verma",
            location = "Chandigarh, IN",
            email = "anmol.verma4@gmail.com",
            username = "oianmol",
            userSince = Clock.System.now().toEpochMilliseconds().minus(1000 * 60 * 60 * 28),
            phone = "+918284866938",
            avatarUrl = "https://avatars.slack-edge.com/2018-07-20/401750958992_1b07bb3c946bc863bfc6_88.png"
          )
        )
      }
    }
  }

  private fun getWorkSpaces(): MutableList<DomainLayerWorkspaces.SKWorkspace> {
    return mutableListOf<DomainLayerWorkspaces.SKWorkspace>().apply {
      add(
        DomainLayerWorkspaces.SKWorkspace(
          "1",
          "Kotlin",
          "kotlinlang.slack.com",
          "https://avatars.slack-edge.com/2021-08-18/2394702857843_51119ca847fe3f05614b_88.png",
          true
        )
      )
      add(
        DomainLayerWorkspaces.SKWorkspace(
          "2",
          "mutualmobile",
          "mutualmobile.slack.com",
          "https://avatars.slack-edge.com/2018-07-20/401750958992_1b07bb3c946bc863bfc6_88.png",
          false
        )
      )
      add(
        DomainLayerWorkspaces.SKWorkspace(
          "3",
          "androidworldwide",
          "androidworldwide.slack.com",
          "https://avatars.slack-edge.com/2020-09-03/1337922760453_3531ceb03787e9a60507_88.png",
          false
        )
      )
      add(
        DomainLayerWorkspaces.SKWorkspace(
          "4",
          "gophers",
          "gophers.slack.com",
          "https://avatars.slack-edge.com/2019-12-06/850376190706_33364309961e71e9fe4a_88.png",
          false
        )
      )

    }
  }
}