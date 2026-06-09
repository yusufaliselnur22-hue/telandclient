package com.telandclient.fabric.gui;

import com.telandclient.fabric.config.FriendsConfig;
import com.telandclient.fabric.friends.LanInviteManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import java.util.List;

public class FriendsTab {

    private final TelandScreen screen;
    private final int x, y, w, h;
    private TextFieldWidget inviteField;

    public FriendsTab(TelandScreen screen, int x, int y, int w, int h) {
        this.screen = screen; this.x = x; this.y = y; this.w = w; this.h = h;
    }

    public void init() {
        // LAN sunucusu aç
        screen.addBtn(ButtonWidget.builder(
            Text.literal(LanInviteManager.isHosting() ? "§aSunucu Açık ✓" : "🌐 LAN Aç"),
            b -> {
                if (!LanInviteManager.isHosting()) {
                    String code = LanInviteManager.startHosting();
                    b.setMessage(Text.literal("§aDavet Kodu: §e" + code));
                } else {
                    LanInviteManager.stopHosting();
                    b.setMessage(Text.literal("🌐 LAN Aç"));
                }
            }
        ).dimensions(x + 8, y + 10, 140, 20).build());

        // Davet kodu alanı + katıl butonu
        inviteField = new TextFieldWidget(
            MinecraftClient.getInstance().textRenderer,
            x + 8, y + 40, 200, 18,
            Text.literal("Davet kodu...")
        );
        inviteField.setPlaceholder(Text.literal("§7Davet kodu yapıştır..."));

        screen.addBtn(ButtonWidget.builder(Text.literal("▶ Katıl"), b -> {
            String code = inviteField.getText().trim();
            if (!code.isEmpty()) LanInviteManager.joinByCode(code);
        }).dimensions(x + 214, y + 40, 50, 18).build());

        // Arkadaş ekle
        screen.addBtn(ButtonWidget.builder(Text.literal("+ Arkadaş Ekle"), b -> {
            // TODO: arkadaş arama ekranı
        }).dimensions(x + 8, y + 68, 140, 20).build());
    }

    public void render(DrawContext ctx, int mx, int my, float delta) {
        var tr = MinecraftClient.getInstance().textRenderer;
        if (inviteField != null) inviteField.render(ctx, mx, my, delta);

        // Arkadaş listesi
        List<FriendsConfig.Friend> friends = FriendsConfig.get().friends;
        ctx.drawTextWithShadow(tr,
            Text.literal("§7Arkadaşlar (" + friends.size() + ")"),
            x + 8, y + 96, 0xAAAAAA);

        int fy = y + 110;
        for (FriendsConfig.Friend f : friends) {
            if (fy > y + h - 20) break;
            String statusDot = "online".equals(f.status) ? "§a●" :
                               "ingame".equals(f.status) ? "§e●" : "§8●";
            ctx.drawTextWithShadow(tr,
                Text.literal(statusDot + " §f" + f.username), x + 12, fy, 0xFFFFFF);
            fy += 14;
        }

        if (friends.isEmpty()) {
            ctx.drawTextWithShadow(tr,
                Text.literal("§7Henüz arkadaş yok."),
                x + 12, y + 112, 0x666666);
        }
    }
}
