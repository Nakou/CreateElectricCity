package dev.nakou.createelectriccity.content.smalllightbulb;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockEntity;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockRenderer;
import dev.nakou.createelectriccity.registry.CECPartialModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class SmallLightBulbRenderer extends AbstractLightBlockRenderer<AbstractLightBlockEntity> {
    public SmallLightBulbRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void renderBeforeWires(AbstractLightBlockEntity be, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

    }

    @Override
    public void renderAfterWires(AbstractLightBlockEntity be, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

    }

    @Override
    public PartialModel getLightModel(AbstractLightBlockEntity be) {
        if(be.getBlockState().getValue(BlockStateProperties.POWERED)){
            return CECPartialModels.SMALL_LIGHT_BULB_LIGHT;
        }
        return null;
    }

}