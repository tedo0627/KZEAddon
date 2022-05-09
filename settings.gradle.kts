pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "kzeaddon"

val map = mapOf(
    "kzeaddon-fabric" to "fabric",
    "kzeaddon-forge" to "forge"
)
for (entry in map) {
    include(entry.key)
    findProject(":${entry.key}")?.projectDir = file(entry.value)
}
