# TelandClient Fabric Modu

Minecraft **1.20.x** ve **1.21.x** uyumlu Fabric modu.  
Sıfır kurulum — sadece `.jar` dosyasını `mods/` klasörüne at.

## Özellikler

| Özellik | Nasıl açılır? |
|---------|--------------|
| 🎭 Kozmetikler (Pelerin / Şapka / İz / Kanat) | **Sağ Shift** → Kozmetikler sekmesi |
| 👥 Arkadaşlar + LAN davet | **Sağ Shift** → Arkadaşlar sekmesi |
| 🏷 **[TC]** HUD rozeti | Otomatik (sağ alt köşe) |
| 🎨 Pelerin render | Aktif pelerin seçilince otomatik |

## Kurulum (Oyuncu)

1. [Releases](../../releases) sayfasından son `.jar` dosyasını indir
2. `.minecraft/mods/` klasörüne koy
3. [Fabric Loader](https://fabricmc.net/use/) + [Fabric API](https://modrinth.com/mod/fabric-api) gereklidir

## Desteklenen Sürümler

| Minecraft | Fabric API |
|-----------|-----------|
| 1.20.1    | 0.92.3+   |
| 1.21.1    | 0.107.0+  |

1.21.x için `gradle.properties` içinde:
```properties
minecraft_version=1.21.1
yarn_mappings=1.21.1+build.3
fabric_version=0.107.0+1.21.1
```

## Derleme (Geliştirici)

```bash
git clone https://github.com/KULLANICI_ADIN/teland-fabric
cd teland-fabric
# Java 17 ve Gradle gerekli
gradle build
# Çıktı: build/libs/teland-fabric-1.0.0.jar
```

GitHub'a push ettiğinde Actions otomatik derler.  
`v1.0.0` gibi bir tag oluşturduğunda otomatik Release çıkar.

## Config Dosyası

`.minecraft/config/telandclient/cosmetics.json`
```json
{
  "activeCape": "teland_cape",
  "activeHat": null,
  "activeTrail": null,
  "activeWings": null,
  "showCapeToOthers": true
}
```

## Lisans

MIT
