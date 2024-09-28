rootProject.name = "example-extension"

pluginManagement {
    repositories {
        mavenLocal()
        maven {
            url = uri("https://maven.extframework.dev/snapshots")
        }
        maven {
            url = uri("https://maven.extframework.dev/releases")
        }
        gradlePluginPortal()
    }
}
