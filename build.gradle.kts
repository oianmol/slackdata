plugins {
  kotlin("multiplatform") version "1.7.10"
  id("com.android.library")
  id("maven-publish")
  id("com.squareup.sqldelight") version "1.5.3"
}

object GithubRepo {
    val name: String? = System.getenv("GITHUB_REPOSITORY")
    val path: String = "https://www.github.com/$name"
    val packages: String = "https://maven.pkg.github.com/$name"
    val ref: String? = System.getenv("GITHUB_REF")
}

object Jvm {
    val target = JavaVersion.VERSION_1_8
}


group = "dev.baseio.slackclone"
version = "1.0.0"

publishing {
  repositories {
    mavenLocal()
    repositories.maven {
        name = "github"
        url = project.uri(GithubRepo.packages)
        credentials(PasswordCredentials::class)
    }.takeIf { GithubRepo.name != null }
  }
}

allprojects {
  group = "dev.baseio.slackclone"
  version = System.getenv("GITHUB_REF")?.split('/')?.last() ?: "development"
}

repositories {
  google()
  jcenter()
  mavenCentral()
  gradlePluginPortal()
}

kotlin {
  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = "1.8"
    }
    testRuns["test"].executionTask.configure {
      useJUnitPlatform()
    }
  }

  iosX64 {
    binaries {
      framework {
        baseName = "slackdata"
      }
    }
  }
  iosArm64 {
    binaries {
      framework {
        baseName = "slackdata"
      }
    }
  }
  iosSimulatorArm64 {
    binaries {
      framework {
        baseName = "slackdata"
      }
    }
  }
  android {
    publishLibraryVariants("release")
    compilations.all {
            kotlinOptions {
                jvmTarget = "${Jvm.target}"
                useIR = true
            }
        }
  }
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(Deps.Kotlinx.datetime)
        implementation(Deps.SqlDelight.core)
        implementation(Deps.Kotlinx.coroutines)
        implementation(Deps.Koin.core)
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }
    val jvmMain by getting {
      dependencies {
        implementation(Deps.Kotlinx.JVM.coroutinesSwing)
        implementation(Deps.SqlDelight.jvmDriver)
      }
    }
    val jvmTest by getting

    val sqlDriverNativeMain by creating {
      dependsOn(commonMain)
      dependencies {
        implementation(Deps.SqlDelight.nativeDriver)
      }
    }

    val iosArm64Main by getting {
      dependsOn(sqlDriverNativeMain)
      dependencies {
        //implementation(Deps.Kotlinx.IOS.coroutinesArm64)
      }
    }
    val iosSimulatorArm64Main by getting {
      dependsOn(sqlDriverNativeMain)
      dependencies {
        //implementation(Deps.Kotlinx.IOS.coroutinesArm64)
      }
    }
    val iosX64Main by getting {
      dependsOn(sqlDriverNativeMain)
      dependencies {
        implementation(Deps.Kotlinx.IOS.coroutinesX64)
      }
    }
    val iosX64Test by getting
    val iosArm64Test by getting
    val androidMain by getting {
      dependencies {
        implementation(Deps.SqlDelight.androidDriver)
        implementation(Deps.AndroidX.lifecycleViewModelKtx)
      }
    }
    val androidTest by getting {
      dependencies {
        implementation("junit:junit:4.13.2")
      }
    }
  }
}

kotlin {
  targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
    binaries.all {
      // TODO: the current compose binary surprises LLVM, so disable checks for now.
      freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
    }
  }
}

sqldelight {
  database("SlackDB") {
    packageName = "dev.baseio.database"
    linkSqlite = true
  }
}


android {
  lint{
    this.abortOnError = false
  }
  lintOptions {
    this.isAbortOnError = false
    baseline(file("lint-baseline.xml"))
  }
  compileSdk = (31)
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  defaultConfig {
    minSdk = (24)
    targetSdk = (31)
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
}

object Versions {
  const val koin = "3.1.4"
}

object Deps {

  object Kotlinx {
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4"
    const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.4.0"

    object JVM {
      const val coroutinesSwing = "org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.4"
    }

    object IOS {
      const val coroutinesX64 = "org.jetbrains.kotlinx:kotlinx-coroutines-core-iosx64:1.6.4"
      const val coroutinesArm64 = "org.jetbrains.kotlinx:kotlinx-coroutines-core-iosarm64:1.6.4"
    }

    object Android {
      const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
    }
  }

  object SqlDelight {
    const val androidDriver = "com.squareup.sqldelight:android-driver:1.5.3"
    const val jvmDriver = "com.squareup.sqldelight:sqlite-driver:1.5.3"
    const val nativeDriver = "com.squareup.sqldelight:native-driver:1.5.3"
    const val core = "com.squareup.sqldelight:runtime:1.5.3"
  }

  object Koin {
    const val core = "io.insert-koin:koin-core:${Versions.koin}"
    const val core_jvm = "io.insert-koin:koin-core-jvm:${Versions.koin}"
    const val test = "io.insert-koin:koin-test:${Versions.koin}"
    const val android = "io.insert-koin:koin-android:${Versions.koin}"
  }

  object AndroidX {
    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1"
  }

}