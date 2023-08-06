import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("fabric-loom") version("1.2-SNAPSHOT")
    kotlin("jvm") version "1.8.21"
}

base { archivesName.set("kzeaddon-fabric") }
group = "mcpc.tedo0627.kzeaddon.fabric"

repositories {
    maven("https://maven.terraformersmc.com/releases/")
    maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
}

dependencies {
    minecraft("com.mojang:minecraft:1.19.4")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:0.14.21")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.82.0+1.19.4")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.9.4+kotlin.1.8.21")

    modImplementation("software.bernie.geckolib:geckolib-fabric-1.19.4:4.2")

    modApi("com.terraformersmc:modmenu:6.2.1")
}

tasks {
    runClient {
        args("--username", "Dev")
    }
    val javaVersion = JavaVersion.VERSION_17
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = javaVersion.toString()
        targetCompatibility = javaVersion.toString()
        options.release.set(javaVersion.toString().toInt())
    }
    withType<KotlinCompile> { kotlinOptions.jvmTarget = javaVersion.toString() }
    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion.toString()))
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        withSourcesJar()
    }
}