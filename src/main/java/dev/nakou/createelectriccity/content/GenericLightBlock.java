package dev.nakou.createelectriccity.content;

import com.mrh0.createaddition.shapes.CAShapes;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.nakou.createelectriccity.config.CommonConfig;
import dev.nakou.createelectriccity.content.common.AbstractLightBlock;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockEntity;
import dev.nakou.createelectriccity.registry.CECBlockEntityTypes;
import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GenericLightBlock extends AbstractLightBlock<GenericLightBlockEntity> {
    public static final VoxelShaper SHAPE = CAShapes.shape(5, 0, 5, 11, 7, 11).forDirectional();
    protected VoxelShaper shape;
    protected BlockEntityEntry<? extends GenericLightBlockEntity> blockEntityType;
    protected CommonConfig.LightConfig config;
    protected String internalName;
    public GenericLightBlock(Properties properties, BlockEntityEntry<? extends GenericLightBlockEntity> blockEntityType, VoxelShaper shape, CommonConfig.LightConfig config, String internalName) {
        super(properties);
        this.shape = shape;
        this.blockEntityType = blockEntityType;
        this.config = config;
        this.internalName = internalName;
    }

    @Override
    public String getInternalName() {
        return internalName;
    }

    @Override
    public int getConsumption() {
        return config.CONSUMPTION.getAsInt();
    }

    public CommonConfig.LightConfig getConfig(){
        return config;
    }

    @Override
    public Class<GenericLightBlockEntity> getBlockEntityClass() {
        return GenericLightBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends GenericLightBlockEntity> getBlockEntityType() {
        return blockEntityType.get();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING).getOpposite());
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand pHand, BlockHitResult hitResult) {
        if (player.isShiftKeyDown())
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if(!config.DYEABLE.get())
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        ItemStack heldItem = player.getItemInHand(pHand);
        AbstractLightBlockEntity be = getBlockEntity(level, pos);
        DyeColor dye = DyeColor.getColor(heldItem);
        if (be != null) {
            if (dye != null) {
                level.playSound(null, pos, SoundEvents.DYE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                be.setColor(dye);
                return ItemInteractionResult.SUCCESS;
            }
        }


        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
