package dev.nakou.createelectriccity.registry;

import com.mrh0.createaddition.energy.NodeMovementBehaviour;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.nakou.createelectriccity.CreateElectricCity;
import dev.nakou.createelectriccity.content.smalllightbulb.SmallLightBulbBlock;

import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static dev.nakou.createelectriccity.CreateElectricCity.REGISTRATE;

public class CECBlocks {

    static {
        REGISTRATE.defaultCreativeTab(CreateElectricCity.CREATIVE_TAB_KEY);
    }

    public static final BlockEntry<SmallLightBulbBlock> SMALL_LIGHT_BULB =
            REGISTRATE.block("small_light_bulb", SmallLightBulbBlock::new)
                    .initialProperties(SharedProperties::stone)
                    .blockstate(SmallLightBulbBlock::makeBlockState)
                    .onRegister(movementBehaviour(new NodeMovementBehaviour()))
                    .item()
                    .transform(customItemModel())
                    .register();

    public static void load() {  }
}
