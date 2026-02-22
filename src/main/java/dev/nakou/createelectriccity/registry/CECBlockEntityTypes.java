package dev.nakou.createelectriccity.registry;

import com.mrh0.createaddition.blocks.connector.base.ConnectorRenderer;
import dev.nakou.createelectriccity.content.common.LightRenderer;
import dev.nakou.createelectriccity.content.smalllightbulb.SmallLightBulbEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static dev.nakou.createelectriccity.CreateElectricCity.REGISTRATE;

public class CECBlockEntityTypes {
    public static final BlockEntityEntry<SmallLightBulbEntity> SMALL_LIGHT_BULB = REGISTRATE
            .blockEntity("heavy_connector", SmallLightBulbEntity::new)
            .validBlocks(CECBlocks.SMALL_LIGHT_BULB)
            .renderer(() -> LightRenderer::new)
            .register();
    public static void load() {  }
}
