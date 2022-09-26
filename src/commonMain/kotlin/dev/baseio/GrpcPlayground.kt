package dev.baseio


import dev.baseio.slackdata.protos.kmHelloRequest
import dev.baseio.slackdata.protos.KMHelloServiceStub
import dev.baseio.slackdata.protos.KMResponse
import io.github.timortel.kotlin_multiplatform_grpc_lib.KMChannel
import io.github.timortel.kotlin_multiplatform_grpc_lib.util.TimeUnit

suspend fun performGreeting(message: String, port: Int): String {
  val channel = KMChannel.Builder
    .forAddress("localhost", port)
    .usePlaintext()
    .build()

  val stub = KMHelloServiceStub(channel)

  val request = kmHelloRequest {
    greeting = message
  }

  val response: KMResponse = stub
    .withDeadlineAfter(10, TimeUnit.SECONDS)
    .sayHello(request)

  return response.response
}

suspend fun performMultipleGreetings(message: String, port: Int) {
  val channel = KMChannel.Builder
    .forAddress("localhost", port)
    .usePlaintext()
    .build()

  val stub = KMHelloServiceStub(channel)

  val request = kmHelloRequest {
    greeting = message
  }

  stub
    .withDeadlineAfter(10, TimeUnit.SECONDS)
    .sayHelloMultipleTimes(request)
    .collect {
      println("Received: ${it.response}")
    }
}