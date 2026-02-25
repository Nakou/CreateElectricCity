package dev.nakou.createelectriccity.content.smalllightbulb;

import com.mrh0.createaddition.energy.network.EnergyNetwork;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import dev.nakou.createelectriccity.config.CommonConfig;
import dev.nakou.createelectriccity.content.common.AbstractLightBlock;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockEntity;
import dev.nakou.createelectriccity.registry.CECBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import java.util.List;



public class SmallLightBulbBlockEntity extends AbstractLightBlockEntity {

    public SmallLightBulbBlockEntity(BlockEntityType<?> blockEntityTypeIn, BlockPos pos, BlockState state) {
        super(blockEntityTypeIn, pos, state);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                CECBlockEntityTypes.SMALL_LIGHT_BULB.get(),
                (be, context) -> be.internal
        );
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> list) {}

    public int getConsumption(){
        return CommonConfig.SMALL_LIGHT_BUBBLE.CONSUMPTION.get();
    }

    public int getLightProduction(){ return  CommonConfig.SMALL_LIGHT_BUBBLE.LUMENS.getAsInt(); }

}
