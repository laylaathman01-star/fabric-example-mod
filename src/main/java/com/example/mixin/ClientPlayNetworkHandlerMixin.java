package com.example.mixin;

import com.example.SusChunks;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(method = "onChunkData", at = @At("TAIL"))
    private void onChunkDataPacket(ChunkDataS2CPacket packet, CallbackInfo ci) {
        if (SusChunks.enabled) {
            net.minecraft.client.MinecraftClient mc = net.minecraft.client.MinecraftClient.getInstance();
            if (mc.world != null) {
                WorldChunk chunk = mc.world.getChunk(packet.getX(), packet.getZ());
                if (chunk != null) {
                    new Thread(() -> SusChunks.checkChunk(chunk)).start();
                }
            }
        }
    }
}
