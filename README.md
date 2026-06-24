# Farmer's Respite — 1.20.1

A Minecraft **1.20.1 / Forge** addon for [Farmer's Delight](https://www.curseforge.com/minecraft/mc-mods/farmers-delight) focused on brewing tea and coffee.

Original mod by **Umpaz** and **Probleyes**. This repository is maintained by [Craftorio](https://github.com/craftorio) as a 1.20.1 port and continuation.

## Features

- **Kettle** — a brewing station with its own recipe book category, fluid storage, and JEI integration
- **Tea & coffee crops** — wild bushes, farmable tea bushes (green / yellow / black leaves), and coffee that grows on other crops
- **Drinks** — teas, coffee, hot cocoa, apple cider, melon juice, and more; long and strong variants via brewing
- **Food** — coffee cake, rose hip pie, cookies, and related Farmer's Delight-style meals
- **Worldgen** — wild tea and coffee bushes in the overworld

## Branches

| Branch | Contents |
|---|---|
| `1.20.1` | **current** — Forge 1.20.1 port |
| `1.18.2` | earlier Forge port |

## Requirements

| | |
|---|---|
| Minecraft | 1.20.1 |
| Forge | 47.4.20+ |
| Farmer's Delight | 1.2.3+ |

JEI is optional at runtime but included in the dev environment for recipe debugging.

## Building

```sh
./gradlew build
```

Output: `build/libs/FarmersRespite-1.20.1-<mod_version>-<craftorio_version>.jar`

### Development

```sh
./gradlew runClient   # launch a test client
./gradlew runData     # run data generators
./gradlew clean build # full rebuild
```

Version numbers and dependency pins live in `gradle.properties`.

## Releases

GitHub Actions builds every push and pull request (`.github/workflows/build.yml`).

To publish a release, push a tag:

```sh
# matches jar name FarmersRespite-1.20.1-2.1.2-3.jar
git tag 2.1.2-3
git push origin 2.1.2-3

# also supported: 2.1.2+3, release/2.1.2-3, or plain 2.1.2
```

The release workflow (`.github/workflows/release.yml`) builds the jar and attaches it to a GitHub Release. You can also trigger it manually from the Actions tab.

## Configuration

Client/server settings in `config/farmersrespite-common.toml`:

```toml
[settings]
enableBoneMealTeaBush = false    # allow bonemeal on tea bushes
enableBoneMealCoffeeBush = false # allow bonemeal on coffee bushes
```

## Credits & licensing

Farmer's Respite was created by **Umpaz** and **Probleyes**; additional credits to **SoyTutta**.

Licensed under the **MIT License**. See the original project and Farmer's Delight for their respective terms.
