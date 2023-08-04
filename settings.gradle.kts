pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://maven.minecraftforge.net")
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("fabric-loom").version("1.2-SNAPSHOT")
        kotlin("jvm").version("1.8.21")
    }
}

rootProject.name = "kzeaddon"

val list = listOf(
    "fabric",
    "forge",
    "forge-bundle"
)
for (str in list) {
    val name = "kzeaddon-$str"
    include(name)
    findProject(":$name")?.projectDir = file(str)
}
