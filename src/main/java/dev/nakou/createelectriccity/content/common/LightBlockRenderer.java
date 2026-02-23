package dev.nakou.createelectriccity.content.common;

import com.mrh0.createaddition.rendering.WireNodeRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class LightBlockRenderer extends WireNodeRenderer<AbstractLightBlockEntity> {
    public LightBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }
}