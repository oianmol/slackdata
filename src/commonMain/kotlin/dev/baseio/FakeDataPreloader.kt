package dev.baseio

import dev.baseio.slackdomain.datasources.channels.SKDataSourceCreateChannels
import dev.baseio.slackdomain.datasources.users.SKDataSourceCreateUsers
import dev.baseio.slackdomain.datasources.workspaces.SKDataSourceCreateWorkspaces
import dev.baseio.slackdomain.model.channel.DomainLayerChannels
import dev.baseio.slackdomain.model.users.DomainLayerUsers
import dev.baseio.slackdomain.model.workspaces.DomainLayerWorkspaces

class FakeDataPreloader(
  private val skDataSourceCreateWorkspaces: SKDataSourceCreateWorkspaces,
  private val skDataSourceCreateChannels: SKDataSourceCreateChannels,
  private val skDataSourceCreateUsers: SKDataSourceCreateUsers
) {
  suspend fun preload() {
    //TODO prepare this data from online/offline json sources
    val workSpaces = mutableListOf<DomainLayerWorkspaces.SKWorkspace>()
    val channels = mutableListOf<DomainLayerChannels.SKChannel>()
    val users = mutableListOf<DomainLayerUsers.SKUser>()
    skDataSourceCreateWorkspaces.saveWorkspaces(workSpaces)
    skDataSourceCreateChannels.saveChannels(channels)
    skDataSourceCreateUsers.createUsers(users)
  }
}