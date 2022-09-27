package dev.baseio


import dev.baseio.slackdata.protos.KMHelloServiceStub
import dev.baseio.slackdata.protos.KMSKWorkspaces
import dev.baseio.slackdata.protos.kmEmpty
import io.github.timortel.kotlin_multiplatform_grpc_lib.KMChannel

suspend fun getWorkspaces(address: String = "localhost", port: Int): KMSKWorkspaces {
  val channel = KMChannel.Builder
    .forAddress(address, port)
    .usePlaintext()
    .build()

  val stub = KMHelloServiceStub(channel)
  return stub.getWorkspaces(kmEmpty { })
}