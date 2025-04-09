// A convention plugin that should be applied to all Minecraft-related subprojects, including common.

package ioticblocks

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

    @Suppress("UnstableApiUsage")
    mixin {
        // the default name includes both archivesName and the subproject, resulting in the platform showing up twice
        // default: ioticblocks-common-Common-refmap.json
        // fixed:   ioticblocks-common.refmap.json
        defaultRefmapName = "${base.archivesName.get()}.refmap.json"
    }
}

pkJson5 {
    autoProcessJson5 = true
    autoProcessJson5Flattening = true
}

dependencies {
    minecraft(libs.minecraft)

    @Suppress("UnstableApiUsage")
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

val localMavenUrl: String? = System.getenv("local_maven_url")

publishing {
    repositories {
        localMavenUrl?.let {
            maven {
                url = uri(it)
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            artifactId = base.archivesName.get()
            from(components["java"])
        }
    }
}
