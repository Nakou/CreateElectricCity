package dev.nakou.createelectriccity.registry;

import dev.nakou.createelectriccity.content.smalllightbulb.SmallLightBulbEntity;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class CECCapabilities {
    public static void register(RegisterCapabilitiesEvent event) {
        SmallLightBulbEntity.registerCapabilities(event);
    }
}
