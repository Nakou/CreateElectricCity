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
import net.minecraft.world.level.block.state.BlockState;

public class HangingLightBlock extends GenericLightBlock {

    public HangingLightBlock(Properties properties, BlockEntityEntry<? extends GenericLightBlockEntity> blockEntityType, VoxelShaper shape, CommonConfig.LightConfig config, String internalName) {
        super(properties, blockEntityType, shape, config, internalName);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (level.getBlockState(pos.above()).isFaceSturdy(level, pos.above(), Direction.DOWN)) {
            return this.defaultBlockState();
        }
        return null;
    }
}
