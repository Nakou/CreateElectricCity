package dev.nakou.createelectriccity.content.variants;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockEntity;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockRenderer;
import dev.nakou.createelectriccity.registry.CECPartialModels;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

public class LanternRenderer extends AbstractLightBlockRenderer<AbstractLightBlockEntity> {
    public LanternRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public PartialModel getLightModel(AbstractLightBlockEntity be) {
        return CECPartialModels.LANTERN;
    }

    @Override
    public void applyFacingTransform(PoseStack stack, Direction facing){}
}