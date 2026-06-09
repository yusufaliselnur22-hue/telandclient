package com.telandclient.fabric;

import com.telandclient.fabric.config.CosmeticsConfig;
import com.telandclient.fabric.config.FriendsConfig;
import com.telandclient.fabric.gui.TelandScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class TelandFabricClient implements ClientModInitializer {

    public static KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        // Sağ Shift → TelandClient menüsü
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.telandclient.menu",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.telandclient"
        ));

        // Config dosyalarını yükle
        CosmeticsConfig.load();
        FriendsConfig.load();

        // Her tick kontrol et
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new TelandScreen(client.currentScreen));
                }
            }
        });

        TelandFabric.LOGGER.info("[TelandClient] Client başlatıldı. Sağ Shift = Menü");
    }
}
