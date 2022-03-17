package me.frankhan2004.test.worlds;

import de.articdive.jnoise.JNoise;
import me.frankhan2004.test.biomes.BiomesManager;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.DynamicChunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.chunk.ChunkSupplier;
import org.jetbrains.annotations.NotNull;

public class OverWorldChunkSupplier implements ChunkSupplier {
    private JNoise heatNoise;
    private JNoise humidityNoise;
    public OverWorldChunkSupplier(long seed) {
        heatNoise = JNoise.newBuilder().superSimplex().setSeed(seed).setFrequency(0.3).build();
        humidityNoise = JNoise.newBuilder().fastSimplex().setSeed(seed).setFrequency(0.3).build();
    }

    @Override
    public @NotNull Chunk createChunk(@NotNull Instance instance, int chunkX, int chunkZ) {
        Chunk chunk = new DynamicChunk(instance,chunkX,chunkZ);
        for (int x = 0;x <= 15;x++) for (int y = -64;y <= 194;y++) for (int z = 0;z <= 15;z++) {
            int px = chunkX*16+x;
            int pz = chunkZ*16+z;
            double heat = heatNoise.getNoise(px,y,pz)*10000;
            double humidity = humidityNoise.getNoise(px,y,pz)*10000;
            chunk.setBiome(px,y,pz,BiomesManager.getBiome((int) heat, (int) humidity));
        }
        return chunk;
    }
}
