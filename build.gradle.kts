plugins {
    kotlin("jvm") version "1.7.10"

    id("maven-publish")
    id("net.yakclient") version "1.0.1"
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

dependencies {
    implementation(yakclient.tweakerPartition.map { it.sourceSet.output })
    add("kapt", "net.yakclient:yakclient-preprocessor:1.0-SNAPSHOT")
    implementation("net.yakclient:client-api:1.0-SNAPSHOT")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
}


tasks.named<JavaExec>("launch") {
//   args(listOf(
//            "-Dlog4j.configurationFile=/Users/durganmcbroom/IdeaProjects/yakclient/example-extension/build/launch/mc/1.20.1/logging.xml"
//    )
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
}

yakclient {
    model {
        groupId = "net.yakclient.extensions"
        name = "example-extension"
        version = "1.0-SNAPSHOT"

        packagingType = "jar"
        extensionClass = "net.yakclient.extensions.example.ExampleExtension"
    }

    tweakerPartition {
        entrypoint.set("net.yakclient.extensions.example.tweaker.ExampleTweaker")
        dependencies {
            implementation("net.yakclient.components:ext-loader:1.0-SNAPSHOT")
            implementation("net.yakclient:boot:1.0-SNAPSHOT")
            implementation("net.yakclient:archives:1.1-SNAPSHOT")
            implementation("com.durganmcbroom:jobs:1.0-SNAPSHOT")
            implementation("com.durganmcbroom:artifact-resolver-simple-maven:1.0-SNAPSHOT")
            implementation("com.durganmcbroom:artifact-resolver:1.0-SNAPSHOT")
        }
    }

    partitions {
        create("latest") {
            dependencies {
                implementation(tweakerPartition.get().sourceSet.output)
                implementation("net.yakclient:archives:1.1-SNAPSHOT") {
                    isChanging = true
                }
                compileOnly("net.yakclient:boot:1.0-SNAPSHOT")
                implementation("net.yakclient:common-util:1.0-SNAPSHOT")

                add("kaptLatest", "net.yakclient:yakclient-preprocessor:1.0-SNAPSHOT")
                "annotationProcessor"("net.yakclient:yakclient-preprocessor:1.0-SNAPSHOT")
                implementation(main)
                minecraft("1.20.1")
                implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
                implementation("net.yakclient:client-api:1.0-SNAPSHOT")
            }

            supportedVersions.addAll(listOf("1.20.1", "1.19.2"))
        }

        create("1.19.2") {
            dependencies {
                minecraft("1.19.2")
                add("kapt1.19.2", "net.yakclient:yakclient-preprocessor:1.0-SNAPSHOT")

                implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
                implementation("net.yakclient:client-api:1.0-SNAPSHOT")
            }

            supportedVersions.addAll(listOf("1.18"))
        }
    }

    extension("net.yakclient.extensions:resource-tweaker:1.0-SNAPSHOT")
}

publishing {
    publications {
        create<MavenPublication>("prod") {
            artifact(tasks.jar)
            artifact(tasks.generateErm) {
                classifier = "erm"
            }

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