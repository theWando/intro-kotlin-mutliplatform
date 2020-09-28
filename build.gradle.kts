@file:Suppress("UNUSED_VARIABLE")

plugins {
  kotlin("multiplatform") version "1.4.10"
}

kotlin {
  jvm()
  js {
    browser {}
  }

  val ktorVersion = "1.4.1"
  val logbackVersion = "1.2.3"
  val kotlinXVersion = "0.7.1"

  sourceSets {
    val jvmMain by getting {
      dependencies {
        implementation("io.ktor:ktor-server-netty:$ktorVersion")
        implementation("io.ktor:ktor-html-builder:$ktorVersion")
        implementation("ch.qos.logback:logback-classic:$logbackVersion")
      }
    }

    val jsMain by getting {
      dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-html-js:$kotlinXVersion")
      }
    }

    val commonMain by getting {
      dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-html-common:$kotlinXVersion")
      }
    }
  }
}

repositories {
  jcenter()
  mavenCentral()
}

val run by tasks.creating(JavaExec::class) {
  group = "application"
  main = "com.jetbrains.handson.introMpp.MainKt"
  kotlin {
    val main = targets["jvm"].compilations["main"]
    dependsOn(main.compileAllTaskName)
    classpath(
            { main.output.allOutputs.files },
            { configurations["jvmRuntimeClasspath"] }
    )
  }
  ///disable app icon on macOS
  systemProperty("java.awt.headless", "true")
}