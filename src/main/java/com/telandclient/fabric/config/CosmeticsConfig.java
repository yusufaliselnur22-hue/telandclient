package com.telandclient.fabric.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.io.*;
import java.nio.file.*;

public class CosmeticsConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_FILE = FabricLoader.getInstance()
        .getConfigDir().resolve("telandclient/cosmetics.json");

    public String activeCape   = "teland_cape";
    public String activeHat    = null;
    public String activeTrail  = null;
    public String activeWings  = null;
    public boolean showCapeToOthers = true;

    private static CosmeticsConfig instance = new CosmeticsConfig();

    public static CosmeticsConfig get() { return instance; }

    public static void load() {
        try {
            Files.createDirectories(CONFIG_FILE.getParent());
            if (Files.exists(CONFIG_FILE)) {
                try (Reader r = Files.newBufferedReader(CONFIG_FILE)) {
                    instance = GSON.fromJson(r, CosmeticsConfig.class);
                }
            } else {
                save();
            }
        } catch (IOException e) {
            instance = new CosmeticsConfig();
        }
    }

    public static void save() {
        try {
            Files.createDirectories(CONFIG_FILE.getParent());
            try (Writer w = Files.newBufferedWriter(CONFIG_FILE)) {
                GSON.toJson(instance, w);
            }
        } catch (IOException ignored) {}
    }
}
