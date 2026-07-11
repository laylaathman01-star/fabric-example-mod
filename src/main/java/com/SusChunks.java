package com.example;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;
import java.util.HashSet;
import java.util.Set;

public class SusChunks {
    public static final Set<ChunkPos> SUS_CHUNKS = new HashSet<>();
    public static boolean enabled = true;

    public static void checkChunk(WorldChunk chunk) {
        if (chunk == null) return;

        int clusterCount = 0;
        ChunkPos chunkPos = chunk.getPos();
        
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = -64; y < 320; y++) { 
                    BlockPos pos = chunkPos.getBlockPos(x, y, z);
                    if (chunk.getBlockState(pos).isOf(Blocks.AMETHYST_CLUSTER)) {
                        clusterCount++;
                    }
                }
            }
        }

        synchronized (SUS_CHUNKS) {
            if (clusterCount >= 10 && clusterCount <= 15) {
                SUS_CHUNKS.add(chunkPos);
            } else {
                SUS_CHUNKS.remove(chunkPos);
            }
        }
    }
}
