package me.frankhan2004.test.worlds.populators;

import de.articdive.jnoise.JNoise;
import me.frankhan2004.test.biomes.BiomesManager;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.batch.ChunkBatch;

public class BiomesPopulator implements ChunkPopulator {
    private final JNoise height;
    public BiomesPopulator(JNoise height) {
        this.height = height;
    }

    @Override
    public void populateChunk(ChunkBatch batch, Chunk chunk) {
        for (int x = 0;x < 16;x++) for (int z = 0;z<16;z++) {
            int height = (int) this.height.getNoise(x+ chunk.getChunkX()*16,z+chunk.getChunkZ()*16);
            BiomesManager.biomes.surfaceBuilder(x,z,height,chunk.getBiome(x,height,z).id(),batch);
        }
    }
}
