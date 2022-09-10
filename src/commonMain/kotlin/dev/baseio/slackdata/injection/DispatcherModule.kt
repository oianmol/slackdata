package dev.baseio.slackdata.injection

import dev.baseio.slackdata.injection.dispatcher.CoroutineDispatcherProvider
import dev.baseio.slackdata.injection.dispatcher.RealCoroutineDispatcherProvider
import org.koin.dsl.module

val dispatcherModule = module {
  single<CoroutineDispatcherProvider> { RealCoroutineDispatcherProvider() }
}
