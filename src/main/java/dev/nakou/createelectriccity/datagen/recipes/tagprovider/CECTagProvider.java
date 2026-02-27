package dev.nakou.createelectriccity.datagen.recipes.tagprovider;

import dev.nakou.createelectriccity.CreateElectricCity;
import dev.nakou.createelectriccity.registry.CECBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CECTagProvider extends BlockTagsProvider {

    public CECTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CreateElectricCity.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(CECTagRegister.Blocks.SMALL_LIGHT_BULB).add(CECBlocks.SMALL_LIGHT_BULB.get());
        tag(CECTagRegister.Blocks.BIG_LIGHT_BULB).add(CECBlocks.BIG_LIGHT_BULB.get());
        tag(CECTagRegister.Blocks.OLD_LIGHT_BULB).add(CECBlocks.OLD_LIGHT_BULB.get());
        tag(CECTagRegister.Blocks.LANTERN).add(CECBlocks.LANTERN.get());
        tag(CECTagRegister.Blocks.HANGING_LIGHT).add(CECBlocks.HANGING_LIGHT.get());
    }
}
