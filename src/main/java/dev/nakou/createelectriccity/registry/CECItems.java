package dev.nakou.createelectriccity.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static dev.nakou.createelectriccity.CreateElectricCity.REGISTRATE;

public class CECItems {
    public static final ItemEntry<Item> SMALL_LIGHT_BULB = REGISTRATE.item("small_light_bulb", Item::new).register();

    public static void load() {}
    @SafeVarargs
    private static ItemEntry<Item> taggedIngredient(String name, TagKey<Item>... tags) {
        return REGISTRATE.item(name, Item::new)
                .tag(tags)
                .register();
    }
}
