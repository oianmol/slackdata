pluginManagement {
    repositories {
        google()
        mavenLocal()
        maven(url = "https://jitpack.io")
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "com.android" || requested.id.name == "kotlin-android-extensions") {
                useModule("com.android.tools.build:gradle:7.2.2")
            }
        }
    }
}
include("generate-proto")
include("protos")
rootProject.name = "slackdata"

