package dev.nakou.createelectriccity.content.variants.hanging_light;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.nakou.createelectriccity.content.GenericLightBlockEntity;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockEntity;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockRenderer;
import dev.nakou.createelectriccity.registry.CECPartialModels;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class HangingLightRenderer extends AbstractLightBlockRenderer<GenericLightBlockEntity> {
    public HangingLightRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public PartialModel getLightModel(AbstractLightBlockEntity be) {
        return CECPartialModels.BIG_LIGHT_BULB;
    }
}