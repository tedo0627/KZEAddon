import net.minecraftforge.gradle.userdev.tasks.JarJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("net.minecraftforge.gradle") version "6.0.4"
    kotlin("jvm") version "1.8.21"
}

base { archivesName.set("kzeaddon-forge-bundle") }
group = "mcpc.tedo0627.kzeaddon.forge-bundle"

jarJar.enable()

repositories {
    mavenCentral()
    maven("https://thedarkcolour.github.io/KotlinForForge/")
    maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
}

dependencies {
    minecraft("net.minecraftforge:forge:1.19.4-45.0.66")

    jarJar(project(":kzeaddon-forge")) {
        exclude("*", "*")
        jarJar.ranged(this, "[3.0,4.0)")
    }

    jarJar("thedarkcolour:kotlinforforge:[4.0,5.0)") {
        jarJar.pin(this, "4.2.0")
    }
    jarJar("software.bernie.geckolib:geckolib-forge-1.19.4:[4.0,5.0)") {
        jarJar.pin(this, "4.2")
    }
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
        archiveVersion.set("")
        archiveClassifier.set("slim")
        finalizedBy("reobfJar")

        manifest {
            attributes(
                "Implementation-Version" to version,
            )
        }
    }

    reobf {
        jarJar{}
    }

    withType<JarJar> {
        archiveVersion.set("")
        archiveClassifier.set("")
        finalizedBy("reobfJarJar")
    }
}
