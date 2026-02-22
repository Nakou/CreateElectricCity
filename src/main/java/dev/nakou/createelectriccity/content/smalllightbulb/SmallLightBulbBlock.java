package dev.nakou.createelectriccity.content.smalllightbulb;

import com.mrh0.createaddition.blocks.connector.base.AbstractConnectorBlock;
import com.mrh0.createaddition.shapes.CAShapes;
import com.simibubi.create.foundation.utility.CreateLang;
import dev.nakou.createelectriccity.config.CommonConfig;
import dev.nakou.createelectriccity.content.common.AbstractLightBlock;
import dev.nakou.createelectriccity.registry.CECBlockEntityTypes;
import dev.nakou.createelectriccity.utils.StringFormattingTool;
import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SmallLightBulbBlock extends AbstractLightBlock<SmallLightBulbEntity> {
    public static final VoxelShaper CONNECTOR_SHAPE = CAShapes.shape(5, 0, 5, 11, 9, 11).forDirectional();
    public SmallLightBulbBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        if(Screen.hasShiftDown()){
            tooltip.add(CreateLang.translate("createelectriccity.small_light_bulb.tooltip.summary")
                    .style(ChatFormatting.AQUA)
                    .component());
            tooltip.add(CreateLang.translate("createelectriccity.small_light_bulb.tooltip.condition1")
                    .style(ChatFormatting.DARK_GRAY)
                    .component());
            tooltip.add(CreateLang.translate("createelectriccity.small_light_bulb.tooltip.behaviour1")
                    .style(ChatFormatting.AQUA)
                    .component());
            tooltip.add(CreateLang.translate("createelectriccity.small_light_bulb.tooltip.condition2")
                    .style(ChatFormatting.DARK_GRAY)
                    .component());
            tooltip.add(CreateLang.translate("createelectriccity.small_light_bulb.tooltip.behaviour2")
                    .style(ChatFormatting.AQUA)
                    .component());
        }
        else {
            tooltip.add(CreateLang.translate("tooltip.createelectriccity.transfers")
                    .style(ChatFormatting.GRAY)
                    .component());
            tooltip.add(CreateLang.text(" ").translate("tooltip.createelectriccity.energy_per_tick",
                            StringFormattingTool.formatLong(CommonConfig.SMALL_LIGHT_BUBBLE.CONSUMPTION.get()))
                    .style(ChatFormatting.AQUA)
                    .component());

            tooltip.add(CreateLang.translate("tooltip.createelectriccity.shift")
                    .style(ChatFormatting.DARK_GRAY)
                    .component());

        }
    }

    @Override
    public Class<SmallLightBulbEntity> getBlockEntityClass() {
        return SmallLightBulbEntity.class;
    }

    @Override
    public BlockEntityType<? extends SmallLightBulbEntity> getBlockEntityType() {
        return CECBlockEntityTypes.SMALL_LIGHT_BULB.get();
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CECBlockEntityTypes.SMALL_LIGHT_BULB.create(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return CONNECTOR_SHAPE.get(state.getValue(FACING).getOpposite());
    }
}
