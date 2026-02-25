package dev.nakou.createelectriccity.content.smalllightbulb;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockEntity;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockRenderer;
import dev.nakou.createelectriccity.registry.CECPartialModels;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class SmallLightBulbRenderer extends AbstractLightBlockRenderer<AbstractLightBlockEntity> {
    public SmallLightBulbRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public PartialModel getLightModel(AbstractLightBlockEntity be) {
        return CECPartialModels.SMALL_LIGHT_BULB_LIGHT;
    }
}