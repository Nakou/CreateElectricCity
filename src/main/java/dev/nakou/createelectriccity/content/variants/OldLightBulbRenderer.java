package dev.nakou.createelectriccity.content.variants;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.nakou.createelectriccity.content.GenericLightBlockEntity;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockEntity;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockRenderer;
import dev.nakou.createelectriccity.registry.CECPartialModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.DyeColor;

public class OldLightBulbRenderer extends AbstractLightBlockRenderer<GenericLightBlockEntity> {
    public OldLightBulbRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    boolean hasOverwrittenColorYet = false;

    @Override
    public void render(AbstractLightBlockEntity be, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn){
        if(!hasOverwrittenColorYet){ // can't find a way to get the BE in the constructor...
            be.setColor(DyeColor.ORANGE);
            hasOverwrittenColorYet = true;
        }
        super.render(be, partialTicks, stack, bufferIn, combinedLightIn, combinedOverlayIn);
    }

    @Override
    public PartialModel getLightModel(AbstractLightBlockEntity be) {
        return CECPartialModels.OLD_LIGHT_BULB;
    }
}