import com.kaolinmc.core.main.main
import com.kaolinmc.gradle.common.kaolin
import com.kaolinmc.kiln.publish.ExtensionPublication
import com.kaolinmc.minecraft.MojangNamespaces
import com.kaolinmc.minecraft.minecraft
import com.kaolinmc.minecraft.task.LaunchMinecraft

plugins {
    kotlin("jvm") version "2.1.0"

    id("maven-publish")
    id("kaolin.kiln") version "0.1.5"
    id("com.kaolinmc.common") version "0.1.5"
}

group = "com.kaolinmc.extension"
version = "1.0.3-BETA"

tasks.wrapper {
    gradleVersion = "8.6-rc-1"
}

repositories {
    mavenCentral()
    kaolin()
}

val launch1_21_4 by tasks.registering(LaunchMinecraft::class) {
    dependsOn(tasks.named("publishToMavenLocal"))
    targetNamespace = MojangNamespaces.deobfuscated.identifier
    javaLauncher.set(javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(21))
    })
    mcVersion = "1.21.4"
}


extension {
    metadata {
        name = "Example Extension"
        developers.add("Durgan McBroom")
        description.set("An example extension to test the limits of Kaolin")
    }

    partitions {
        main {
            extensionClass = "com.kaolinmc.extensions.example.ExampleExtension"
            dependencies {
            }
        }
        minecraft("latest") {
            entrypoint = "com.kaolinmc.extensions.example.Initializer"
            dependencies {
                minecraft("1.21.4")
            }
            mappings = MojangNamespaces.deobfuscated

            supportVersions("1.21", "1.21.1", "1.21.4")
        }
    }
}

publishing {
    publications {
        create<ExtensionPublication>("prod")
    }
    repositories {
        maven {
            url = uri("https://repo.kaolinmc.com")
            credentials {
//                password = property("maven.auth.token") as String
            }
        }
    }
}

kotlin {
    jvmToolchain(8)
}