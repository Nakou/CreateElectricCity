package dev.nakou.createelectriccity;


import dev.nakou.createelectriccity.ponder.PonderPlugin;
import net.createmod.catnip.config.ui.BaseConfigScreen;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;


import static dev.nakou.createelectriccity.CreateElectricCity.MODID;

public class CECClient {
    public static void onInitializeClient(final FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new PonderPlugin());

//        ModContainer modContainer = ModList.get()
//                .getModContainerById(Create_better_motors.MOD_ID)
//                .orElseThrow(() -> new IllegalStateException("What the..."));

//        modContainer.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
//                () -> new ConfigScreenHandler.ConfigScreenFactory(
//                        (mc, previousScreen) -> new BaseConfigScreen(previousScreen, Create_better_motors.MOD_ID)));


        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (mc, previous) -> new BaseConfigScreen(previous, MODID)
        );
    }

}
