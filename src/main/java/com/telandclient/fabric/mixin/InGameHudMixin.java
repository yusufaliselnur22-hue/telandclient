package com.telandclient.fabric.mixin;

import com.telandclient.fabric.render.NameTagHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void renderTelandOverlay(DrawContext ctx, float tickDelta, CallbackInfo ci) {
        NameTagHelper.renderHudBadge(ctx);
    }
}
