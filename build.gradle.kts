plugins {
    kotlin("jvm") version "1.7.10"
    id("maven-publish")

    id("net.yakclient") version "1.0"
    kotlin("kapt") version "1.8.10"
}

group = "net.yakclient.extensions"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        isAllowInsecureProtocol = true
        url = uri("http://maven.yakclient.net/snapshots")
    }
}

tasks.named<JavaExec>("launch") {
    jvmArgs("-XstartOnFirstThread")
}

yakclient {
    model {
        groupId = "net.yakclient.extensions"
        name = "example-extension"
        version = "1.0-SNAPSHOT"

        packagingType = "jar"
        extensionClass = "net.yakclient.extensions.example.ExampleExtension"
    }

    partitions {
        val main by named {
            dependencies {
                "kapt"("net.yakclient:yakclient-preprocessor:1.0-SNAPSHOT")
                implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
            }
        }

        this.main = main

        named("1.19.2") {
            dependencies {
                "kapt1.19.2"("net.yakclient:yakclient-preprocessor:1.0-SNAPSHOT")

                minecraft("1.19.2")
                implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
                implementation("net.yakclient:client-api:1.0-SNAPSHOT")
            }

            supportedVersions.addAll(listOf("1.19.2"))
        }

        named("1.18") {
            dependencies {
                minecraft("1.18")
                "kapt1.18"("net.yakclient:yakclient-preprocessor:1.0-SNAPSHOT")

                implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
                implementation("net.yakclient:client-api:1.0-SNAPSHOT")
            }

            supportedVersions.addAll(listOf("1.18"))
        }
    }
}



publishing {
    publications {
        create<MavenPublication>("example-extension-maven") {
            artifact(tasks["jar"])
            artifact(project.buildDir.toPath().resolve("libs/erm.json")).classifier = "erm"

            groupId = "net.yakclient.extensions"
            artifactId = "example-extension"
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}