import dev.baseio.slackserver.data.Database
import dev.baseio.slackserver.data.impl.ChannelsDataSourceImpl
import dev.baseio.slackserver.data.impl.MessagesDataSourceImpl
import dev.baseio.slackserver.data.impl.WorkspaceDataSourceImpl
import dev.baseio.slackserver.services.ChannelService
import dev.baseio.slackserver.services.MessagingService
import dev.baseio.slackserver.services.WorkspaceService
import io.grpc.ServerBuilder

fun main() {
  val workspaceDataSource = WorkspaceDataSourceImpl(Database.slackDB)
  val channelsDataSource = ChannelsDataSourceImpl(Database.slackDB)
  val messagesDataSource = MessagesDataSourceImpl(Database.slackDB)

  ServerBuilder.forPort(17600)
    .addService(WorkspaceService(workspaceDataSource = workspaceDataSource))
    .addService(ChannelService(channelsDataSource = channelsDataSource))
    .addService(MessagingService(messagesDataSource = messagesDataSource))
    .build()
    .start()
    .awaitTermination()
}