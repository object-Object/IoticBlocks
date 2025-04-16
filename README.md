# IoticBlocks

[![powered by hexdoc](https://img.shields.io/endpoint?url=https://hexxy.media/api/v0/badge/hexdoc?label=1)](https://ioticblocks.hexxy.media)

[CurseForge](https://curseforge.com/minecraft/mc-mods/ioticblocks) | [Modrinth](https://modrinth.com/mod/ioticblocks)

A [Hex Casting](https://github.com/FallingColors/HexMod) addon/library that adds patterns for reading and writing iotas to/from blocks, and an API for addon developers to easily add iota reading/writing support to their blocks.

Original implementations by [SamsTheNerd](https://github.com/SamsTheNerd) in [HexGloop](https://github.com/SamsTheNerd/HexGloop) and [FallingColors/HexMod#796](https://github.com/FallingColors/HexMod/pull/796).

## Maven

Build artifacts are published to the [BlameJared repository](https://maven.blamejared.com/gay/object/ioticblocks/) via [Jenkins](https://ci.blamejared.com/job/object-Object/job/IoticBlocks/) (the same repository as Hex Casting).

To depend on IoticBlocks, add something like this to your build script:

```groovy
repositories {
    maven { url = uri("https://maven.blamejared.com") }
}

dependencies {
    modApi("gay.object.ioticblocks:ioticblocks-$platform:$ioticblocksVersion+$minecraftVersion")
}
```

Full examples:

```groovy
// released versions
modApi("gay.object.ioticblocks:ioticblocks-common:1.0.0+1.20.1")
modApi("gay.object.ioticblocks:ioticblocks-fabric:1.0.0+1.20.1")
modApi("gay.object.ioticblocks:ioticblocks-forge:1.0.0+1.20.1")

// bleeding edge builds
modApi("gay.object.ioticblocks:ioticblocks-common:1.0.0+1.20.1-SNAPSHOT")
modApi("gay.object.ioticblocks:ioticblocks-fabric:1.0.0+1.20.1-SNAPSHOT")
modApi("gay.object.ioticblocks:ioticblocks-forge:1.0.0+1.20.1-SNAPSHOT")
```

Try to avoid using things outside of the `gay.object.ioticblocks.api` package, since they may change at any time.

## Attribution

* Original implementations: SamsTheNerd ([GitHub](https://github.com/SamsTheNerd), [Modrinth](https://modrinth.com/user/SamsTheNerd))
