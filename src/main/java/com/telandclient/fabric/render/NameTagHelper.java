package com.telandclient.fabric.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

/**
 * HUD'a küçük [TC] rozeti çizer (sağ alt köşe).
 */
public class NameTagHelper {

    public static void renderHudBadge(DrawContext ctx) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        String badge = "§b[TC]";
        int x = mc.getWindow().getScaledWidth()  - mc.textRenderer.getWidth(badge) - 4;
        int y = mc.getWindow().getScaledHeight() - 12;
        ctx.drawTextWithShadow(mc.textRenderer, Text.literal(badge), x, y, 0xFFFFFF);
    }
}
