package dev.nakou.createelectriccity.registry;

import com.mrh0.createaddition.energy.NodeMovementBehaviour;
import com.mrh0.createaddition.index.CABlocks;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.nakou.createelectriccity.CreateElectricCity;
import dev.nakou.createelectriccity.content.smalllightbulb.SmallLightBulbBlock;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;

import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;
import static dev.nakou.createelectriccity.CreateElectricCity.REGISTRATE;

public class CECBlocks {
    // public static final CreateRegistrate REGISTRATE = CreateRegistrate.create("create_better_motors");
    static {
        REGISTRATE.defaultCreativeTab(CreateElectricCity.CREATIVE_TAB_KEY);
    }

    public static final BlockEntry<SmallLightBulbBlock> SMALL_LIGHT_BULB =
            REGISTRATE.block("small_light_bulb",  SmallLightBulbBlock::new)
                    .initialProperties(SharedProperties::stone)
                    .onRegister(movementBehaviour(new NodeMovementBehaviour()))
                    .item()
                    .transform(customItemModel())
                    .transform(axeOrPickaxe())
                    .recipe((c, p) ->
                            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 1)
                                    .pattern(" C ")
                                    .pattern("CSC")
                                    .pattern(" C ")
                                    .define('S', CABlocks.LARGE_CONNECTOR.get())
                                    //.define('C', CBMItems.REGGARFONITE_NUGGET.get())
                                    .unlockedBy("has_compass", has(CABlocks.LARGE_CONNECTOR.get()))
                                    .save(p, CreateElectricCity.asResource("crafting/heavy_connector"))
                    )
                    .register();

    public static void load() {  }
}
