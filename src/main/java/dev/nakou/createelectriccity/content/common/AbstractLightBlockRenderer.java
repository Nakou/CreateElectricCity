package dev.nakou.createelectriccity.content.common;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrh0.createaddition.rendering.WireNodeRenderer;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.render.RenderTypes;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static dev.nakou.createelectriccity.content.common.AbstractLightBlock.FACING;

public abstract class AbstractLightBlockRenderer<A extends SmartBlockEntity> extends WireNodeRenderer<AbstractLightBlockEntity> {

    public AbstractLightBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(AbstractLightBlockEntity be, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        super.render(be, partialTicks, stack, bufferIn, combinedLightIn, combinedOverlayIn);
        if(!be.getBlockState().getValue(BlockStateProperties.POWERED))
            return;
        if(getLightModel(be) == null)
            return;
        if(be.getLevel() == null)
            return;
        BlockState blockState = be.getBlockState();
        stack.pushPose();

        float time = (be.getLevel().getGameTime() + partialTicks);
        float pulse = (float) (Math.sin(time * 0.08f) * 0.05f + 1.0f);
        float flicker = (float) (Math.sin(time * 0.35f) * Math.sin(time * 0.12f) * 0.15f + 0.85f);

        float glow = be.glow.getValue(partialTicks) * flicker;
        int color =  Math.min(100,(int) (glow/0.2f));

        if(be.glow.getValue()!=0) {

            applyFacingTransform(stack, blockState.getValue(FACING));

            stack.translate(0.5, 0.5, 0.5);
            stack.scale(pulse, pulse, pulse);
            stack.translate(-0.5, -0.5, -0.5);

            SuperByteBuffer lightModel = CachedBuffers.partial(getLightModel(be), blockState)
                    .light((int) glow * 3 + 40)
                    .color(color, color, (int) (color * 0.8), 255)
                    .disableDiffuse();

            if(be.color == DyeColor.WHITE){
                lightModel.color(color, color, (int) (color * 0.8), 255);
            }
            else{
                int c = be.color.getTextColor();
                int r = (int) (((c >> 16) & 0xFF) * flicker);
                int g = (int) (((c >> 8) & 0xFF) * flicker);
                int b = (int) ((c & 0xFF) * flicker);
                lightModel.color(r, g, b, 255);
                //lightModel.color(be.color.getTextColor());
            }

            lightModel.renderInto(stack, bufferIn.getBuffer(RenderTypes.additive()));
        }
        stack.popPose();
    }

    private void applyFacingTransform(PoseStack stack, Direction facing) {
        // Translate to block center, rotate, then translate back
        stack.translate(0.5, 0.5, 0.5);
        switch (facing) {
            case DOWN -> {} // default model direction, no rotation needed
            case UP    -> stack.mulPose(Axis.XP.rotationDegrees(180));
            case SOUTH -> stack.mulPose(Axis.XP.rotationDegrees(-90));
            case NORTH -> stack.mulPose(Axis.XP.rotationDegrees(90));
            case EAST  -> stack.mulPose(Axis.ZP.rotationDegrees(90));
            case WEST  -> stack.mulPose(Axis.ZP.rotationDegrees(-90));
        }
        stack.translate(-0.5, -0.5, -0.5);
    }

    public abstract PartialModel getLightModel(AbstractLightBlockEntity be);
}
