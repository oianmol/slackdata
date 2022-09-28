import dev.baseio.slackserver.data.Database
import dev.baseio.slackserver.data.impl.ChannelsDataSourceImpl
import dev.baseio.slackserver.data.impl.WorkspaceDataSourceImpl
import dev.baseio.slackserver.services.SlackService
import io.grpc.ServerBuilder

fun main() {
  val workspaceDataSource = WorkspaceDataSourceImpl(Database.slackDB)
  val channelsDataSource = ChannelsDataSourceImpl(Database.slackDB)

  ServerBuilder.forPort(17600)
    .addService(SlackService(workspaceDataSource = workspaceDataSource,
      channelsDataSource = channelsDataSource))
    .build()
    .start()
    .awaitTermination()
}