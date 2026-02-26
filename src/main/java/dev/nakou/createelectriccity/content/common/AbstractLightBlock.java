package dev.nakou.createelectriccity.content.common;

import com.mrh0.createaddition.energy.IWireNode;
import com.mrh0.createaddition.energy.NodeRotation;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.CreateLang;
import dev.nakou.createelectriccity.config.CommonConfig;
import dev.nakou.createelectriccity.utils.StringFormattingTool;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractLightBlock<BE extends AbstractLightBlockEntity> extends Block implements IBE<BE>, IWrenchable {
    public static final DirectionProperty FACING;
    public static final EnumProperty<LightMode> MODE;
    public static final EnumProperty<LightVariant> VARIANT;
    public static final IntegerProperty LIGHT = BlockStateProperties.LEVEL;
    private static final VoxelShape boxwe;
    private static final VoxelShape boxsn;

    public static final BooleanProperty POWERED;
    public static final IntegerProperty LEVEL;

    public AbstractLightBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.defaultBlockState()
                .setValue(FACING, Direction.NORTH))
                .setValue(MODE, LightMode.None))
                .setValue(NodeRotation.ROTATION, NodeRotation.NONE))
                .setValue(VARIANT, LightVariant.Default));
    }

    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return (Boolean)state.getValue(POWERED) ? state.getValue(LEVEL) : 0;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, MODE, NodeRotation.ROTATION, VARIANT, POWERED, LEVEL});
    }

    public BlockState getStateForPlacement(BlockPlaceContext c) {
        Direction dir = c.getClickedFace().getOpposite();
        LightMode mode = LightMode.test(c.getLevel(), c.getClickedPos().relative(dir), c.getClickedFace());
        LightVariant variant = LightVariant.test(c.getLevel(), c.getClickedPos().relative(dir), c.getClickedFace());
        return ((BlockState)(BlockState)((BlockState)((BlockState)this
                .defaultBlockState().setValue(FACING, dir))
                .setValue(MODE, mode)).setValue(VARIANT, variant))
                .setValue(POWERED, false)
                .setValue(LEVEL, 0);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand pHand, BlockHitResult hitResult) {
        if (player.isShiftKeyDown())
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

    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
        if (!level.isClientSide()) {
            BlockEntity te = level.getBlockEntity(pos);
            if (te != null) {
                if (te instanceof IWireNode) {
                    IWireNode cte = (IWireNode)te;
                    cte.dropWires(level, !player.isCreative());
                }
            }
        }
    }

    public abstract String getInternalName();

    public abstract int getConsumption();

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        String blockName = getInternalName();
        if(Screen.hasShiftDown()){
            tooltip.add(CreateLang.translate("createelectriccity.common.tooltip.summary")
                    .style(ChatFormatting.AQUA)
                    .component());
            tooltip.add(CreateLang.translate("createelectriccity.common.tooltip.condition1", blockName)
                    .style(ChatFormatting.DARK_GRAY)
                    .component());
            tooltip.add(CreateLang.translate("createelectriccity.common.tooltip.behaviour1", blockName)
                    .style(ChatFormatting.AQUA)
                    .component());
            tooltip.add(CreateLang.translate("createelectriccity.common.tooltip.condition2", blockName)
                    .style(ChatFormatting.DARK_GRAY)
                    .component());
            tooltip.add(CreateLang.translate("createelectriccity.common.tooltip.behaviour2", blockName)
                    .style(ChatFormatting.AQUA)
                    .component());
        }
        else {
            tooltip.add(CreateLang.translate("create.tooltip.createelectriccity.using")
                    .style(ChatFormatting.GRAY)
                    .component());
            tooltip.add(CreateLang.text(" ").translate("tooltip.createelectriccity.energy_per_tick",
                            StringFormattingTool.formatLong(getConsumption()))
                    .style(ChatFormatting.AQUA)
                    .component());

            tooltip.add(CreateLang.translate("tooltip.createelectriccity.shift")
                    .style(ChatFormatting.DARK_GRAY)
                    .component());

        }
    }

    public InteractionResult onWrenched(BlockState state, UseOnContext c) {
        if (c.getLevel().isClientSide()) {
            c.getLevel().playLocalSound((double)c.getClickedPos().getX(), (double)c.getClickedPos().getY(), (double)c.getClickedPos().getZ(), SoundEvents.BONE_BLOCK_HIT, SoundSource.BLOCKS, 1.0F, 1.0F, false);
        }

        return InteractionResult.PASS;
    }

    public InteractionResult onSneakWrenched(BlockState state, UseOnContext c) {
        BlockEntity be = c.getLevel().getBlockEntity(c.getClickedPos());
        if (be == null) {
            return IWrenchable.super.onSneakWrenched(state, c);
        } else if (be instanceof IWireNode) {
            IWireNode cbe = (IWireNode)be;
            if (!c.getLevel().isClientSide() && c.getPlayer() != null) {
                cbe.dropWires(c.getLevel(), c.getPlayer(), !c.getPlayer().isCreative());
            }
            return IWrenchable.super.onSneakWrenched(state, c);
        } else {
            return IWrenchable.super.onSneakWrenched(state, c);
        }
    }

    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        BlockEntity blockEntity = state.hasBlockEntity() ? worldIn.getBlockEntity(pos) : null;
        if (blockEntity != null && blockEntity instanceof AbstractLightBlockEntity) {
            ((AbstractLightBlockEntity)blockEntity).updateExternalEnergyStorage();
        }

        if (!state.canSurvive(worldIn, pos)) {
            dropResources(state, worldIn, pos, blockEntity);
            if (blockEntity instanceof IWireNode) {
                ((IWireNode)blockEntity).dropWires(worldIn, true);
            }

            worldIn.removeBlock(pos, false);

            for(Direction direction : Direction.values()) {
                worldIn.updateNeighborsAt(pos.relative(direction), this);
            }
        }

    }

    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Direction dir = (Direction)state.getValue(FACING);
        return !Shapes.joinIsNotEmpty(world.getBlockState(pos.relative(dir)).getBlockSupportShape(world, pos.relative(dir)).getFaceShape(dir.getOpposite()), boxwe, BooleanOp.ONLY_SECOND)
                || !Shapes.joinIsNotEmpty(world.getBlockState(pos.relative(dir)).getBlockSupportShape(world, pos.relative(dir)).getFaceShape(dir.getOpposite()), boxsn, BooleanOp.ONLY_SECOND)
                || world.getBlockState(pos.relative(dir)).isFaceSturdy(world, pos, dir.getOpposite(), SupportType.CENTER)
                || (Boolean) CommonConfig.CONNECTOR_IGNORE_FACE_CHECK.get();
    }

    /*public BlockState transform(BlockState state, StructureTransform transform) {
        NodeRotation rotation = NodeRotation.get(transform.rotationAxis, transform.rotation);
        if (transform.mirror != null) {
            state = this.mirror(state, transform.mirror);
        }

        state = (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING), false));
        return (BlockState)state.setValue(NodeRotation.ROTATION, rotation);
    }*/

    static {
        POWERED = BlockStateProperties.POWERED;
        LEVEL = BlockStateProperties.LEVEL;
        FACING = BlockStateProperties.FACING;
        MODE = EnumProperty.create("mode", LightMode.class);
        VARIANT = EnumProperty.create("variant", LightVariant.class);
        boxwe = Block.box((double)0.0F, (double)7.0F, (double)7.0F, (double)10.0F, (double)9.0F, (double)9.0F);
        boxsn = Block.box((double)7.0F, (double)7.0F, (double)0.0F, (double)9.0F, (double)9.0F, (double)10.0F);
    }
}