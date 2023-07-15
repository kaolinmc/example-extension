rootProject.name = "example-extension"

pluginManagement {
    repositories {
        maven {
            isAllowInsecureProtocol = true
            url = uri("http://maven.yakclient.net/snapshots")
        }
        gradlePluginPortal()
    }
}