package dev.nakou.createelectriccity.ponder;

import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.nakou.createelectriccity.registry.CECBlocks;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

import static dev.nakou.createelectriccity.ponder.PonderTags.ELECTRIC;

public class PonderIndex {

    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?,?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        //Lightbulbs
        //  HELPER.forComponents(CECBlocks.SMALL_LIGHT_BULB)
        // .addStoryBoard("wires", PonderScenes::Electricity, AllCreatePonderTags.KINETIC_SOURCES, ELECTRIC);
    }
}
