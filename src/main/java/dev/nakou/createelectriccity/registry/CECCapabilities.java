package dev.nakou.createelectriccity.registry;

import dev.nakou.createelectriccity.content.smalllightbulb.SmallLightBulbBlockEntity;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class CECCapabilities {
    public static void register(RegisterCapabilitiesEvent event) {
        SmallLightBulbBlockEntity.registerCapabilities(event);
    }
}
