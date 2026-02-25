package dev.nakou.createelectriccity.content.smalllightbulb;

import com.mrh0.createaddition.shapes.CAShapes;
import dev.nakou.createelectriccity.content.common.AbstractLightBlock;
import dev.nakou.createelectriccity.registry.CECBlockEntityTypes;
import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmallLightBulbBlock extends AbstractLightBlock<SmallLightBulbBlockEntity> {
    public static final VoxelShaper SHAPE = CAShapes.shape(5, 0, 5, 11, 9, 11).forDirectional();
    public SmallLightBulbBlock(Properties properties) {
        super(properties);
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
