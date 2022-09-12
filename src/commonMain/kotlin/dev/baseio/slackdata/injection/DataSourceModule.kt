package dev.baseio.slackdata.injection

import dev.baseio.slackdata.datasources.channels.SKDataSourceChannelsImpl
import dev.baseio.slackdata.datasources.channels.SKDataSourceCreateChannelImpl
import dev.baseio.slackdata.datasources.channels.SlackSKDataSourceChannelLastMessage
import dev.baseio.slackdata.datasources.messages.SlackSKDataSourceMessagesImpl
import dev.baseio.slackdata.datasources.users.SKDataSourceUsersImpl
import dev.baseio.slackdata.datasources.workspaces.SKDataSourceCreateWorkspacesImpl
import dev.baseio.slackdata.datasources.workspaces.SKDataSourceWorkspacesImpl
import dev.baseio.slackdomain.datasources.channels.SKDataSourceChannelLastMessage
import dev.baseio.slackdomain.datasources.channels.SKDataSourceChannels
import dev.baseio.slackdomain.datasources.channels.SKDataSourceCreateChannel
import dev.baseio.slackdomain.datasources.messages.SKDataSourceMessages
import dev.baseio.slackdomain.datasources.users.SKDataSourceUsers
import dev.baseio.slackdomain.datasources.workspaces.SKDataSourceCreateWorkspaces
import dev.baseio.slackdomain.datasources.workspaces.SKDataSourceWorkspaces
import org.koin.dsl.module

val dataSourceModule = module {
  single<SKDataSourceCreateWorkspaces> {
    SKDataSourceCreateWorkspacesImpl(get(), get())
  }
  single<SKDataSourceWorkspaces> {
    SKDataSourceWorkspacesImpl(get(), get(SlackWorkspaceMapperQualifier), get())
  }
  single<SKDataSourceCreateChannel> {
    SKDataSourceCreateChannelImpl(
      get(),
      get(qualifier = SlackChannelChannelQualifier),
      get()
    )
  }
  single<SKDataSourceChannels> {
    SKDataSourceChannelsImpl(get(), get(SlackChannelChannelQualifier), get())
  }
  single<SKDataSourceUsers> { SKDataSourceUsersImpl(get(), get(SlackUserRandomUserQualifier), get()) }
  single<SKDataSourceMessages> { SlackSKDataSourceMessagesImpl(get(), get(SlackMessageMessageQualifier), get()) }
  single<SKDataSourceChannelLastMessage> {
    SlackSKDataSourceChannelLastMessage(
      get(),
      get(SlackMessageMessageQualifier),
      get(SlackChannelChannelQualifier),
      get()
    )
  }
}

