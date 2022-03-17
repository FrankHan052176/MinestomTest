package me.frankhan2004.test;

import me.frankhan2004.test.biomes.BiomesManager;
import me.frankhan2004.test.utils.IOUtil;
import me.frankhan2004.test.worlds.OverWorldChunkGenerator;
import me.frankhan2004.test.worlds.OverWorldChunkSupplier;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.world.DimensionTypeManager;

import java.util.Random;

public class Server {
    private static MinecraftServer minecraftServer;
    private static InstanceManager instanceManager;
    private static DimensionTypeManager dimensionTypeManager;
    private static InstanceContainer world;
    private static IOUtil io;
    public static void main(String[] args) {
        // 初始化
        minecraftServer = MinecraftServer.init();
        dimensionTypeManager = MinecraftServer.getDimensionTypeManager();
        io = new IOUtil();
        instanceManager = MinecraftServer.getInstanceManager();
        // IO
        BiomesManager.loadBiomes();
        // 创建世界
        world = instanceManager.createInstanceContainer();
        // 设置世界生成器
        long seed = new Random().nextLong();
        world.setChunkGenerator(new OverWorldChunkGenerator(seed));
        world.setChunkSupplier(new OverWorldChunkSupplier(seed));

        // Add an event callback to specify the spawning instance (and the spawn position)
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(world);
            player.setRespawnPoint(new Pos(0, 100, 0));
        });

        // Start the server on port 25565
        minecraftServer.start("0.0.0.0", 25565);
    }
    public static MinecraftServer getMinecraftServer() {
        return minecraftServer;
    }
    public static InstanceManager getInstanceManager() {
        return instanceManager;
    }
    public static IOUtil getIO() {
        return io;
    }
}
