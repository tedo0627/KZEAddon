plugins {
    id("java")
    id("fabric-loom") version "0.10-SNAPSHOT"
}

group = "mcpc.tedo0627.kzeaddon"
tasks.jar {
    archiveBaseName.set("kzeaddon-fabric")
}

repositories {
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:1.17.1")
    mappings("net.fabricmc:yarn:1.17.1+build.63:v2")
    modImplementation("net.fabricmc:fabric-loader:0.12.12")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.42.1+1.17")
}

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}