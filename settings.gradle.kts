rootProject.name = "example-extension"

pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.kaolinmc.com/snapshots")
        }
        maven {
            url = uri("https://maven.kaolinmc.com/releases")
        }
        gradlePluginPortal()
        mavenLocal()
    }
}