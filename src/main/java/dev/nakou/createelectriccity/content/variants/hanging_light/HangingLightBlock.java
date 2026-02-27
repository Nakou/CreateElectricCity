package dev.nakou.createelectriccity.content.variants.hanging_light;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.nakou.createelectriccity.config.CommonConfig;
import dev.nakou.createelectriccity.content.GenericLightBlock;
import dev.nakou.createelectriccity.content.GenericLightBlockEntity;
import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class HangingLightBlock extends GenericLightBlock {

    public HangingLightBlock(Properties properties, BlockEntityEntry<? extends GenericLightBlockEntity> blockEntityType, VoxelShaper shape, CommonConfig.LightConfig config, String internalName) {
        super(properties, blockEntityType, shape, config, internalName);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos anchorPos = pos.above();
        BlockState anchorState = level.getBlockState(anchorPos);

        return anchorState.isFaceSturdy(level, anchorPos, Direction.DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        // If the block above can support us, allow placement
        if (this.canSurvive(this.defaultBlockState(), level, pos)) {
            return super.getStateForPlacement(context);
        }

        return null;
    }
}
