package dev.nakou.createelectriccity.content.smalllightbulb;

import com.mrh0.createaddition.shapes.CAShapes;
import dev.nakou.createelectriccity.config.CommonConfig;
import dev.nakou.createelectriccity.content.common.AbstractLightBlock;
import dev.nakou.createelectriccity.registry.CECBlockEntityTypes;
import dev.nakou.createelectriccity.registry.CECShapes;
import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmallLightBulbBlock extends AbstractLightBlock<SmallLightBulbBlockEntity> {
    public static final VoxelShaper SHAPE = CECShapes.SMALL_LIGHT_BULB;
    public SmallLightBulbBlock(Properties properties) {
        super(properties);
    }

    @Override
    public String getInternalName() {
        return "small_light_bulb";
    }

    @Override
    public int getConsumption() {
        return CommonConfig.LANTERN.CONSUMPTION.getAsInt();
    }

    @Override
    public Class<SmallLightBulbBlockEntity> getBlockEntityClass() {
        return SmallLightBulbBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SmallLightBulbBlockEntity> getBlockEntityType() {
        return CECBlockEntityTypes.SMALL_LIGHT_BULB.get();
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CECBlockEntityTypes.SMALL_LIGHT_BULB.create(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING).getOpposite());
    }

}
