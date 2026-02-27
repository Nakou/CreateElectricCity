package dev.nakou.createelectriccity.datagen.recipes;

import com.mrh0.createaddition.datagen.TagProvider.CATagRegister;
import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.data.recipe.CommonMetal;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.nakou.createelectriccity.CreateElectricCity;
import dev.nakou.createelectriccity.datagen.recipes.tagprovider.CECTagRegister;
import dev.nakou.createelectriccity.registry.CECBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CECRecipeProvider extends RecipeProvider {

    static final List<ProcessingRecipeGen<?, ?, ?>> GENERATORS = new ArrayList<>();

    protected final List<CECRecipeProvider.GeneratedRecipe> all = new ArrayList<>();

    public CECRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        all.forEach(c -> c.register(pRecipeOutput));
        CreateElectricCity.LOGGER.info("{} registered {} recipe{}", getName(), all.size(), all.size() == 1 ? "" : "s");
    }

    protected CECRecipeProvider.GeneratedRecipe register(CECRecipeProvider.GeneratedRecipe recipe) {
        all.add(recipe);
        return recipe;
    }

    @FunctionalInterface
    public interface GeneratedRecipe {
        void register(RecipeOutput output);
    }

    public static class Marker {
    }


    public static class I {

        public static ItemLike smallLightBulb() {
            return CECBlocks.SMALL_LIGHT_BULB.get();
        }

        public static TagKey<Item> copperWire() {
            return CATagRegister.Items.COPPER_WIRES;
        }

        public static TagKey<Item> glass() {
            return Tags.Items.GLASS_PANES;
        }

        public static TagKey<Item> ironNugget() {
            return CommonMetal.IRON.nuggets;
        }

        public static TagKey<Item> iron() {
            return Tags.Items.INGOTS_IRON;
        }

        public static ItemLike copperIngot() {
            return Items.COPPER_INGOT;
        }

        public static TagKey<Item> copperNugget() {
            return CommonMetal.COPPER.nuggets;
        }


    }
}
