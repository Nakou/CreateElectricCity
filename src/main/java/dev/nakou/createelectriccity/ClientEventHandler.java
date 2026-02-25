package dev.nakou.createelectriccity;

import dev.nakou.createelectriccity.sound.CECSoundScapes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = CreateElectricCity.MODID, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void tickSoundscapes(ClientTickEvent.Post event) {
        CECSoundScapes.tick();
    }
}
