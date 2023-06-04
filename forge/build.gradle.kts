import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("net.minecraftforge.gradle") version "6.0.4"
    kotlin("jvm") version "1.8.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.spongepowered.mixin") version "0.7.+"
}

base { archivesName.set("kzeaddon-forge") }
group = "mcpc.tedo0627.kzeaddon.forge"

mixin {
    add(sourceSets.main.get(), "mixins.kzeaddon.refmap.json")

    config("kzeaddon.mixins.json")
}

repositories {
    maven("https://thedarkcolour.github.io/KotlinForForge/")
}

dependencies {
    minecraft("net.minecraftforge:forge:1.19.4-45.0.66")

    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")

    implementation("thedarkcolour:kotlinforforge:4.2.0")
}

minecraft {
    mappings("official", "1.19.4")

    runs {
        create("client") {
            workingDirectory(project.file("run"))

            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")
            property("forge.enabledGameTestNamespaces", "examplemod")

            mods {
                create("kzeaddon") {
                    source(sourceSets.main.get())
                }
            }
        }
    }
}

tasks {
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
    withType<Jar> {
        finalizedBy("reobfJar")
    }
}
