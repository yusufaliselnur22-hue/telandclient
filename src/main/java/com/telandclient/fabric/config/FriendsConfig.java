package com.telandclient.fabric.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FriendsConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_FILE = FabricLoader.getInstance()
        .getConfigDir().resolve("telandclient/friends.json");

    public List<Friend> friends = new ArrayList<>();

    private static FriendsConfig instance = new FriendsConfig();

    public static FriendsConfig get() { return instance; }

    public static void load() {
        try {
            Files.createDirectories(CONFIG_FILE.getParent());
            if (Files.exists(CONFIG_FILE)) {
                try (Reader r = Files.newBufferedReader(CONFIG_FILE)) {
                    instance = GSON.fromJson(r, FriendsConfig.class);
                    if (instance == null) instance = new FriendsConfig();
                }
            } else {
                save();
            }
        } catch (IOException e) {
            instance = new FriendsConfig();
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

    public static class Friend {
        public String id;
        public String username;
        public String uuid;
        public String status; // "online" | "offline" | "ingame"
        public String inviteCode;

        public Friend(String id, String username, String uuid) {
            this.id = id; this.username = username; this.uuid = uuid;
            this.status = "offline"; this.inviteCode = null;
        }
    }
}
