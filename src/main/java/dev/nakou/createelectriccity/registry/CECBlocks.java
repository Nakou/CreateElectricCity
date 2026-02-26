package dev.nakou.createelectriccity.registry;

import com.mrh0.createaddition.energy.NodeMovementBehaviour;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.nakou.createelectriccity.CreateElectricCity;
import dev.nakou.createelectriccity.config.CommonConfig;
import dev.nakou.createelectriccity.content.GenericLightBlock;
import dev.nakou.createelectriccity.content.variants.hanging_light.HangingLightBlock;

import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static dev.nakou.createelectriccity.CreateElectricCity.REGISTRATE;

public class CECBlocks {

    static {
        REGISTRATE.defaultCreativeTab(CreateElectricCity.CREATIVE_TAB_KEY);
    }

    public static final BlockEntry<GenericLightBlock> BIG_LIGHT_BULB =
            REGISTRATE.block("big_light_bulb",
                            p -> new GenericLightBlock(p, CECBlockEntityTypes.BIG_LIGHT_BULB,
                                    CECShapes.BIG_LIGHT_BULB, CommonConfig.BIG_LIGHT_BULB, "big_light_bulb"))
                    .initialProperties(SharedProperties::softMetal)
                    .blockstate(BlockStateGen.directionalBlockProvider(true))
                    .onRegister(movementBehaviour(new NodeMovementBehaviour()))
                    .item()
                    .transform(customItemModel())
                    .register();

    public static final BlockEntry<GenericLightBlock> OLD_LIGHT_BULB =
            REGISTRATE.block("old_light_bulb",
                            p -> new GenericLightBlock(p, CECBlockEntityTypes.OLD_LIGHT_BULB,
                                    CECShapes.OLD_LIGHT_BULB, CommonConfig.OLD_LIGHT_BULB, "old_light_bulb"))
                    .initialProperties(SharedProperties::softMetal)
                    .blockstate(BlockStateGen.directionalBlockProvider(true))
                    .onRegister(movementBehaviour(new NodeMovementBehaviour()))
                    .item()
                    .transform(customItemModel())
                    .register();

    public static final BlockEntry<GenericLightBlock> SMALL_LIGHT_BULB =
            REGISTRATE.block("small_light_bulb",
                            p -> new GenericLightBlock(p, CECBlockEntityTypes.SMALL_LIGHT_BULB,
                                    CECShapes.SMALL_LIGHT_BULB, CommonConfig.SMALL_LIGHT_BULB, "small_light_bulb"))
                    .initialProperties(SharedProperties::softMetal)
                    .blockstate(BlockStateGen.directionalBlockProvider(true))
                    .onRegister(movementBehaviour(new NodeMovementBehaviour()))
                    .item()
                    .transform(customItemModel())
                    .register();

    public static final BlockEntry<HangingLightBlock> HANGING_LIGHT =
            REGISTRATE.block("hanging_light",
                            p -> new HangingLightBlock(p, CECBlockEntityTypes.HANGING_LIGHT,
                                    CECShapes.HANGING_LIGHT, CommonConfig.HANGING_LIGHT, "hanging_light"))
                    .initialProperties(SharedProperties::softMetal)
                    .blockstate(BlockStateGen.directionalBlockProvider(true))
                    .onRegister(movementBehaviour(new NodeMovementBehaviour()))
                    .item()
                    .transform(customItemModel())
                    .register();

    public static final BlockEntry<GenericLightBlock> LANTERN =
            REGISTRATE.block("lantern",
                            p -> new GenericLightBlock(p, CECBlockEntityTypes.LANTERN,
                                    CECShapes.LANTERN, CommonConfig.LANTERN, "lantern"))
                    .initialProperties(SharedProperties::softMetal)
                    .blockstate(BlockStateGen.directionalBlockProvider(true))
                    .onRegister(movementBehaviour(new NodeMovementBehaviour()))
                    .item()
                    .transform(customItemModel())
                    .register();

    public static void load() {  }
}
