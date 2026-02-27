package dev.nakou.createelectriccity.datagen.recipes.tagprovider;

import com.mrh0.createaddition.datagen.TagProvider.CATagRegister;
import com.mrh0.createaddition.index.CABlocks;
import com.mrh0.createaddition.index.CAFluids;
import com.mrh0.createaddition.index.CAItems;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import dev.nakou.createelectriccity.CreateElectricCity;
import dev.nakou.createelectriccity.registry.CECBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CECItemTagProvider extends ItemTagsProvider {

    public CECItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, CreateElectricCity.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(CECTagRegister.Items.SMALL_LIGHT_BULB).add(CECBlocks.SMALL_LIGHT_BULB.get().asItem());
        this.tag(CECTagRegister.Items.BIG_LIGHT_BULB).add(CECBlocks.BIG_LIGHT_BULB.get().asItem());
        this.tag(CECTagRegister.Items.OLD_LIGHT_BULB).add(CECBlocks.OLD_LIGHT_BULB.get().asItem());
        this.tag(CECTagRegister.Items.HANGING_LIGHT).add(CECBlocks.HANGING_LIGHT.get().asItem());
        this.tag(CECTagRegister.Items.LANTERN).add(CECBlocks.LANTERN.get().asItem());
    }
}