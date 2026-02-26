package dev.nakou.createelectriccity.registry;

import dev.nakou.createelectriccity.content.GenericLightBlockEntity;
import dev.nakou.createelectriccity.content.variants.LanternRenderer;
import dev.nakou.createelectriccity.content.variants.BigLightBulbRenderer;
import dev.nakou.createelectriccity.content.variants.SmallLightBulbRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.nakou.createelectriccity.content.variants.OldLightBulbRenderer;
import dev.nakou.createelectriccity.content.variants.hanging_light.HangingLightRenderer;

import static dev.nakou.createelectriccity.CreateElectricCity.REGISTRATE;

public class CECBlockEntityTypes {

    public static final BlockEntityEntry<GenericLightBlockEntity> SMALL_LIGHT_BULB = REGISTRATE
            .blockEntity("small_light_bulb",GenericLightBlockEntity::new)
            .validBlocks(CECBlocks.SMALL_LIGHT_BULB)
            .renderer(() -> SmallLightBulbRenderer::new)
            .register();

    public static final BlockEntityEntry<GenericLightBlockEntity> LANTERN = REGISTRATE
            .blockEntity("lantern",GenericLightBlockEntity::new)
            .validBlocks(CECBlocks.LANTERN)
            .renderer(() -> LanternRenderer::new)
            .register();

    public static final BlockEntityEntry<GenericLightBlockEntity> BIG_LIGHT_BULB = REGISTRATE
            .blockEntity("big_light_bulb",GenericLightBlockEntity::new)
            .validBlocks(CECBlocks.BIG_LIGHT_BULB)
            .renderer(() -> BigLightBulbRenderer::new)
            .register();

    public static final BlockEntityEntry<GenericLightBlockEntity> HANGING_LIGHT = REGISTRATE
            .blockEntity("hanging_light",GenericLightBlockEntity::new)
            .validBlocks(CECBlocks.HANGING_LIGHT)
            .renderer(() -> HangingLightRenderer::new)
            .register();

    public static final BlockEntityEntry<GenericLightBlockEntity> OLD_LIGHT_BULB = REGISTRATE
            .blockEntity("old_light_bulb",GenericLightBlockEntity::new)
            .validBlocks(CECBlocks.OLD_LIGHT_BULB)
            .renderer(() -> OldLightBulbRenderer::new)
            .register();

    public static void load() {  }
}
