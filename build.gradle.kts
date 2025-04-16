plugins {
    id("ioticblocks.java")
}

val minecraftVersion: String by project

// scuffed sanity check, because we need minecraftVersion to be in gradle.properties for the hexdoc plugin
libs.versions.minecraft.get().also {
    if (minecraftVersion != it) {
        throw IllegalArgumentException("Mismatched Minecraft version: gradle.properties ($minecraftVersion) != libs.versions.toml ($it)")
    }
}

architectury {
    minecraft = minecraftVersion
}

dependencies {
    dokka(project(":common"))
    dokka(project(":fabric"))
    dokka(project(":forge"))
}

tasks {
    register("runAllDatagen") {
        dependsOn(":forge:runCommonDatagen")
    }
}
