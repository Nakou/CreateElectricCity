package dev.nakou.createelectriccity.content.variants;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.nakou.createelectriccity.content.GenericLightBlockEntity;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockEntity;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockRenderer;
import dev.nakou.createelectriccity.registry.CECPartialModels;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class OldLightBulbRenderer extends AbstractLightBlockRenderer<GenericLightBlockEntity> {
    public OldLightBulbRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public PartialModel getLightModel(AbstractLightBlockEntity be) {
        return CECPartialModels.OLD_LIGHT_BULB;
    }
}