package dev.baseio.slackdomain.usecases.channels


import dev.baseio.slackdomain.datasources.local.channels.SKDataSourceChannels
import dev.baseio.slackdomain.model.channel.DomainLayerChannels
import dev.baseio.slackdomain.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow

class UseCaseSearchChannel(private val sdkDataSource: SKDataSourceChannels) :
  BaseUseCase<List<DomainLayerChannels.SKChannel>, String> {
  override fun performStreamingNullable(params: String?): Flow<List<DomainLayerChannels.SKChannel>> {
    return sdkDataSource.fetchChannelsOrByName(params)
  }
}
