package dev.baseio

import database.SlackChannel
import dev.baseio.slackdata.mapper.EntityMapper
import dev.baseio.slackdomain.CoroutineDispatcherProvider
import dev.baseio.slackdomain.datasources.local.channels.SKDataSourceCreateChannels
import dev.baseio.slackdomain.datasources.local.users.SKDataSourceCreateUsers
import dev.baseio.slackdomain.datasources.local.workspaces.SKDataSourceCreateWorkspaces
import dev.baseio.slackdomain.model.channel.DomainLayerChannels
import dev.baseio.slackdomain.model.users.DomainLayerUsers
import dev.baseio.slackdomain.model.workspaces.DomainLayerWorkspaces
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class FakeDataPreloader(
  private val skDataSourceCreateWorkspaces: SKDataSourceCreateWorkspaces,
  private val skDataSourceCreateChannels: SKDataSourceCreateChannels,
  private val skDataSourceCreateUsers: SKDataSourceCreateUsers,
  private val slackUserChannelMapper: EntityMapper<DomainLayerUsers.SKUser, SlackChannel>,
  private val channelMapper: EntityMapper<DomainLayerChannels.SKChannel, SlackChannel>,
  private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) {
  suspend fun preload() {
    withContext(coroutineDispatcherProvider.io) {
      val workSpaces = getWorkSpaces()
      skDataSourceCreateWorkspaces.saveWorkspaces(workSpaces)

      val users = buildUsers(workSpaces)
      skDataSourceCreateUsers.saveUsers(users)

      val channels = buildChannelsForWorkspaces(workSpaces)
      // this creates one to one groups for users
      val channelsOneToOne = users.map { user ->
        slackUserChannelMapper.mapToData(user)
      }.map {
        channelMapper.mapToDomain(it)
      }
      channels.addAll(channelsOneToOne)

      skDataSourceCreateChannels.saveChannels(channels)
    }
  }

  private fun buildChannelsForWorkspaces(
    workSpaces: MutableList<DomainLayerWorkspaces.SKWorkspace>
  ): MutableList<DomainLayerChannels.SKChannel> {
    val channels = mutableListOf<DomainLayerChannels.SKChannel>()
    workSpaces.forEach { skWorkspace ->
      // for every workspace create 50 channels
      repeat(5) {
        channels.add(
          DomainLayerChannels.SKChannel(
            workspaceId = skWorkspace.uuid,
            uuid = "${it}_${skWorkspace.uuid}",
            name = "Channel #$it",
            createdDate = Clock.System.now().toEpochMilliseconds(),
            modifiedDate = Clock.System.now().toEpochMilliseconds(),
            isMuted = false,
            isPrivate = false,
            isStarred = false,
            isShareOutSide = false,
            isOneToOne = false,
            avatarUrl = null
          )
        )
      }


    }
    return channels
  }

  private fun buildUsers(
    workSpaces: MutableList<DomainLayerWorkspaces.SKWorkspace>,
  ): MutableList<DomainLayerUsers.SKUser> {
    val users = mutableListOf<DomainLayerUsers.SKUser>()
    workSpaces.forEach { skWorkspace ->
      users.addAll(getUsers(skWorkspace))
    }
    return users
  }

  private fun getUsers(skWorkspace: DomainLayerWorkspaces.SKWorkspace): MutableList<DomainLayerUsers.SKUser> {
    return mutableListOf<DomainLayerUsers.SKUser>().apply {
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "fd7bcea3-5ff0-4235-8555-7e0876ead879",
          skWorkspace.uuid,
          "Genderqueer",
          "Emery O'Kon",
          "",
          "emery.o'kon@email.com",
          "emery.o'kon",
          Clock.System.now().toEpochMilliseconds(),
          "+222 1-612-884-0086 x98654",
          "https://robohash.org/etetsit.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "25c21209-5af0-448b-aa8b-fa970d776e94",
          skWorkspace.uuid,
          "Non-binary",
          "Adam Bergnaum",
          "",
          "adam.bergnaum@email.com",
          "adam.bergnaum",
          Clock.System.now().toEpochMilliseconds(),
          "+420 381.672.9797",
          "https://robohash.org/quasiplaceataut.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "dfba01a7-ff39-4d45-b79b-1b8554c15354",
          skWorkspace.uuid,
          "Agender",
          "Loretta Lind",
          "",
          "loretta.lind@email.com",
          "loretta.lind",
          Clock.System.now().toEpochMilliseconds(),
          "+53 213.810.3645 x6042",
          "https://robohash.org/voluptatemcumquequis.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "a3e5bd64-be25-4b26-a0de-650a45e6f2b4",
          skWorkspace.uuid,
          "Polygender",
          "Beaulah Gulgowski",
          "",
          "beaulah.gulgowski@email.com",
          "beaulah.gulgowski",
          Clock.System.now().toEpochMilliseconds(),
          "+670 1-368-246-6176 x8440",
          "https://robohash.org/quibusdamiustocum.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "587a53e9-1275-4040-bc18-4d3d19b1c9e4",
          skWorkspace.uuid,
          "Non-binary",
          "Valerie Keebler",
          "",
          "valerie.keebler@email.com",
          "valerie.keebler",
          Clock.System.now().toEpochMilliseconds(),
          "+598 726-455-2039",
          "https://robohash.org/dignissimosquidemearum.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "4466a497-2961-4699-b325-8625dc1df3d7",
          skWorkspace.uuid,
          "Genderqueer",
          "Brain Bechtelar",
          "",
          "brain.bechtelar@email.com",
          "brain.bechtelar",
          Clock.System.now().toEpochMilliseconds(),
          "+44 177.258.2104",
          "https://robohash.org/nullavoluptatumpariatur.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "6fc6828e-7ce2-41c3-a397-320b1ca9f646",
          skWorkspace.uuid,
          "Male",
          "Kaley Blanda",
          "",
          "kaley.blanda@email.com",
          "kaley.blanda",
          Clock.System.now().toEpochMilliseconds(),
          "+222 1-978-392-8047",
          "https://robohash.org/atquenumquamvoluptatibus.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "daf5c5bc-92fb-4ecd-b7df-0b18724169d3",
          skWorkspace.uuid,
          "Bigender",
          "Jada Breitenberg",
          "",
          "jada.breitenberg@email.com",
          "jada.breitenberg",
          Clock.System.now().toEpochMilliseconds(),
          "+1-268 683-361-7659 x129",
          "https://robohash.org/eumnobisoptio.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "06def76e-6dcf-461e-9f7d-567562d6942c",
          skWorkspace.uuid,
          "Female",
          "Drew Zemlak",
          "",
          "drew.zemlak@email.com",
          "drew.zemlak",
          Clock.System.now().toEpochMilliseconds(),
          "+502 (794) 655-5126 x27188",
          "https://robohash.org/vitaeesteius.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "000b8e3f-ca2c-4fb3-a5e0-7d923fe00cfb",
          skWorkspace.uuid,
          "Bigender",
          "Signe Sauer",
          "",
          "signe.sauer@email.com",
          "signe.sauer",
          Clock.System.now().toEpochMilliseconds(),
          "+33 1-968-628-3956 x38528",
          "https://robohash.org/saepeconsecteturharum.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "3d6e4c4a-ab90-4a70-b00c-5eb0722448ef",
          skWorkspace.uuid,
          "Female",
          "Margarita Ferry",
          "",
          "margarita.ferry@email.com",
          "margarita.ferry",
          Clock.System.now().toEpochMilliseconds(),
          "+30 (773) 786-3601",
          "https://robohash.org/sitnullapraesentium.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "8476c8d0-76f5-4da7-ab14-644e2e622bae",
          skWorkspace.uuid,
          "Female",
          "Marline Kub",
          "",
          "marline.kub@email.com",
          "marline.kub",
          Clock.System.now().toEpochMilliseconds(),
          "+213 1-956-393-3063 x67371",
          "https://robohash.org/etsaepenobis.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "598f5e93-e7ee-4daf-93f5-613275e033a1",
          skWorkspace.uuid,
          "Polygender",
          "Britta Shields",
          "",
          "britta.shields@email.com",
          "britta.shields",
          Clock.System.now().toEpochMilliseconds(),
          "+64 1-939-134-3914 x65496",
          "https://robohash.org/omnisautrerum.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "9abb996c-84f4-40b1-bd30-f7810763fdb5",
          skWorkspace.uuid,
          "Agender",
          "Guadalupe Kirlin",
          "",
          "guadalupe.kirlin@email.com",
          "guadalupe.kirlin",
          Clock.System.now().toEpochMilliseconds(),
          "+40 1-834-539-0334 x6142",
          "https://robohash.org/etmodineque.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "5c1124e3-3b30-44a9-8094-2a630b41c78a",
          skWorkspace.uuid,
          "Female",
          "Mikaela Windler",
          "",
          "mikaela.windler@email.com",
          "mikaela.windler",
          Clock.System.now().toEpochMilliseconds(),
          "+971 779-410-3929 x24261",
          "https://robohash.org/dolorquisquamcupiditate.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "f1a704bf-98fb-4b58-b592-202761f0192a",
          skWorkspace.uuid,
          "Bigender",
          "Merna Feil",
          "",
          "merna.feil@email.com",
          "merna.feil",
          Clock.System.now().toEpochMilliseconds(),
          "+852 995.864.5834 x064",
          "https://robohash.org/omnismolestiasculpa.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "0fc4029a-e7d7-469c-97c4-b336aa4e3b87",
          skWorkspace.uuid,
          "Male",
          "Eulah Beatty",
          "",
          "eulah.beatty@email.com",
          "eulah.beatty",
          Clock.System.now().toEpochMilliseconds(),
          "+971 387-037-1186 x4558",
          "https://robohash.org/etadquos.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "ae012ba5-55c1-4a44-af6e-c46f3e27f70b",
          skWorkspace.uuid,
          "Bigender",
          "Evelia Roob",
          "",
          "evelia.roob@email.com",
          "evelia.roob",
          Clock.System.now().toEpochMilliseconds(),
          "+260 (375) 987-7315 x48344",
          "https://robohash.org/velessequis.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "e7a7d8a7-61d9-43db-849b-bd44478e7db9",
          skWorkspace.uuid,
          "Agender",
          "Carmine Kihn",
          "",
          "carmine.kihn@email.com",
          "carmine.kihn",
          Clock.System.now().toEpochMilliseconds(),
          "+244 (345) 330-2095 x311",
          "https://robohash.org/sintvoluptatumad.png?size=300x300&set=set1"
        )
      )
      add(
        DomainLayerUsers.SKUser(
          skWorkspace.uuid +
              "43f636c9-5259-4418-aa81-77636f3f673c",
          skWorkspace.uuid,
          "Genderfluid",
          "Rusty Stracke",
          "",
          "rusty.stracke@email.com",
          "rusty.stracke",
          Clock.System.now().toEpochMilliseconds(),
          "+688 1-696-635-3375",
          "https://robohash.org/doloreatquedolores.png?size=300x300&set=set1"
        )
      )
    }
  }


  private fun getWorkSpaces(): MutableList<DomainLayerWorkspaces.SKWorkspace> {
    return mutableListOf<DomainLayerWorkspaces.SKWorkspace>().apply {
      add(
        DomainLayerWorkspaces.SKWorkspace(
          "1",
          "Kotlin",
          "kotlinlang.slack.com",
          "https://avatars.slack-edge.com/2021-08-18/2394702857843_51119ca847fe3f05614b_88.png",
          true
        )
      )
      add(
        DomainLayerWorkspaces.SKWorkspace(
          "2",
          "mutualmobile",
          "mutualmobile.slack.com",
          "https://avatars.slack-edge.com/2018-07-20/401750958992_1b07bb3c946bc863bfc6_88.png",
          false
        )
      )
      add(
        DomainLayerWorkspaces.SKWorkspace(
          "3",
          "androidworldwide",
          "androidworldwide.slack.com",
          "https://avatars.slack-edge.com/2020-09-03/1337922760453_3531ceb03787e9a60507_88.png",
          false
        )
      )
      add(
        DomainLayerWorkspaces.SKWorkspace(
          "4",
          "gophers",
          "gophers.slack.com",
          "https://avatars.slack-edge.com/2019-12-06/850376190706_33364309961e71e9fe4a_88.png",
          false
        )
      )

    }
  }
}