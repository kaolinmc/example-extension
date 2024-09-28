import dev.extframework.gradle.common.archives
import dev.extframework.gradle.common.commonUtil
import dev.extframework.gradle.common.coreApi
import dev.extframework.gradle.deobf.MinecraftMappings
import dev.extframework.gradle.extframework
import dev.extframework.gradle.publish.ExtensionPublication

plugins {
    kotlin("jvm") version "1.9.21"

    id("maven-publish")
    id("dev.extframework.mc") version "1.2.8"
    id("dev.extframework.common") version "1.0.17"
}

group = "dev.extframework.extension"
version = "1.0-BETA"

tasks.wrapper {
    gradleVersion = "8.6-rc-1"
}

repositories {
    mavenCentral()
    extframework()
    maven {
        url = uri("https://cursemaven.com")
    }
    maven {
        url = uri("https://repo.extframework.dev/registry")
    }
}

dependencies {
    coreApi()

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
}

tasks.launch {
    javaLauncher.set(javaToolchains.launcherFor(java.toolchain))
    targetNamespace.set("mojang:deobfuscated")
    jvmArgs(
        "-XstartOnFirstThread",
        "-Xmx2G",
        "-XX:+UnlockExperimentalVMOptions",
        "-XX:+UseG1GC",
        "-XX:G1NewSizePercent=20",
        "-XX:G1ReservePercent=20",
        "-XX:MaxGCPauseMillis=50",
        "-XX:G1HeapRegionSize=32M"
    )
    mcVersion.set("1.21")
}

extension {
    model {
//        groupId.set("dev.extframework.extension")
        name = "example-extension"
//        version.set("1.0-BETA")
    }

    extensions {
//        fabricMod(
//            name = "fabric-api",
//            projectId = "306612",
//            fileId = "5105683"
//        )
    }

    metadata {
        name = "Example Extension"
        developers.add("Durgan McBroom")
        description.set("An example extension to test the limits of extframework")
    }

    partitions {
        main {
            extensionClass = "dev.extframework.extensions.example.ExampleExtension"
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
            }
        }
        version("latest") {
            dependencies {
                archives()
                commonUtil()

                minecraft("1.21")
                implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
                coreApi()
            }
            mappings = MinecraftMappings.mojang

            supportVersions("1.21")
        }

        version("1.19.2") {
            mappings = MinecraftMappings.mojang
            dependencies {
                minecraft("1.19.2")
                implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
                coreApi()
            }

            supportVersions("1.18", "1.19.2")
        }
    }
}

publishing {
    publications {
        create<ExtensionPublication>("prod")
    }
    repositories {
        maven {
            url = uri("https://repo.extframework.dev")
            credentials {
                password = property("maven.auth.token") as String
            }
        }
    }
}


kotlin {
    jvmToolchain(21)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}