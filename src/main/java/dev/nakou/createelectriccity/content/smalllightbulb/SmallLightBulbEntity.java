package dev.nakou.createelectriccity.content.smalllightbulb;

import com.mrh0.createaddition.blocks.connector.SmallLightConnectorBlock;
import com.mrh0.createaddition.blocks.connector.base.AbstractConnectorBlockEntity;
import com.mrh0.createaddition.blocks.connector.ConnectorType;
import com.mrh0.createaddition.blocks.connector.base.AbstractConnectorBlock;
import com.mrh0.createaddition.energy.network.EnergyNetwork;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import dev.nakou.createelectriccity.config.CommonConfig;
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



public class SmallLightBulbEntity extends AbstractLightBlockEntity {

    private final static float OFFSET_HEIGHT = 1f;
    public final static Vec3 OFFSET_DOWN = new Vec3(0f, -OFFSET_HEIGHT/16f, 0f);
    public final static Vec3 OFFSET_UP = new Vec3(0f, OFFSET_HEIGHT/16f, 0f);
    public final static Vec3 OFFSET_NORTH = new Vec3(0f, 0f, -OFFSET_HEIGHT/16f);
    public final static Vec3 OFFSET_WEST = new Vec3(-OFFSET_HEIGHT/16f, 0f, 0f);
    public final static Vec3 OFFSET_SOUTH = new Vec3(0f, 0f, OFFSET_HEIGHT/16f);
    public final static Vec3 OFFSET_EAST = new Vec3(OFFSET_HEIGHT/16f, 0f, 0f);

    private int posTimeOffset = 0;

    public SmallLightBulbEntity(BlockEntityType<?> blockEntityTypeIn, BlockPos pos, BlockState state) {
        super(blockEntityTypeIn, pos, state);

        posTimeOffset = 10 + (Math.abs(pos.getX()*31 + pos.getY()*45 + pos.getZ()*33) % 7) * 3;
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

    @Override
    public boolean isEnergyInput(Direction side) {
        return this.getBlockState().getValue(AbstractConnectorBlock.FACING) == side;
    }

    @Override
    public boolean isEnergyOutput(Direction side) {
        return false;
    }

    @Override
    public int getMaxIn() {
        return CommonConfig.LIGHTS_MAX_INPUT.get();
    }

    @Override
    public int getMaxOut() {
        return CommonConfig.LIGHTS_MAX_OUTPUT.get();
    }

    @Override
    public int getNodeCount() {
        return 4;
    }

    @Override
    public Vec3 getNodeOffset(int node) {
        return switch (getBlockState().getValue(AbstractConnectorBlock.FACING)) {
            case DOWN -> OFFSET_DOWN;
            case UP -> OFFSET_UP;
            case NORTH -> OFFSET_NORTH;
            case WEST -> OFFSET_WEST;
            case SOUTH -> OFFSET_SOUTH;
            case EAST -> OFFSET_EAST;
        };
    }

    @Override
    public ConnectorType getConnectorType() {
        return ConnectorType.Large;
    }

    public int getMaxWireLength() {
        return CommonConfig.LIGHTS_MAX_LENGTH.get();
    }

    private int tickToggleTimer = 0;
    @Override
    protected void specialTick() {
        if(getLevel() == null) return;
        if(level.isClientSide()) return;
        EnergyNetwork network = getNetwork(0);
        if (network != null) network.demand(1);
        boolean hasEnergy = network != null && network.pull(CommonConfig.SMALL_LIGHT_BUBBLE.CONSUMPTION.get(), false) > 0;
        tickToggleTimer = tickToggleTimer + (hasEnergy ? 1 : -1);

        if (tickToggleTimer >= posTimeOffset) {
            tickToggleTimer = posTimeOffset;
            if (!getBlockState().getValue(SmallLightConnectorBlock.POWERED))
                getLevel().setBlockAndUpdate(getBlockPos(), getBlockState()
                        .setValue(SmallLightConnectorBlock.POWERED, true));
        }

        if (tickToggleTimer <= -posTimeOffset) {
            tickToggleTimer = -posTimeOffset;
            if (getBlockState().getValue(SmallLightConnectorBlock.POWERED))
                getLevel().setBlockAndUpdate(getBlockPos(), getBlockState()
                        .setValue(SmallLightConnectorBlock.POWERED, false));
        }
    }

}
