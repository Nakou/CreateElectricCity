package dev.nakou.createelectriccity.registry;

import com.mrh0.createaddition.energy.NodeMovementBehaviour;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.nakou.createelectriccity.CreateElectricCity;
import dev.nakou.createelectriccity.config.CommonConfig;
import dev.nakou.createelectriccity.content.lantern.LanternBlock;
import dev.nakou.createelectriccity.content.smalllightbulb.SmallLightBulbBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static dev.nakou.createelectriccity.CreateElectricCity.REGISTRATE;
import static dev.nakou.createelectriccity.content.common.AbstractLightBlock.LIGHT;

public class CECBlocks {

    static {
        REGISTRATE.defaultCreativeTab(CreateElectricCity.CREATIVE_TAB_KEY);
    }

    public static final BlockEntry<SmallLightBulbBlock> SMALL_LIGHT_BULB =
            REGISTRATE.block("small_light_bulb", SmallLightBulbBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .blockstate(BlockStateGen.directionalBlockProvider(true))
                    //.blockstate(SmallLightBulbBlock::makeBlockState)
                    .properties(p -> p.lightLevel(s -> s.getValue(LIGHT)))
                    .properties(p -> p
                            .mapColor(MapColor.TERRACOTTA_WHITE)
                            .strength(2.0f)
                            .sound(SoundType.GLASS)
                    )
                    .onRegister(movementBehaviour(new NodeMovementBehaviour()))
                    .item()
                    .transform(customItemModel())
                    .register();

    public static final BlockEntry<LanternBlock> LANTERN =
            REGISTRATE.block("lantern", LanternBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .blockstate(BlockStateGen.directionalBlockProvider(true))
                    .properties(p -> p.lightLevel(s -> s.getValue(LIGHT)))
                    .properties(p -> p
                            .mapColor(MapColor.TERRACOTTA_WHITE)
                            .strength(2.0f)
                            .sound(SoundType.GLASS)
                    )
                    .onRegister(movementBehaviour(new NodeMovementBehaviour()))
                    .item()
                    .transform(customItemModel())
                    .register();

    public static void load() {  }
}
