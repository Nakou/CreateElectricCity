package dev.nakou.createelectriccity.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.ModConfigSpec;

public class CommonConfig {
    private static final ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
    public static ModConfigSpec COMMON_CONFIG;

    // ====== General Categories ======
    public static final String CATAGORY_LIGHTS = "lights";
    public static final String CATAGORY_WIRES = "wires";

    // ====== Wires ======
    public static ModConfigSpec.IntValue LIGHTS_MAX_INPUT;
    public static ModConfigSpec.IntValue LIGHTS_MAX_OUTPUT;
    public static ModConfigSpec.IntValue LIGHTS_MAX_LENGTH;

    // ====== Message Config ======
    public static ModConfigSpec.BooleanValue MESSAGES_ENABLED;

    // ====== Motor Configs (Grouped Struct) ======
    public static class LightConfig {
        public ModConfigSpec.IntValue CONSUMPTION;
        public ModConfigSpec.IntValue LUMENS;
        public ModConfigSpec.BooleanValue AUDIO_ENABLED;
    }
    public static LightConfig SMALL_LIGHT_BUBBLE;

    static {
        // Go check Create : Better Motors for references
        builder.comment("General configuration for Create: Electric City");

        builder.comment("Wires").push(CATAGORY_WIRES);
        LIGHTS_MAX_INPUT = builder.comment("Heavy Connector max input in FE/t").defineInRange("max_input", 90000, 0, Integer.MAX_VALUE);
        LIGHTS_MAX_OUTPUT = builder.comment("Heavy Connector max output in FE/t").defineInRange("max_output", 90000, 0, Integer.MAX_VALUE);
        LIGHTS_MAX_LENGTH = builder.comment("Heavy Connector max length in blocks").defineInRange("max_length", 48, 0, 256);
        builder.pop();

        SMALL_LIGHT_BUBBLE = light(builder, CATAGORY_LIGHTS, 1, 8);
        COMMON_CONFIG = builder.build();
    }

    /** Helper for motor config creation */
    private static LightConfig light(ModConfigSpec.Builder builder, String category,
                                     int consumption, int lumens) {
        builder.comment(category).push(category);
        LightConfig config = new LightConfig();
        config.CONSUMPTION = builder.comment("Consumption of FE/t").defineInRange("consumption", consumption, 0, Integer.MAX_VALUE);
        config.LUMENS = builder.comment("Production of light").defineInRange("lumens", lumens, 0, Integer.MAX_VALUE);
        config.AUDIO_ENABLED = builder.comment("Enable light audio").define("audio_enabled", true);
        builder.pop();
        return config;
    }

    // ===== Config Loading =====
    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        loadConfig(CommonConfig.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("create_better_motors-common.toml"));
    }

    public static void loadConfig(ModConfigSpec spec, java.nio.file.Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync().autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        spec.correct(configData);
    }
}
