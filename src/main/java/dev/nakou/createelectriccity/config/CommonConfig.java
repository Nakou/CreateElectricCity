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
    public static final String CATEGORY_SMALL_LIGHT_BULB = "small light bulbs";
    public static final String CATEGORY_BIG_LIGHT_BULB = "big light bulbs";
    public static final String CATEGORY_OLD_LIGHT_BULB = "old light bulbs";
    public static final String CATEGORY_LANTERN = "lanterns";
    public static final String CATEGORY_WIRES = "wires";

    // ====== Wires ======
    public static ModConfigSpec.IntValue LIGHTS_MAX_INPUT;
    public static ModConfigSpec.IntValue LIGHTS_MAX_OUTPUT;
    public static ModConfigSpec.IntValue LIGHTS_MAX_LENGTH;

    //General
    public static ModConfigSpec.BooleanValue CONNECTOR_IGNORE_FACE_CHECK;

    // ====== Message Config ======
    public static ModConfigSpec.BooleanValue MESSAGES_ENABLED;

    // ====== Light Config (Grouped Struct) ======
    public static class LightConfig {
        public ModConfigSpec.IntValue CONSUMPTION;
        public ModConfigSpec.IntValue LUMEN;
        public ModConfigSpec.BooleanValue AUDIO_ENABLED;
        public ModConfigSpec.BooleanValue DYEABLE;
    }

    public static LightConfig SMALL_LIGHT_BULB;
    public static LightConfig LANTERN;
    public static LightConfig BIG_LIGHT_BULB;
    public static LightConfig OLD_LIGHT_BULB;
    public static LightConfig HANGING_LIGHT;

    static {
        // Go check Create : Better Motors for references
        builder.comment("General configuration for Create: Electric City");

        builder.comment("Wires").push(CATEGORY_WIRES);
        LIGHTS_MAX_INPUT = builder.comment("Heavy Connector max input in FE/t").defineInRange("max_input", 90000, 0, Integer.MAX_VALUE);
        LIGHTS_MAX_OUTPUT = builder.comment("Heavy Connector max output in FE/t").defineInRange("max_output", 90000, 0, Integer.MAX_VALUE);
        LIGHTS_MAX_LENGTH = builder.comment("Heavy Connector max length in blocks").defineInRange("max_length", 48, 0, 256);
        builder.pop();

        CONNECTOR_IGNORE_FACE_CHECK = builder.comment("Ignore checking if block face can support connector.").define("connector_ignore_face_check", false);
        SMALL_LIGHT_BULB = light(builder, CATEGORY_SMALL_LIGHT_BULB, 1, 6, true);
        BIG_LIGHT_BULB = light(builder, CATEGORY_BIG_LIGHT_BULB, 2, 14, true);
        LANTERN = light(builder, CATEGORY_LANTERN, 2, 12, true);
        OLD_LIGHT_BULB = light(builder, CATEGORY_OLD_LIGHT_BULB, 2, 8, false);
        HANGING_LIGHT = light(builder, CATEGORY_OLD_LIGHT_BULB, 2, 13, true);
        COMMON_CONFIG = builder.build();
    }

    /** Helper for light config creation */
    private static LightConfig light(ModConfigSpec.Builder builder, String category,
                                     int consumption, int lumens, boolean dyeable) {
        builder.comment(category).push(category);
        LightConfig config = new LightConfig();
        config.CONSUMPTION = builder.comment("Consumption of FE/t").defineInRange("consumption", consumption, 0, Integer.MAX_VALUE);
        config.LUMEN = builder.comment("Production of light").defineInRange("lumens", lumens, 0, 15);
        config.DYEABLE = builder.comment("Can be Dyed").define("dyeable", dyeable);
        builder.pop();
        return config;
    }

    // ===== Config Loading =====
    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        loadConfig(CommonConfig.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("createelectriccity-common.toml"));
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
