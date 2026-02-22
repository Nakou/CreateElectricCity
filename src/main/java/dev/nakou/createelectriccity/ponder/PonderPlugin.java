package dev.nakou.createelectriccity.ponder;

import dev.nakou.createelectriccity.CreateElectricCity;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class PonderPlugin implements net.createmod.ponder.api.registration.PonderPlugin {
    @Override
    public String getModId() {
        return CreateElectricCity.MODID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderIndex.register(helper);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PonderTags.register(helper);
    }
}
