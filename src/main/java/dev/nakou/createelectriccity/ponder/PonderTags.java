package dev.nakou.createelectriccity.ponder;

import com.mrh0.createaddition.CreateAddition;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.nakou.createelectriccity.CreateElectricCity;
import dev.nakou.createelectriccity.registry.CECBlocks;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;



public class PonderTags {
    public static final ResourceLocation ELECTRIC = CreateAddition.asResource("electric");

    private static ResourceLocation loc(String id) {
        return CreateElectricCity.asResource(id);
    }

    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PonderTagRegistrationHelper<RegistryEntry<?,?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
        PonderTagRegistrationHelper<ItemLike> itemHelper = helper.withKeyFunction(
                RegisteredObjectsHelper::getKeyOrThrow);


        HELPER.registerTag(ELECTRIC)
                .addToIndex()
                .item(CECBlocks.SMALL_LIGHT_BULB.get(), true, false)
                .title("Electric Blocks")
                .description("Components which use electricity")
                .register();



        //HELPER.addToTag(AllCreatePonderTags.KINETIC_APPLIANCES)
        // .add(CECBlocks.MECANICAL_LAMP); <= a girl can dream hahaha


        //HELPER.addToTag(ELECTRIC)
        // .add(CECBlocks.SMALL_LIGHT_BULB); <= nooot really needed?
    }

}