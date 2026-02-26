package dev.nakou.createelectriccity.content.variants;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.nakou.createelectriccity.content.GenericLightBlockEntity;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockEntity;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockRenderer;
import dev.nakou.createelectriccity.registry.CECPartialModels;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class BigLightBulbRenderer extends AbstractLightBlockRenderer<GenericLightBlockEntity> {
    public BigLightBulbRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public PartialModel getLightModel(AbstractLightBlockEntity be) {
        return CECPartialModels.BIG_LIGHT_BULB;
    }
}