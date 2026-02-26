package dev.nakou.createelectriccity.content;

import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import dev.nakou.createelectriccity.config.CommonConfig;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class GenericLightBlockEntity extends AbstractLightBlockEntity {

    protected CommonConfig.LightConfig config;

    public GenericLightBlockEntity(BlockEntityType<?> blockEntityTypeIn, BlockPos pos, BlockState state) {
        super(blockEntityTypeIn, pos, state);
        this.config = ((GenericLightBlock)state.getBlock()).getConfig();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> list) {
    }

    public int getConsumption() {
        return config.CONSUMPTION.get();
    }

    public int getLightProduction() {
        return config.LUMEN.getAsInt();
    }
}
