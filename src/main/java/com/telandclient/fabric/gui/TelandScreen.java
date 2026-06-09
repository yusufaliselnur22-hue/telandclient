package com.telandclient.fabric.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class TelandScreen extends Screen {

    private final Screen parent;
    private int activeTab = 0; // 0=Kozmetikler, 1=Arkadaşlar

    private CosmeticsTab cosmeticsTab;
    private FriendsTab   friendsTab;

    public TelandScreen(Screen parent) {
        super(Text.literal("TelandClient"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int panelW = 320, panelH = 240;
        int panelX = (width  - panelW) / 2;
        int panelY = (height - panelH) / 2;

        // Tab butonları
        addDrawableChild(ButtonWidget.builder(Text.literal("🎭 Kozmetikler"), b -> {
            activeTab = 0; clearAndInit();
        }).dimensions(panelX, panelY - 24, 155, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("👥 Arkadaşlar"), b -> {
            activeTab = 1; clearAndInit();
        }).dimensions(panelX + 161, panelY - 24, 155, 20).build());

        // Kapat butonu
        addDrawableChild(ButtonWidget.builder(Text.literal("✕ Kapat"), b -> {
            close();
        }).dimensions(panelX + panelW - 60, panelY + panelH + 4, 60, 16).build());

        // Aktif sekme
        if (activeTab == 0) {
            cosmeticsTab = new CosmeticsTab(this, panelX, panelY, panelW, panelH);
            cosmeticsTab.init();
        } else {
            friendsTab = new FriendsTab(this, panelX, panelY, panelW, panelH);
            friendsTab.init();
        }
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        // Arka plan (yarı saydam koyu panel)
        renderBackgroundTexture(ctx);
        int panelW = 320, panelH = 240;
        int panelX = (width  - panelW) / 2;
        int panelY = (height - panelH) / 2;
        ctx.fill(panelX - 4, panelY - 30, panelX + panelW + 4, panelY + panelH + 24, 0xCC111111);
        ctx.fill(panelX - 4, panelY - 30, panelX + panelW + 4, panelY - 28, 0xFF5B9AFF);

        // Başlık
        ctx.drawCenteredTextWithShadow(textRenderer,
            Text.literal("§b§lTELAND§f§lCLIENT"), width / 2, panelY - 42, 0xFFFFFF);
        ctx.drawCenteredTextWithShadow(textRenderer,
            Text.literal(activeTab == 0 ? "§7[ Kozmetikler ]" : "§7[ Arkadaşlar ]"),
            width / 2, panelY - 32, 0xAAAAAA);

        super.render(ctx, mouseX, mouseY, delta);

        // Aktif sekme render
        if (activeTab == 0 && cosmeticsTab != null)
            cosmeticsTab.render(ctx, mouseX, mouseY, delta);
        else if (activeTab == 1 && friendsTab != null)
            friendsTab.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() { return false; }

    @Override
    public void close() {
        assert client != null;
        client.setScreen(parent);
    }

    /** Butonu dışarıdan ekleyebilmek için public wrapper */
    public void addBtn(net.minecraft.client.gui.widget.ClickableWidget w) {
        addDrawableChild(w);
    }
}
