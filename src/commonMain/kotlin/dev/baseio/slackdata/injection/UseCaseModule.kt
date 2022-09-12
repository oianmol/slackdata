package dev.baseio.slackdata.injection

import dev.baseio.slackdomain.usecases.chat.UseCaseSendMessage
import dev.baseio.slackdomain.usecases.channels.*
import dev.baseio.slackdomain.usecases.chat.UseCaseFetchMessages
import org.koin.dsl.module

val useCaseModule = module {
  single { UseCaseFetchChannels(get()) }
  single { UseCaseFetchChannelsWithLastMessage(get()) }
  single { UseCaseFetchMessages(get()) }
  single { UseCaseSendMessage(get()) }
  single { UseCaseCreateChannel(get()) }
  single { UseCaseCreateOneToOneChannel(get()) }
  single { UseCaseGetChannel(get()) }
  single { UseCaseFetchChannelCount(get()) }
  single { UseCaseSearchChannel(get()) }
  single { UseCaseFetchUsers(get()) }
}