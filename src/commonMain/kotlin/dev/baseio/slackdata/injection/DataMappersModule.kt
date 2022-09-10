package dev.baseio.slackdata.injection

import database.SlackChannel
import database.SlackMessage
import dev.baseio.slackdata.mapper.*
import dev.baseio.slackdomain.domain.model.channel.DomainLayerChannels
import dev.baseio.slackdomain.domain.model.message.DomainLayerMessages
import dev.baseio.slackdomain.domain.model.users.DomainLayerUsers
import dev.baseio.slackdomain.domain.model.users.RandomUser
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue
import org.koin.dsl.module

val dataMappersModule = module {
  single<EntityMapper<DomainLayerUsers.SlackUser, SlackChannel>>(qualifier = SlackUserChannel) { SlackUserChannelMapper() }
  single<EntityMapper<DomainLayerUsers.SlackUser, RandomUser>>(qualifier = SlackUserRandomUser) { SlackUserMapper() }
  single<EntityMapper<DomainLayerChannels.SlackChannel, SlackChannel>>(qualifier = SlackChannelChannel) { SlackChannelMapper() }
  single<EntityMapper<DomainLayerMessages.SlackMessage, SlackMessage>>(qualifier = SlackMessageMessage) { SlackMessageMapper() }
}

object SlackMessageMessage: Qualifier{
  override val value: QualifierValue
    get() = "SlackMessageMessage"

}
object SlackUserChannel : Qualifier {
  override val value: QualifierValue
    get() = "SlackUserChannel"
}

object SlackUserRandomUser :Qualifier {
  override val value: QualifierValue
    get() = "SlackUserRandomUser"
}

object SlackChannelChannel: Qualifier{
  override val value: QualifierValue
    get() = "SlackChannelChannel"

}