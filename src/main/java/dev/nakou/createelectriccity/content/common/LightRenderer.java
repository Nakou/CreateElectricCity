package dev.nakou.createelectriccity.content.common;

import com.mrh0.createaddition.rendering.WireNodeRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class LightRenderer extends WireNodeRenderer<AbstractLightBlockEntity> {
    public LightRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }
}