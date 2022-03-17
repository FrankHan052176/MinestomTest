package me.frankhan2004.test.biomes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.frankhan2004.test.Server;
import me.frankhan2004.test.utils.MathUtil;
import net.minestom.server.MinecraftServer;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.biomes.Biome;
import net.minestom.server.world.biomes.BiomeEffects;
import net.minestom.server.world.biomes.BiomeManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BiomesManager {
    public static final Biomes biomes = new Biomes();
    public static final BiomeManager manager = MinecraftServer.getBiomeManager();
    public static final List<String> biomeList = new ArrayList<>(Arrays.asList(
            "badlands.json", "badlands_plateau.json", "bamboo_jungle.json", "bamboo_jungle_hills.json", "basalt_deltas.json",
            "beach.json", "birch_forest.json", "birch_forest_hills.json", "cold_ocean.json", "crimson_forest.json", "dark_forest.json",
            "dark_forest_hills.json", "deep_cold_ocean.json", "deep_frozen_ocean.json", "deep_lukewarm_ocean.json", "deep_ocean.json",
            "deep_warm_ocean.json", "desert.json", "desert_hills.json", "desert_lakes.json", "end_barrens.json", "end_highlands.json",
            "end_midlands.json", "eroded_badlands.json", "flower_forest.json", "forest.json", "frozen_ocean.json", "frozen_river.json",
            "giant_spruce_taiga.json", "giant_spruce_taiga_hills.json", "giant_tree_taiga.json", "giant_tree_taiga_hills.json",
            "gravelly_mountains.json", "ice_spikes.json", "jungle.json", "jungle_edge.json", "jungle_hills.json", "lukewarm_ocean.json",
            "modified_badlands_plateau.json", "modified_gravelly_mountains.json", "modified_jungle.json", "modified_jungle_edge.json",
            "modified_wooded_badlands_plateau.json", "mountains.json", "mountain_edge.json", "mushroom_fields.json",
            "mushroom_field_shore.json", "nether_wastes.json", "ocean.json", "plains.json", "river.json", "savanna.json",
            "savanna_plateau.json", "shattered_savanna.json", "shattered_savanna_plateau.json", "small_end_islands.json",
            "snowy_beach.json", "snowy_mountains.json", "snowy_taiga.json", "snowy_taiga_hills.json", "snowy_taiga_mountains.json",
            "snowy_tundra.json", "soul_sand_valley.json", "stone_shore.json", "sunflower_plains.json", "swamp.json", "swamp_hills.json",
            "taiga.json", "taiga_hills.json", "taiga_mountains.json", "tall_birch_forest.json", "tall_birch_hills.json", "the_end.json",
            "the_void.json", "warm_ocean.json", "warped_forest.json", "wooded_badlands_plateau.json", "wooded_hills.json",
            "wooded_mountains.json"));
    public static void loadBiomes() {
        for (String biome: biomeList) {
            try {
                Server.getIO().saveResource("biomes/"+biome,false);
                File file = new File("biomes/"+biome);
                JsonObject json = JsonParser.parseReader(new FileReader(file)).getAsJsonObject();
                Biome biome1 = Biome.builder()
                        .category(Biome.Category.valueOf(json.get("category").getAsString().toUpperCase(Locale.ROOT)))
                        .depth(json.get("depth").getAsFloat())
                        .downfall(json.get("downfall").getAsFloat())
                        .scale(json.get("scale").getAsFloat())
                        .precipitation(Biome.Precipitation.valueOf(json.get("precipitation").getAsString().toUpperCase(Locale.ROOT)))
                        .temperature(json.get("temperature").getAsFloat())
                        .effects(loadEffects(json.getAsJsonObject("effects")))
                        .name(NamespaceID.from(biome.split("\\.")[0]))
                        .build();
                biomes.put((int)(((Float)biome1.temperature()).doubleValue()*10000),
                        MathUtil.precipitationValue(biome1.precipitation()),
                        biome1.id(),json.get("surface_builder").getAsString());
                manager.addBiome(biome1);
            }catch (FileNotFoundException e) {
                System.out.println("无法找到"+biome);
            }
        }
    }
    public static BiomeEffects loadEffects(JsonObject json) {
        return BiomeEffects.builder()
                .moodSound(new BiomeEffects.MoodSound(
                        NamespaceID.from(json.get("mood_sound").getAsJsonObject().get("sound").getAsString()),
                        json.get("mood_sound").getAsJsonObject().get("tick_delay").getAsInt(),
                        json.get("mood_sound").getAsJsonObject().get("block_search_extent").getAsInt(),
                        json.get("mood_sound").getAsJsonObject().get("offset").getAsDouble()))
                .fogColor(json.has("fog_color")?json.get("fog_color").getAsInt():0)
                .foliageColor(json.has("foliage_color")?json.get("foliage_color").getAsInt():0)
                .skyColor(json.has("sky_color")?json.get("sky_color").getAsInt():0)
                .waterColor(json.has("water_color")?json.get("water_color").getAsInt():0)
                .waterFogColor(json.has("water_fog_color")?json.get("water_fog_color").getAsInt():0)
                .grassColor(json.has("grass_color")?json.get("grass_color").getAsInt():0)
                .build();
    }
    public static Biome getBiome(int heat, int humidity) {
        return manager.getById(biomes.find(heat,humidity));
    }
}
