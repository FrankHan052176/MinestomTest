package me.frankhan2004.test.worlds;

import de.articdive.jnoise.JNoise;
import de.articdive.jnoise.fade_functions.FadeFunction;
import de.articdive.jnoise.interpolation.Interpolation;
import me.frankhan2004.test.worlds.populators.BiomesPopulator;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OverWorldChunkGenerator implements ChunkGenerator {
    //用于给区块设置地形
    private final JNoise heightNoise;
    public OverWorldChunkGenerator(long seed) {
        heightNoise = JNoise.newBuilder().perlin()
                .setFrequency(0.5)
                .setSeed(seed)
                .setFadeFunction(FadeFunction.SMOOTHSTEP)
                .setInterpolation(Interpolation.COSINE)
                .build();
    }

    @Override
    public void generateChunkData(@NotNull ChunkBatch batch, int chunkX, int chunkZ) {
        for (int x = 0;x <= 15;x++) for (int z = 0;z <= 15;z++) {
            int px = chunkX*16+x;
            int pz = chunkZ*16+z;
            setBlocks(x,z, (int) heightNoise.getNoise(px,pz),batch);
        }
    }

    @Override
    public @Nullable List<ChunkPopulator> getPopulators() {
        return new ArrayList<>(List.of(new BiomesPopulator(heightNoise)));
    }
    private void setBlocks(int x, int z, int y, ChunkBatch batch) {
        for (int i = -64;i < y;i++) {
            batch.setBlock(x,i,z,Block.STONE);
        }
        if (y < 64) {
            for (int i = y;i < 64;i++) {
                batch.setBlock(x,i,z,Block.WATER);
            }
        }
    }
}
