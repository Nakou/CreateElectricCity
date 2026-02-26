package dev.nakou.createelectriccity.content.lantern;

import com.mrh0.createaddition.shapes.CAShapes;
import dev.nakou.createelectriccity.config.CommonConfig;
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

public class LanternBlock extends AbstractLightBlock<LanternBlockEntity> {
    public static final VoxelShaper SHAPE = CAShapes.shape(4, 0, 4, 12, 11, 12).forDirectional();
    public LanternBlock(Properties properties) {
        super(properties);
    }

    @Override
    public String getInternalName() {
        return "lantern";
    }

    @Override
    public int getConsumption() {
        return CommonConfig.LANTERN.CONSUMPTION.getAsInt();
    }

    @Override
    public Class<LanternBlockEntity> getBlockEntityClass() {
        return LanternBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends LanternBlockEntity> getBlockEntityType() {
        return CECBlockEntityTypes.LANTERN.get();
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CECBlockEntityTypes.LANTERN.create(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING).getOpposite());
    }

}
