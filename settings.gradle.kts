pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("fabric-loom").version("1.2-SNAPSHOT")
        kotlin("jvm").version("1.8.21")
    }
}

rootProject.name = "kzeaddon"

val map = mapOf(
    "kzeaddon-fabric" to "fabric",
    //"kzeaddon-forge" to "forge"
)
for (entry in map) {
    include(entry.key)
    findProject(":${entry.key}")?.projectDir = file(entry.value)
}
