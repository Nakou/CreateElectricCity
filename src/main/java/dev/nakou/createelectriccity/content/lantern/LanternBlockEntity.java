package dev.nakou.createelectriccity.content.lantern;

import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import dev.nakou.createelectriccity.config.CommonConfig;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockEntity;
import dev.nakou.createelectriccity.registry.CECBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import java.util.List;



public class LanternBlockEntity extends AbstractLightBlockEntity {

    public LanternBlockEntity(BlockEntityType<?> blockEntityTypeIn, BlockPos pos, BlockState state) {
        super(blockEntityTypeIn, pos, state);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                CECBlockEntityTypes.LANTERN.get(),
                (be, context) -> be.internal
        );
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> list) {}

    public int getConsumption(){
        return CommonConfig.LANTERN.CONSUMPTION.get();
    }

    public int getLightProduction(){ return  CommonConfig.LANTERN.LUMEN.getAsInt(); }

}
