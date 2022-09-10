package dev.baseio.slackdata.injection

import dev.baseio.slackdata.repository.SlackChannelLastMessageRepository
import dev.baseio.slackdata.repository.SlackChannelsRepositoryImpl
import dev.baseio.slackdata.repository.SlackMessagesRepositoryImpl
import dev.baseio.slackdata.repository.SlackUserRepository
import dev.baseio.slackdomain.domain.repository.ChannelLastMessageRepository
import dev.baseio.slackdomain.domain.repository.ChannelsRepository
import dev.baseio.slackdomain.domain.repository.MessagesRepository
import dev.baseio.slackdomain.domain.repository.UsersRepository
import org.koin.dsl.module

val repositoryModule = module {
  single<ChannelsRepository> {
    SlackChannelsRepositoryImpl(
      get(),
      get(qualifier = SlackChannelChannel),
      get()
    )
  }
  single<UsersRepository> { SlackUserRepository(get(SlackUserRandomUser), get()) }
  single<MessagesRepository> { SlackMessagesRepositoryImpl(get(), get(SlackMessageMessage), get()) }
  single<ChannelLastMessageRepository> { SlackChannelLastMessageRepository(get(), get(SlackMessageMessage), get(SlackChannelChannel),get()) }
}

