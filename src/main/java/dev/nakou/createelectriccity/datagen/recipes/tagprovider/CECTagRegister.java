package dev.nakou.createelectriccity.datagen.recipes.tagprovider;

import dev.nakou.createelectriccity.CreateElectricCity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class CECTagRegister {

    public static class Blocks {
        public static final TagKey<Block> SMALL_LIGHT_BULB = tag("small_light_bulb");
        public static final TagKey<Block> BIG_LIGHT_BULB = tag("big_light_bulb");
        public static final TagKey<Block> OLD_LIGHT_BULB = tag("old_light_bulb");
        public static final TagKey<Block> HANGING_LIGHT = tag("hanging_light");
        public static final TagKey<Block> LANTERN = tag("lantern");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(CreateElectricCity.MODID, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> SMALL_LIGHT_BULB = tag("small_light_bulb");
        public static final TagKey<Item> BIG_LIGHT_BULB = tag("big_light_bulb");
        public static final TagKey<Item> OLD_LIGHT_BULB = tag("old_light_bulb");
        public static final TagKey<Item> HANGING_LIGHT = tag("hanging_light");
        public static final TagKey<Item> LANTERN = tag("lantern");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(CreateElectricCity.MODID, name));
        }
    }
}
