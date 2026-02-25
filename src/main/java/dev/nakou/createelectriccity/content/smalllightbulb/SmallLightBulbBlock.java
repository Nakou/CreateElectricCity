package dev.nakou.createelectriccity.content.smalllightbulb;

import com.mrh0.createaddition.shapes.CAShapes;
import com.simibubi.create.foundation.utility.CreateLang;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import dev.nakou.createelectriccity.config.CommonConfig;
import dev.nakou.createelectriccity.content.common.AbstractLightBlock;
import dev.nakou.createelectriccity.content.common.LightMode;
import dev.nakou.createelectriccity.content.common.LightVariant;
import dev.nakou.createelectriccity.registry.CECBlockEntityTypes;
import dev.nakou.createelectriccity.registry.CECPartialModels;
import dev.nakou.createelectriccity.utils.StringFormattingTool;
import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

import java.util.List;

import static dev.nakou.createelectriccity.CreateElectricCity.MODID;

public class SmallLightBulbBlock extends AbstractLightBlock<SmallLightBulbBlockEntity> {
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
        return CONNECTOR_SHAPE.get(state.getValue(FACING).getOpposite());
    }

    public static void makeBlockState(DataGenContext<Block, SmallLightBulbBlock> ctx, RegistrateBlockstateProvider provider) {
        BlockModelProvider models = provider.models();
        MultiPartBlockStateBuilder builder = provider.getMultipartBuilder(ctx.get());
        ModelFile.ExistingModelFile modelOn = models.getExistingFile(CECPartialModels.SMALL_LIGHT_BULB_ON.modelLocation());
        ModelFile.ExistingModelFile modelOff = models.getExistingFile(CECPartialModels.SMALL_LIGHT_BULB_OFF.modelLocation());

        for(Direction direction: Direction.values()) {
            int horizontalAngle = direction == Direction.UP ? 180 : direction == Direction.DOWN ? 0 : 90;
            int  verticalAngle = ((int) direction.toYRot() + (direction.getAxis()
                    .isVertical() ? 180 : 0))% 360;
            builder.part()
                    .modelFile(modelOn)
                    .rotationX(horizontalAngle)
                    .rotationY(verticalAngle)
                    .addModel()
                    .condition(SmallLightBulbBlock.FACING, direction)
                    .condition(SmallLightBulbBlock.POWERED, Boolean.TRUE)
                    .end()
                    .part()
                    .modelFile(modelOff)
                    .rotationX(horizontalAngle)
                    .rotationY(verticalAngle)
                    .addModel()
                    .condition(SmallLightBulbBlock.FACING, direction)
                    .condition(SmallLightBulbBlock.POWERED, Boolean.FALSE)
                    .end();
        }
    }
}
