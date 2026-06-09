package com.telandclient.fabric;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelandFabric implements ModInitializer {

    public static final String MOD_ID = "telandclient";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("[TelandClient] Mod yüklendi!");
    }
}
