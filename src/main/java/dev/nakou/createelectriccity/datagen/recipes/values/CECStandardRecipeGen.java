package dev.nakou.createelectriccity.datagen.recipes.values;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrh0.createaddition.datagen.TagProvider.CATagRegister;
import com.mrh0.createaddition.index.CAItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.decoration.palettes.AllPaletteBlocks;
import com.simibubi.create.foundation.data.recipe.CommonMetal;
import com.simibubi.create.foundation.data.recipe.Mods;
import com.simibubi.create.foundation.mixin.accessor.MappedRegistryAccessor;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import dev.nakou.createelectriccity.CreateElectricCity;
import dev.nakou.createelectriccity.datagen.recipes.CECRecipeProvider;
import dev.nakou.createelectriccity.registry.CECBlocks;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static dev.nakou.createelectriccity.datagen.recipes.CECRecipeProvider.I.*;

public class CECStandardRecipeGen extends CECRecipeProvider {
    private Marker MATERIALS = enterFolder("materials");
    public static final Map<String, ItemLike> DYES_FROM_COLOR = new HashMap<>();


    static {
        DYES_FROM_COLOR.put("white", Items.WHITE_DYE);
        DYES_FROM_COLOR.put("blue", Items.BLUE_DYE);
        DYES_FROM_COLOR.put("light_blue", Items.LIGHT_BLUE_DYE);
        DYES_FROM_COLOR.put("red", Items.RED_DYE);
        DYES_FROM_COLOR.put("green", Items.GREEN_DYE);
        DYES_FROM_COLOR.put("lime", Items.LIME_DYE);
        DYES_FROM_COLOR.put("pink", Items.PINK_DYE);
        DYES_FROM_COLOR.put("magenta", Items.MAGENTA_DYE);
        DYES_FROM_COLOR.put("yellow", Items.YELLOW_DYE);
        DYES_FROM_COLOR.put("gray", Items.GRAY_DYE);
        DYES_FROM_COLOR.put("light_gray", Items.LIGHT_GRAY_DYE);
        DYES_FROM_COLOR.put("brown", Items.BROWN_DYE);
        DYES_FROM_COLOR.put("cyan", Items.CYAN_DYE);
        DYES_FROM_COLOR.put("purple", Items.PURPLE_DYE);
        DYES_FROM_COLOR.put("orange", Items.ORANGE_DYE);
        DYES_FROM_COLOR.put("black", Items.BLACK_DYE);


    }

    GeneratedRecipe
            SMALL_LIGHT_BULB = create(CECBlocks.SMALL_LIGHT_BULB)
                    .unlockedBy(CAItems.COPPER_WIRE::get)
                    .viaShaped(b -> b
                            .define('I', ironNugget())
                            .define('G', glass())
                            .define('C', copperWire())
                            .pattern(" G ")
                            .pattern(" C ")
                            .pattern(" I ")),
            BIG_LIGHT_BULB = create(CECBlocks.BIG_LIGHT_BULB)
                    .unlockedBy(CAItems.COPPER_WIRE::get)
                    .viaShaped(b -> b
                            .define('I', ironNugget())
                            .define('G', glass())
                            .define('I', copperWire())
                            .pattern("GGG")
                            .pattern(" CC")
                            .pattern(" I ")),
            OLD_LIGHT_BULB = create(CECBlocks.OLD_LIGHT_BULB)
                    .unlockedBy(CAItems.COPPER_WIRE::get)
                    .viaShaped(b -> b
                            .define('I', ironNugget())
                            .define('G', glass())
                            .define('C', copperWire())
                            .pattern("GG ")
                            .pattern("CC ")
                            .pattern(" I ")),
            LANTERN = create(CECBlocks.LANTERN)
                    .unlockedBy(CECBlocks.SMALL_LIGHT_BULB::asItem)
                    .viaShaped(b -> b
                            .define('I', iron())
                            .define('G', glass())
                            .define('L', smallLightBulb())
                            .pattern(" I ")
                            .pattern("GLG")
                            .pattern(" I ")),
            HANGING_LIGHT = create(CECBlocks.HANGING_LIGHT)
                    .unlockedBy(CECBlocks.SMALL_LIGHT_BULB::asItem)
                    .viaShaped(b -> b
                            .define('I', iron())
                            .define('L', smallLightBulb())
                            .pattern(" I ")
                            .pattern(" L ")
                            .pattern("   "));
    /// ////////////////////

    String currentFolder = "";

    CECRecipeProvider.Marker enterFolder(String folder) {
        currentFolder = folder;
        return new CECRecipeProvider.Marker();
    }

    GeneratedRecipeBuilder create(Supplier<ItemLike> result) {
        return new GeneratedRecipeBuilder(currentFolder, result);
    }

    GeneratedRecipeBuilder create(ResourceLocation result) {
        return new GeneratedRecipeBuilder(currentFolder, result);
    }



    GeneratedRecipeBuilder create(ItemProviderEntry<? extends ItemLike, ? extends ItemLike> result) {
        return create(result::get);
    }

        class GeneratedRecipeBuilder {

        private String path;
        private String suffix;
        private Supplier<? extends ItemLike> result;
        private ResourceLocation compatDatagenOutput;
        List<ICondition> recipeConditions;

        private Supplier<ItemPredicate> unlockedBy;
        private int amount;

        private GeneratedRecipeBuilder(String path) {
            this.path = path;
            this.recipeConditions = new ArrayList<>();
            this.suffix = "";
            this.amount = 1;
        }

        public GeneratedRecipeBuilder(String path, Supplier<? extends ItemLike> result) {
            this(path);
            this.result = result;
        }

        public GeneratedRecipeBuilder(String path, ResourceLocation result) {
            this(path);
            this.compatDatagenOutput = result;
        }

        GeneratedRecipeBuilder returns(int amount) {
            this.amount = amount;
            return this;
        }

        GeneratedRecipeBuilder unlockedBy(Supplier<? extends ItemLike> item) {
            this.unlockedBy = () -> ItemPredicate.Builder.item()
                    .of(item.get())
                    .build();
            return this;
        }

        GeneratedRecipeBuilder unlockedByTag(Supplier<TagKey<Item>> tag) {
            this.unlockedBy = () -> ItemPredicate.Builder.item()
                    .of(tag.get())
                    .build();
            return this;
        }

        GeneratedRecipeBuilder whenModLoaded(String modid) {
            return withCondition(new ModLoadedCondition(modid));
        }

        GeneratedRecipeBuilder whenModMissing(String modid) {
            return withCondition(new NotCondition(new ModLoadedCondition(modid)));
        }

        GeneratedRecipeBuilder withCondition(ICondition condition) {
            recipeConditions.add(condition);
            return this;
        }

        GeneratedRecipeBuilder withSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        CECRecipeProvider.GeneratedRecipe viaShaped(UnaryOperator<ShapedRecipeBuilder> builder) {
            return register(consumer -> {

                ShapedRecipeBuilder b =
                        builder.apply(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result.get(), amount));
                if (unlockedBy != null)
                    b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));
                b.save(consumer, createLocation("crafting"));
            });
        }

        CECRecipeProvider.GeneratedRecipe viaShapeless(UnaryOperator<ShapelessRecipeBuilder> builder) {
            return register(recipeOutput -> {
                ShapelessRecipeBuilder b =
                        builder.apply(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result.get(), amount));
                if (unlockedBy != null)
                    b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));

                RecipeOutput conditionalOutput = recipeOutput.withConditions(recipeConditions.toArray(new ICondition[0]));

                b.save(recipeOutput, createLocation("crafting"));
            });
        }

        private ResourceLocation createSimpleLocation(String recipeType) {
            return CreateElectricCity.asResource(recipeType + "/" + getRegistryName().getPath() + suffix);
        }

        private ResourceLocation createLocation(String recipeType) {
            return CreateElectricCity.asResource(recipeType + "/" + path + "/" + getRegistryName().getPath() + suffix);
        }

        private ResourceLocation getRegistryName() {
            return compatDatagenOutput == null ? RegisteredObjectsHelper.getKeyOrThrow(result.get()
                    .asItem()) : compatDatagenOutput;
        }

        class GeneratedCookingRecipeBuilder {

            private Supplier<Ingredient> ingredient;
            private float exp;
            private int cookingTime;

            GeneratedCookingRecipeBuilder(Supplier<Ingredient> ingredient) {
                this.ingredient = ingredient;
                cookingTime = 200;
                exp = 0;
            }

            GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder forDuration(int duration) {
                cookingTime = duration;
                return this;
            }

            GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder rewardXP(float xp) {
                exp = xp;
                return this;
            }

            CECRecipeProvider.GeneratedRecipe inFurnace() {
                return inFurnace(b -> b);
            }

            CECRecipeProvider.GeneratedRecipe inFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                return create(RecipeSerializer.SMELTING_RECIPE, builder, SmeltingRecipe::new, 1);
            }

            private <T extends AbstractCookingRecipe> CECRecipeProvider.GeneratedRecipe create(RecipeSerializer<T> serializer,
                                                                                               UnaryOperator<SimpleCookingRecipeBuilder> builder, AbstractCookingRecipe.Factory<T> factory, float cookingTimeModifier) {
                return register(recipeOutput -> {
                    boolean isOtherMod = compatDatagenOutput != null;

                    SimpleCookingRecipeBuilder b = builder.apply(SimpleCookingRecipeBuilder.generic(ingredient.get(),
                            RecipeCategory.MISC, isOtherMod ? Items.DIRT : result.get(), exp,
                            (int) (cookingTime * cookingTimeModifier), serializer, factory));
                    if (unlockedBy != null)
                        b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));

                    RecipeOutput conditionalOutput = recipeOutput.withConditions(recipeConditions.toArray(new ICondition[0]));

                    b.save(
                            isOtherMod ? new ModdedCookingRecipeOutput(conditionalOutput, compatDatagenOutput) : conditionalOutput,
                            createSimpleLocation(RegisteredObjectsHelper.getKeyOrThrow(serializer).getPath())
                    );
                });
            }
        }
    }



    public CECStandardRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @ParametersAreNonnullByDefault
    @MethodsReturnNonnullByDefault
    private static class ModdedCookingRecipeOutputShim implements Recipe<RecipeInput> {

        private static final Map<RecipeType<?>, ModdedCookingRecipeOutputShim.Serializer> serializers = new ConcurrentHashMap<>();

        private final Recipe<?> wrapped;
        private final ResourceLocation overrideID;

        private ModdedCookingRecipeOutputShim(Recipe<?> wrapped, ResourceLocation overrideID) {
            this.wrapped = wrapped;
            this.overrideID = overrideID;
        }

        @Override
        public boolean matches(RecipeInput recipeInput, Level level) {
            throw new AssertionError("Only for datagen output");
        }

        @Override
        public ItemStack assemble(RecipeInput input, HolderLookup.Provider registries) {
            throw new AssertionError("Only for datagen output");
        }

        @Override
        public boolean canCraftInDimensions(int pWidth, int pHeight) {
            throw new AssertionError("Only for datagen output");
        }

        @Override
        public ItemStack getResultItem(HolderLookup.Provider registries) {
            throw new AssertionError("Only for datagen output");
        }

        @Override
        public RecipeSerializer<?> getSerializer() {
            return serializers.computeIfAbsent(
                    getType(),
                    t -> ModdedCookingRecipeOutputShim.Serializer.create(wrapped)
            );
        }

        @Override
        public RecipeType<?> getType() {
            return wrapped.getType();
        }

        private record Serializer(MapCodec<Recipe<?>> wrappedCodec) implements RecipeSerializer<ModdedCookingRecipeOutputShim> {
            private static ModdedCookingRecipeOutputShim.Serializer create(Recipe<?> wrapped) {
                RecipeSerializer<?> wrappedSerializer = wrapped.getSerializer();
                @SuppressWarnings("unchecked")
                ModdedCookingRecipeOutputShim.Serializer serializer = new ModdedCookingRecipeOutputShim.Serializer((MapCodec<Recipe<?>>) wrappedSerializer.codec());

                // Need to do some registry injection to get the Recipe/Registry#byNameCodec to encode the right type for this
                // getResourceKey and getId
                // byValue and toId
                // Holder.Reference: key
                if (BuiltInRegistries.RECIPE_SERIALIZER instanceof MappedRegistryAccessor<?> mra) {
                    @SuppressWarnings("unchecked")
                    MappedRegistryAccessor<RecipeSerializer<?>> mra$ = (MappedRegistryAccessor<RecipeSerializer<?>>) mra;

                    int wrappedId = mra$.getToId().getOrDefault(wrappedSerializer, -1);
                    ResourceKey<RecipeSerializer<?>> wrappedKey = mra$.getByValue().get(wrappedSerializer).key();

                    mra$.getToId().put(serializer, wrappedId);
                    //noinspection DataFlowIssue - it is ok to pass null as the owner, because this is only being used for serialization
                    mra$.getByValue().put(serializer, Holder.Reference.createStandAlone(null, wrappedKey));
                } else {
                    throw new AssertionError("ModdedCookingRecipeOutputShim will not be able to" +
                            " serialize without injecting into a registry. Expected" +
                            " BuiltInRegistries.RECIPE_SERIALIZER to be of class MappedRegistry, is of class " +
                            BuiltInRegistries.RECIPE_SERIALIZER.getClass()
                    );
                }
                return serializer;
            }

            @Override
            public MapCodec<ModdedCookingRecipeOutputShim> codec() {
                return RecordCodecBuilder.mapCodec(instance -> instance.group(
                        wrappedCodec.forGetter(i -> i.wrapped),
                        ModdedCookingRecipeOutputShim.FakeItemStack.CODEC.fieldOf("result").forGetter(i -> new ModdedCookingRecipeOutputShim.FakeItemStack(i.overrideID))
                ).apply(instance, (wrappedRecipe, fakeItemStack) -> {
                    throw new AssertionError("Only for datagen output");
                }));
            }

            @Override
            public StreamCodec<RegistryFriendlyByteBuf, ModdedCookingRecipeOutputShim> streamCodec() {
                throw new AssertionError("Only for datagen output");
            }
        }

        private record FakeItemStack(ResourceLocation id) {
            public static Codec<ModdedCookingRecipeOutputShim.FakeItemStack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(ModdedCookingRecipeOutputShim.FakeItemStack::id)
            ).apply(instance, ModdedCookingRecipeOutputShim.FakeItemStack::new));
        }
    }

    @ParametersAreNonnullByDefault
    @MethodsReturnNonnullByDefault
    private record ModdedCookingRecipeOutput(RecipeOutput wrapped, ResourceLocation outputOverride) implements RecipeOutput {

        @Override
        public Advancement.Builder advancement() {
            return wrapped.advancement();
        }

        @Override
        public void accept(ResourceLocation id, Recipe<?> recipe, @Nullable AdvancementHolder advancement, ICondition... conditions) {
            wrapped.accept(id, new ModdedCookingRecipeOutputShim(recipe, outputOverride), advancement, conditions);
        }
    }
}