plugins {
    kotlin("jvm") version "1.7.10"
    id("signing")
    id("maven-publish")
    id("org.jetbrains.dokka") version "1.6.0"

    id("net.yakclient") version "1.0"
    kotlin("kapt") version "1.8.10"
}

group = "net.yakclient.extensions"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        isAllowInsecureProtocol = true
        url = uri("http://maven.yakclient.net/snapshots")
    }
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

dependencies {
}

yakclient {
    model {
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



task<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

task<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    from(tasks.dokkaJavadoc)
}

publishing {
    publications {
        create<MavenPublication>("example-extension-maven") {
            artifact(tasks["jar"])
            artifact(project.buildDir.toPath().resolve("libs/erm.json")).classifier = "erm"
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            artifactId = "example-extension"
        }
    }
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

//    publishing {
//        repositories {
//            if (!project.hasProperty("maven-user") || !project.hasProperty("maven-pass")) return@repositories
//
//            maven {
//                val repo = if (project.findProperty("isSnapshot") == "true") "snapshots" else "releases"
//
//                isAllowInsecureProtocol = true
//
//                url = uri("http://maven.yakclient.net/$repo")
//
//                credentials {
//                    username = project.findProperty("maven-user") as String
//                    password = project.findProperty("maven-pass") as String
//                }
//                authentication {
//                    create<BasicAuthentication>("basic")
//                }
//            }
//        }
//    }

    kotlin {
        explicitApi()
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation(kotlin("reflect"))
        testImplementation(kotlin("test"))
    }

    tasks.compileKotlin {
        destinationDirectory.set(tasks.compileJava.get().destinationDirectory.asFile.get())

        kotlinOptions {
            jvmTarget = "17"
        }
    }

    tasks.compileTestKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.compileJava {
        targetCompatibility = "17"
        sourceCompatibility = "17"
    }
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}