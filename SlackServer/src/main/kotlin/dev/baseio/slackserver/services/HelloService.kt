package dev.baseio.slackserver.services

import dev.baseio.slackdata.protos.*
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class HelloService(coroutineContext: CoroutineContext = Dispatchers.IO) :
  HelloServiceGrpcKt.HelloServiceCoroutineImplBase(coroutineContext) {
  override suspend fun getWorkspaces(request: Empty): SKWorkspaces {
    return SKWorkspaces.newBuilder()
      .addAllWorkspaces(FakeDataPreloader.workSpacesLocal)
      .build()
  }

  override suspend fun getUsers(request: Empty): SKUsers {
    return SKUsers.newBuilder()
      .addAllUsers(FakeDataPreloader.users)
      .build()
  }

  override suspend fun getMessages(request: Empty): SKMessages {
    return SKMessages.newBuilder()
      .build()
  }

  override suspend fun getChannels(request: Empty): SKChannels {
    return SKChannels.newBuilder()
      .addAllChannels(FakeDataPreloader.channels)
      .build()
  }
}