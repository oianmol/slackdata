import dev.baseio.slackserver.services.HelloService
import io.grpc.ServerBuilder

fun main() {
  ServerBuilder.forPort(17600)
    .addService(HelloService())
    .build()
    .start()
    .awaitTermination()
}