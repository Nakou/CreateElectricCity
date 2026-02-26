package dev.nakou.createelectriccity.registry;

import dev.nakou.createelectriccity.content.GenericLightBlockEntity;
import dev.nakou.createelectriccity.content.lantern.LanternBlockEntity;
import dev.nakou.createelectriccity.content.lantern.LanternRenderer;
import dev.nakou.createelectriccity.content.variants.BigLightBulbRenderer;
import dev.nakou.createelectriccity.content.smalllightbulb.SmallLightBulbRenderer;
import dev.nakou.createelectriccity.content.smalllightbulb.SmallLightBulbBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.nakou.createelectriccity.content.variants.OldLightBulbRenderer;

import static dev.nakou.createelectriccity.CreateElectricCity.REGISTRATE;

public class CECBlockEntityTypes {
    public static final BlockEntityEntry<SmallLightBulbBlockEntity> SMALL_LIGHT_BULB = REGISTRATE
            .blockEntity("small_light_bulb", SmallLightBulbBlockEntity::new)
            .validBlocks(CECBlocks.SMALL_LIGHT_BULB)
            .renderer(() -> SmallLightBulbRenderer::new)
            .register();
    public static final BlockEntityEntry<LanternBlockEntity> LANTERN = REGISTRATE
            .blockEntity("lantern", LanternBlockEntity::new)
            .validBlocks(CECBlocks.LANTERN)
            .renderer(() -> LanternRenderer::new)
            .register();

    // Attempt at generic (so new model inclusion is even faster.
    public static final BlockEntityEntry<GenericLightBlockEntity> BIG_LIGHT_BULB = REGISTRATE
            .blockEntity("big_light_bulb",GenericLightBlockEntity::new)
            .validBlocks(CECBlocks.BIG_LIGHT_BULB)
            .renderer(() -> BigLightBulbRenderer::new)
            .register();

    public static final BlockEntityEntry<GenericLightBlockEntity> OLD_LIGHT_BULB = REGISTRATE
            .blockEntity("old_light_bulb",GenericLightBlockEntity::new)
            .validBlocks(CECBlocks.OLD_LIGHT_BULB)
            .renderer(() -> OldLightBulbRenderer::new)
            .register();

    public static void load() {  }
}
