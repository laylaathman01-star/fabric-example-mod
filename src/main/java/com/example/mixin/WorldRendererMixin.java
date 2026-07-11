package com.example.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.example.SusChunks;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void onRenderWorld(Render2DContext context, MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f projectionMatrix, CallbackInfo ci) {
        if (!SusChunks.enabled) return;

        Vec3d cameraPos = camera.getPos();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        synchronized (SusChunks.SUS_CHUNKS) {
            for (ChunkPos pos : SusChunks.SUS_CHUNKS) {
                double minX = (pos.x << 4) - cameraPos.x;
                double minZ = (pos.z << 4) - cameraPos.z;
                double maxX = minX + 16;
                double maxZ = minZ + 16;
                double minY = -64 - cameraPos.y;
                double maxY = 320 - cameraPos.y;

                bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
                
                bufferBuilder.vertex(minX, minY, minZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(maxX, minY, minZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(maxX, minY, minZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(maxX, minY, maxZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(maxX, minY, maxZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(minX, minY, maxZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(minX, minY, maxZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(minX, minY, minZ).color(255, 0, 0, 255).next();

                bufferBuilder.vertex(minX, maxY, minZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(maxX, maxY, minZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(maxX, maxY, minZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(maxX, maxY, maxZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(maxX, maxY, maxZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(minX, maxY, maxZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(minX, maxY, maxZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(minX, maxY, minZ).color(255, 0, 0, 255).next();

                bufferBuilder.vertex(minX, minY, minZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(minX, maxY, minZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(maxX, minY, minZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(maxX, maxY, minZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(maxX, minY, maxZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(maxX, maxY, maxZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(minX, minY, maxZ).color(255, 0, 0, 255).next();
                bufferBuilder.vertex(minX, maxY, maxZ).color(255, 0, 0, 255).next();

                tessellator.draw();
            }
        }
        RenderSystem.disableBlend();
        RenderSystem.enableCull();
    }
}
