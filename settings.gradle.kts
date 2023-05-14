rootProject.name = "example-extension"

pluginManagement {
    repositories {
        mavenCentral()
        maven {
            isAllowInsecureProtocol = true
            url = uri("http://maven.yakclient.net/snapshots")
        }
        gradlePluginPortal()
        mavenLocal()

    }
}