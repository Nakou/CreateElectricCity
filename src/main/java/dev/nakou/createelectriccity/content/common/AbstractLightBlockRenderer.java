package dev.nakou.createelectriccity.content.common;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mrh0.createaddition.rendering.WireNodeRenderer;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.render.RenderTypes;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.nakou.createelectriccity.CreateElectricCity;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.createmod.catnip.theme.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static dev.nakou.createelectriccity.content.common.AbstractLightBlock.FACING;

public abstract class AbstractLightBlockRenderer<A extends SmartBlockEntity> extends WireNodeRenderer<AbstractLightBlockEntity> {

    private final BlockRenderDispatcher dispatcher;
    public AbstractLightBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        dispatcher = context.getBlockRenderDispatcher();
    }


    public abstract void renderBeforeWires(AbstractLightBlockEntity be, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn);

    public abstract void renderAfterWires(AbstractLightBlockEntity be, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn);

    @Override
    public void render(AbstractLightBlockEntity be, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        super.render(be, partialTicks, stack, bufferIn, combinedLightIn, combinedOverlayIn);
        if(getLightModel(be) == null)
            return;
        BlockState blockState = be.getBlockState();
        stack.pushPose();
        float glow = be.glow.getValue(partialTicks);
        int color =  Math.min(100,(int) (glow/0.2f));

        if(be.glow.getValue()!=0) {

            applyFacingTransform(stack, blockState.getValue(FACING));

            SuperByteBuffer lightModel = CachedBuffers.partial(getLightModel(be), blockState)
                    .light((int) glow * 3 + 40)
                    .color(color, color, (int) (color * 0.8), 255)
                    .disableDiffuse();

            if(be.color == DyeColor.WHITE){
                lightModel.color(color, color, (int) (color * 0.8), 255);
            }else   lightModel.color(be.color.getTextColor());

            lightModel.renderInto(stack, bufferIn.getBuffer(RenderTypes.additive()));
        }
        stack.popPose();
    }

    /*
    @Override
    public void render(AbstractLightBlockEntity be, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        renderBeforeWires(be, partialTicks, stack, bufferIn, combinedLightIn, combinedOverlayIn);
        super.render(be, partialTicks, stack, bufferIn, combinedLightIn, combinedOverlayIn);
        renderAfterWires(be, partialTicks, stack, bufferIn, combinedLightIn, combinedOverlayIn);
        if(be.getBlockState().getValue(AbstractLightBlock.POWERED)){
            VertexConsumer vertexbuilder = bufferIn.getBuffer(RenderType.TRANSLUCENT);
            combinedLightIn = applyFlickering(be, partialTicks);
            CachedBuffers.partial(getLightModel(be), be.getBlockState()).color(Color.WHITE).light(combinedLightIn).renderInto(stack, vertexbuilder);
        }
    }*/

    /*@Override
    public void render(AbstractLightBlockEntity be, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        // 1. Render the wires from the parent class first
        super.render(be, partialTicks, stack, bufferIn, combinedLightIn, combinedOverlayIn);
        if(be.getBlockState().getValue(AbstractLightBlock.POWERED)) {
            // 2. Get your partial model (assuming this returns a BakedModel or PartialModel)
            var lightModel = this.getLightModel(be);
            if (lightModel == null) return;

            stack.pushPose();

            // Use the BlockRenderDispatcher to get the ModelBlockRenderer
            ModelBlockRenderer modelRenderer = Minecraft.getInstance().getBlockRenderer().getModelRenderer();

            // 3. Choose your RenderType.
            // For a light overlay, 'RenderType.cutout()' or 'RenderType.translucent()' is standard.
            // If you want it to look like a glowing 'eye' (always bright), use RenderType.eyes().
            VertexConsumer vertexConsumer = bufferIn.getBuffer(RenderType.translucent());

            // 4. Render the model with FULL_BRIGHT
            // 0xF000F0 represents the maximum possible light value in Minecraft
            modelRenderer.renderModel(
                    stack.last(),
                    vertexConsumer,
                    null, // BlockState (can be null for partial models)
                    lightModel.get(), // The actual BakedModel
                    1.0F, 1.0F, 1.0F, // RGB Tints
                    LightTexture.FULL_BRIGHT,
                    OverlayTexture.NO_OVERLAY
            );
        stack.popPose();
        }
    }*/

    /*public int applyFlickering(AbstractLightBlockEntity be, float partialTicks){
        float intensity = be.getFlickerIntensity(partialTicks);
        int brightness = (int)(intensity * 15);
        return LightTexture.pack(brightness, brightness);
    }*/

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
