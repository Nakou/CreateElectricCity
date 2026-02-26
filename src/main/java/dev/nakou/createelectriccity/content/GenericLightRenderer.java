package dev.nakou.createelectriccity.content;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.nakou.createelectriccity.CreateElectricCity;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockEntity;
import dev.nakou.createelectriccity.content.common.AbstractLightBlockRenderer;
import dev.nakou.createelectriccity.registry.CECPartialModels;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

public class GenericLightRenderer extends AbstractLightBlockRenderer<GenericLightBlockEntity> {
    public GenericLightRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public PartialModel getLightModel(AbstractLightBlockEntity be) {
        CreateElectricCity.LOGGER.error("You're using the GenericLightRenderer directly, that's not good.");
        return null;
    }
}