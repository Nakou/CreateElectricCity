package dev.nakou.createelectriccity.registry;

import dev.nakou.createelectriccity.CECClient;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class CECClientIniter {
    public static void onInitializeClient(final FMLClientSetupEvent event) {
        CECClient.onInitializeClient(event);
    }
}
