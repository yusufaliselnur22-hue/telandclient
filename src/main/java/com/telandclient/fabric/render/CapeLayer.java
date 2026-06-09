package com.telandclient.fabric.render;

import com.telandclient.fabric.config.CosmeticsConfig;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class CapeLayer extends FeatureRenderer<PlayerEntity, BipedEntityModel<PlayerEntity>> {

    // Texture'lar — assets/telandclient/textures/cape/<isim>.png
    private static Identifier getCapeTexture(String capeName) {
        return new Identifier("telandclient", "textures/cape/" + capeName + ".png");
    }

    public CapeLayer(FeatureRendererContext<PlayerEntity, BipedEntityModel<PlayerEntity>> ctx,
                     EntityModelLoader loader) {
        super(ctx);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                       int light, PlayerEntity entity, float limbAngle, float limbDistance,
                       float tickDelta, float animationProgress, float headYaw, float headPitch) {

        CosmeticsConfig cfg = CosmeticsConfig.get();
        if (cfg.activeCape == null || cfg.activeCape.isEmpty()) return;

        Identifier texture = getCapeTexture(cfg.activeCape);
        VertexConsumer vc = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(texture));

        matrices.push();
        // Sırta yerleştir (MC cape standart transform)
        matrices.translate(0.0, 0.0, 0.125);
        getContextModel().renderCape(matrices, vc, light, OverlayTexture.DEFAULT_UV);
        matrices.pop();
    }
}
