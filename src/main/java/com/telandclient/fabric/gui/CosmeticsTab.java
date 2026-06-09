package com.telandclient.fabric.gui;

import com.telandclient.fabric.config.CosmeticsConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CosmeticsTab {

    private final TelandScreen screen;
    private final int x, y, w, h;

    private static final String[] CAPES   = {"Yok", "teland_cape", "red_cape", "gold_cape"};
    private static final String[] HATS    = {"Yok", "santa_hat", "crown", "headphones"};
    private static final String[] TRAILS  = {"Yok", "sparkle", "flame", "rainbow"};
    private static final String[] WINGS   = {"Yok", "angel_wings", "dragon_wings"};

    public CosmeticsTab(TelandScreen screen, int x, int y, int w, int h) {
        this.screen = screen; this.x = x; this.y = y; this.w = w; this.h = h;
    }

    public void init() {
        CosmeticsConfig cfg = CosmeticsConfig.get();

        // Pelerin seçici
        addCycleBtn("🧣 Pelerin:", CAPES,
            cfg.activeCape == null ? 0 : indexOf(CAPES, cfg.activeCape),
            x + 8, y + 10, 140,
            val -> { cfg.activeCape = "Yok".equals(val) ? null : val; CosmeticsConfig.save(); });

        // Şapka seçici
        addCycleBtn("🎩 Şapka:", HATS,
            cfg.activeHat == null ? 0 : indexOf(HATS, cfg.activeHat),
            x + 8, y + 40, 140,
            val -> { cfg.activeHat = "Yok".equals(val) ? null : val; CosmeticsConfig.save(); });

        // İz seçici
        addCycleBtn("✨ İz:", TRAILS,
            cfg.activeTrail == null ? 0 : indexOf(TRAILS, cfg.activeTrail),
            x + 8, y + 70, 140,
            val -> { cfg.activeTrail = "Yok".equals(val) ? null : val; CosmeticsConfig.save(); });

        // Kanat seçici
        addCycleBtn("🪽 Kanat:", WINGS,
            cfg.activeWings == null ? 0 : indexOf(WINGS, cfg.activeWings),
            x + 8, y + 100, 140,
            val -> { cfg.activeWings = "Yok".equals(val) ? null : val; CosmeticsConfig.save(); });

        // Başkalarına göster toggle
        screen.addBtn(ButtonWidget.builder(
            Text.literal("Pelerini Başkalarına Göster: " + (cfg.showCapeToOthers ? "§aAçık" : "§cKapalı")),
            b -> {
                cfg.showCapeToOthers = !cfg.showCapeToOthers;
                CosmeticsConfig.save();
                b.setMessage(Text.literal("Pelerini Başkalarına Göster: " + (cfg.showCapeToOthers ? "§aAçık" : "§cKapalı")));
            }
        ).dimensions(x + 8, y + 130, 200, 20).build());
    }

    private void addCycleBtn(String label, String[] options, int current, int bx, int by, int bw,
                              java.util.function.Consumer<String> onSelect) {
        final int[] idx = {Math.max(0, current)};
        screen.addBtn(ButtonWidget.builder(
            Text.literal(label + " " + options[idx[0]]),
            b -> {
                idx[0] = (idx[0] + 1) % options.length;
                onSelect.accept(options[idx[0]]);
                b.setMessage(Text.literal(label + " " + options[idx[0]]));
            }
        ).dimensions(bx, by, bw, 20).build());
    }

    public void render(DrawContext ctx, int mx, int my, float delta) {
        ctx.drawTextWithShadow(
            net.minecraft.client.MinecraftClient.getInstance().textRenderer,
            Text.literal("§7Kozmetiklerini buradan değiştirebilirsin."),
            x + 8, y + 165, 0x888888
        );
    }

    private int indexOf(String[] arr, String val) {
        if (val == null) return 0;
        for (int i = 0; i < arr.length; i++) if (arr[i].equals(val)) return i;
        return 0;
    }
}
