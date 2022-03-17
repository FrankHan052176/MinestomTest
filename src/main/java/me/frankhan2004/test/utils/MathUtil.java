package me.frankhan2004.test.utils;

import net.minestom.server.world.biomes.Biome;

import java.util.Arrays;

public class MathUtil {
    public static byte[] toHH(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }
    public static long getBlockIndex(int x, int y, int z) {
        byte[] byteX = toHH(x);
        byte[] byteY = toHH(y);
        byte[] byteZ = toHH(z);
        byte[] bytes = new byte[12];
        //bytes追加
        for (int i = 11;i >= 0;i--) {
            if (i >= 8) {
                bytes[i] = byteX[i-8];
            }else if (i >= 4) {
                bytes[i] = byteY[i-4];
            }else {
                bytes[i] = byteZ[i];
            }
        }
        return Arrays.toString(bytes).hashCode();
    }
    public static int precipitationValue(Biome.Precipitation precipitation) {
        switch (precipitation) {
            case RAIN -> {return 1;}
            case SNOW -> {return 2;}
            default -> {return 0;}
        }
    }
}
