package dev.baseio.slackserver.services

import dev.baseio.slackdata.protos.SKChannel
import dev.baseio.slackdata.protos.SKUser
import dev.baseio.slackdata.protos.SKWorkspace

object FakeDataPreloader {

  val workSpacesLocal = getWorkSpaces()

  val users = buildUsers(workSpacesLocal)

  val channels = buildChannelsForWorkspaces(workSpacesLocal)


  private fun buildChannelsForWorkspaces(
    workSpaces: MutableList<SKWorkspace>
  ): MutableList<SKChannel> {
    val channels = mutableListOf<SKChannel>()
    workSpaces.forEach { skWorkspace ->
      // for every workspace create 50 channels
      repeat(5) {
        channels.add(
          SKChannel.newBuilder()
            .setWorkspaceId(skWorkspace.uuid)
            .setUuid("${it}_${skWorkspace.uuid}")
            .setName("Channel #$it")
            .setCreatedDate(System.currentTimeMillis())
            .setModifiedDate(System.currentTimeMillis())
            .setIsMuted(false)
            .setIsPrivate(false)
            .setIsStarred(false)
            .setIsShareOutSide(false)
            .setIsOneToOne(false)
            .setAvatarUrl(null)
            .build()
        )
      }
    }
    return channels
  }

  private fun buildUsers(
    workSpaces: MutableList<SKWorkspace>,
  ): MutableList<SKUser> {
    val users = mutableListOf<SKUser>()
    workSpaces.forEach { skWorkspace ->
      users.addAll(getUsers(skWorkspace))
    }
    return users
  }

  private fun getUsers(skWorkspace: SKWorkspace): MutableList<SKUser> {
    return mutableListOf<SKUser>().apply {
      repeat(50) {
        add(
          SKUser.newBuilder().setUuid(
            skWorkspace.uuid + "fd7bcea3-5ff0-4235-8555-7e0876ead879$it"
          )
            .setWorkspaceId(skWorkspace.uuid)
            .setGender("Male")
            .setName("Emery O'Kon")
            .setEmail("emery.o'kon@email.com")
            .setUsername("emery.o'kon").setUserSince(System.currentTimeMillis())
            .setPhone(
              "+222 1-612-884-0086 x98654"
            ).setAvatarUrl(
              "https://robohash.org/etetsit.png?size=300x300&set=set1"
            ).build()
        )
      }
    }
  }


  private fun getWorkSpaces(): MutableList<SKWorkspace> {
    return mutableListOf<SKWorkspace>().apply {
      add(
        SKWorkspace.newBuilder()
          .setUuid("1")
          .setName("Kotlin")
          .setDomain("kotlinlang.slack.com")
          .setPicUrl("https://avatars.slack-edge.com/2021-08-18/2394702857843_51119ca847fe3f05614b_88.png")
          .build()
      )
      add(
        SKWorkspace.newBuilder()
          .setUuid("2")
          .setName("mutualmobile")
          .setDomain("mutualmobile.slack.com")
          .setPicUrl("https://avatars.slack-edge.com/2018-07-20/401750958992_1b07bb3c946bc863bfc6_88.png")
          .build()
      )
      add(
        SKWorkspace.newBuilder()
          .setUuid("3")
          .setName("androidworldwide")
          .setDomain("androidworldwide.slack.com")
          .setPicUrl(
            "https://avatars.slack-edge.com/2020-09-03/1337922760453_3531ceb03787e9a60507_88.png",
          )
          .build()
      )
      add(
        SKWorkspace.newBuilder()
          .setUuid("4")
          .setName("gophers")
          .setDomain("gophers.slack.com")
          .setPicUrl(
            "https://avatars.slack-edge.com/2019-12-06/850376190706_33364309961e71e9fe4a_88.png",
          )
          .build()
      )
    }
  }
}