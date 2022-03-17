package me.frankhan2004.test.biomes;

import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Biomes {
    private final ConcurrentHashMap<Integer,String> id2SurfaceBuilders = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long,Integer> index2ID = new ConcurrentHashMap<>();
    public Biomes() {}
    private long getIndex(int heat, int humidity) {
        return (long)heat << 32 | (long)humidity & 4294967295L;
    }
    public void put(int heat, int humidity, int id, String sb) {
        long index = getIndex(heat,humidity);
        index2ID.put(index,id);
        id2SurfaceBuilders.put(id,sb);
    }
    public int find(int heat, int humidity) {
        List<Long> src = new ArrayList<>(index2ID.keySet());
        long index = getIndex(heat, humidity);
        if (src.isEmpty()) {
            return -1;
        }
        if (src.size() == 1) {
            return index2ID.get(src.get(0));
        }
        long minDifference = Math.abs(src.get(0) - index);
        int minIndex = 0;
        for (int i = 1; i < src.size(); i++) {
            long temp = Math.abs(src.get(i) - index);
            if (temp < minDifference) {
               minIndex = i;
               minDifference = temp;
            }
        }
        return index2ID.get(src.get(minIndex));
    }
    public void surfaceBuilder(int x, int z, int height, int id, ChunkBatch batch) {
        switch (id2SurfaceBuilders.get(id)) {
            case "minecraft:grass": default: {
                if (new Random(1).nextBoolean()) batch.setBlock(x,z,height+1, Block.GRASS);
                batch.setBlock(x,z,height, Block.GRASS_BLOCK);
                batch.setBlock(x,z,height-1, Block.DIRT);
                batch.setBlock(x,z,height-2, Block.DIRT);
                break;
            }
        }
    }
}
