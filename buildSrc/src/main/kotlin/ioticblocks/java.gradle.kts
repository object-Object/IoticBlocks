// A convention plugin that should be applied to all build scripts.

package ioticblocks

import gradle.kotlin.dsl.accessors._c04d8e8542fbcc88acb8c750bb46ff86.dokka
import gradle.kotlin.dsl.accessors._c04d8e8542fbcc88acb8c750bb46ff86.dokkaSourceSets
import gradle.kotlin.dsl.accessors._c04d8e8542fbcc88acb8c750bb46ff86.javaMain
import gradle.kotlin.dsl.accessors._c04d8e8542fbcc88acb8c750bb46ff86.main
import libs

plugins {
    java
    kotlin("jvm")
    id("architectury-plugin")
    id("org.jetbrains.dokka")
}

val mavenGroup: String by project
val modVersion: String by project
val minecraftVersion: String by project
val javaVersion = libs.versions.java.get().toInt()
val publishMavenRelease = System.getenv("PUBLISH_MAVEN_RELEASE") == "true"

group = mavenGroup
version = "$modVersion+$minecraftVersion"
if (!publishMavenRelease) {
    version = "$version-SNAPSHOT"
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://maven.blamejared.com") }
    maven { url = uri("https://maven.fabricmc.net/") }
    maven { url = uri("https://maven.ladysnake.org/releases") } // Cardinal Components
    maven { url = uri("https://maven.minecraftforge.net/") }
    maven { url = uri("https://maven.parchmentmc.org") }
    maven { url = uri("https://maven.shedaniel.me") }
    maven { url = uri("https://maven.terraformersmc.com/releases") }
    maven { url = uri("https://maven.theillusivec4.top") } // Caelus
    maven { url = uri("https://thedarkcolour.github.io/KotlinForForge") }
    exclusiveContent {
        filter {
            includeGroup("maven.modrinth")
        }
        forRepository {
            maven { url = uri("https://api.modrinth.com/maven") }
        }
    }
    exclusiveContent {
        filter {
            includeGroup("libs")
        }
        forRepository {
            flatDir { dir(rootProject.file("libs")) }
        }
    }
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
    withSourcesJar()
    withJavadocJar()
}

kotlin {
    jvmToolchain(javaVersion)
}

tasks {
    compileJava {
        options.apply {
            encoding = "UTF-8"
            release = javaVersion
        }
    }

    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    withType<GenerateModuleMetadata>().configureEach {
        enabled = false
    }

    javadoc {
        options {
            this as StandardJavadocDocletOptions
            addStringOption("Xdoclint:none", "-quiet")
        }
    }

    processResources {
        exclude(".cache")
    }

    processTestResources {
        exclude(".cache")
    }
}

val githubRepository = System.getenv("GITHUB_REPOSITORY") ?: "object-Object/IoticBlocks"
val githubRef = System.getenv("GITHUB_SHA") ?: "main"

dokka {
    dokkaSourceSets {
        configureEach {
            perPackageOption {
                suppress = true
            }
            perPackageOption {
                matchingRegex = """.*\bapi\b.*"""
                suppress = false
            }
            sourceLink {
                val projectPath = projectDir.relativeTo(rootProject.projectDir)
                remoteUrl = uri("https://github.com/$githubRepository/tree/$githubRef/$projectPath")
                remoteLineSuffix = "#L"
            }
        }
    }
    pluginsConfiguration {
        html {
            templatesDir = rootProject.file("dokka/templates")
            customStyleSheets = rootProject.fileTree("dokka/styles").matching { include("**/*.css") }
            customAssets.from(
                project(":common").file("src/main/resources/assets/ioticblocks/icon.png"),
            )
        }
    }
}
