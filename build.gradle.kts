plugins {
    kotlin("jvm") version "1.7.10"

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
        val main = create("main") {
            dependencies {
                add( "kapt", "net.yakclient:yakclient-preprocessor:1.0-SNAPSHOT")

                implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
            }
        }

        create("latest") {
            dependencies {
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
                add( "kapt1.19.2", "net.yakclient:yakclient-preprocessor:1.0-SNAPSHOT")

                implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
                implementation("net.yakclient:client-api:1.0-SNAPSHOT")
            }

            supportedVersions.addAll(listOf("1.18"))
        }
    }
}



java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}