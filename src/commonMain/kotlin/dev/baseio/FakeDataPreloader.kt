package dev.baseio

import dev.baseio.slackdomain.CoroutineDispatcherProvider
import dev.baseio.slackdomain.datasources.local.channels.SKDataSourceCreateChannels
import dev.baseio.slackdomain.datasources.local.users.SKDataSourceCreateUsers
import dev.baseio.slackdomain.datasources.local.workspaces.SKDataSourceCreateWorkspaces
import dev.baseio.slackdomain.model.channel.DomainLayerChannels
import dev.baseio.slackdomain.model.users.DomainLayerUsers
import dev.baseio.slackdomain.model.workspaces.DomainLayerWorkspaces
import kotlinx.coroutines.withContext

class FakeDataPreloader(
  private val skDataSourceCreateWorkspaces: SKDataSourceCreateWorkspaces,
  private val skDataSourceCreateChannels: SKDataSourceCreateChannels,
  private val skDataSourceCreateUsers: SKDataSourceCreateUsers,
  private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) {
  suspend fun preload() {
    //TODO prepare this data from online/offline json sources
    withContext(coroutineDispatcherProvider.io) {
      val workSpaces = getWorkSpaces()
      val channels = mutableListOf<DomainLayerChannels.SKChannel>()
      val users = mutableListOf<DomainLayerUsers.SKUser>()
      skDataSourceCreateWorkspaces.saveWorkspaces(workSpaces)
      skDataSourceCreateChannels.saveChannels(channels)
      skDataSourceCreateUsers.saveUsers(users)
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
          true
        )
      )
      add(
        DomainLayerWorkspaces.SKWorkspace(
          "3",
          "androidworldwide",
          "androidworldwide.slack.com",
          "https://avatars.slack-edge.com/2020-09-03/1337922760453_3531ceb03787e9a60507_88.png",
          true
        )
      )
      add(
        DomainLayerWorkspaces.SKWorkspace(
          "4",
          "gophers",
          "gophers.slack.com",
          "https://avatars.slack-edge.com/https://avatars.slack-edge.com/2019-12-06/850376190706_33364309961e71e9fe4a_88.png",
          true
        )
      )

    }
  }
}