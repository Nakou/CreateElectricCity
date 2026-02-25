package dev.nakou.createelectriccity.registry;

import dev.nakou.createelectriccity.content.smalllightbulb.SmallLightBulbRenderer;
import dev.nakou.createelectriccity.content.smalllightbulb.SmallLightBulbBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static dev.nakou.createelectriccity.CreateElectricCity.REGISTRATE;

public class CECBlockEntityTypes {
    public static final BlockEntityEntry<SmallLightBulbBlockEntity> SMALL_LIGHT_BULB = REGISTRATE
            .blockEntity("small_light_bulb", SmallLightBulbBlockEntity::new)
            .validBlocks(CECBlocks.SMALL_LIGHT_BULB)
            .renderer(() -> SmallLightBulbRenderer::new)
            .register();
    public static void load() {  }
}
