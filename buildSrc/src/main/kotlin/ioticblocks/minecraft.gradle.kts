// A convention plugin that should be applied to all Minecraft-related subprojects, including common.

package ioticblocks

import kotlin.io.path.div
import libs

plugins {
    id("ioticblocks.java")

    `maven-publish`
    id("dev.architectury.loom")
    id("at.petra-k.pkpcpbp.PKJson5Plugin")
}

val modId: String by project
val platform: String by project

base.archivesName = "${modId}-$platform"

loom {
    silentMojangMappingsLicense()
    accessWidenerPath = project(":common").file("src/main/resources/ioticblocks.accesswidener")
}

pkJson5 {
    autoProcessJson5 = true
    autoProcessJson5Flattening = true
}

dependencies {
    minecraft(libs.minecraft)

    mappings(loom.layered {
        officialMojangMappings()
        parchment(libs.parchment)
    })

    annotationProcessor(libs.bundles.asm)
}

sourceSets {
    main {
        kotlin {
            srcDir(file("src/main/java"))
        }
        resources {
            srcDir(file("src/generated/resources"))
        }
    }
}

tasks {
    val artifactsTask = register<Copy>("githubArtifacts") {
        from(remapJar, remapSourcesJar, get("javadocJar"))
        into(rootDir.toPath() / "build" / "githubArtifacts")
    }

    build {
        dependsOn(artifactsTask)
    }
}
