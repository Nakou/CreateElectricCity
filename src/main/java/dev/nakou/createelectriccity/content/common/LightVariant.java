package dev.nakou.createelectriccity.content.common;

import com.mrh0.createaddition.blocks.connector.base.ConnectorVariant;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.decoration.girder.GirderBlock;
import com.simibubi.create.content.decoration.girder.GirderEncasedShaftBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public enum LightVariant implements StringRepresentable {
    Default("default"),
    Girder("girder");

    private String name;

    private LightVariant(String name) {
        this.name = name;
    }

    public String getSerializedName() {
        return this.name;
    }

    public static LightVariant test(Level level, BlockPos pos, Direction face) {
        BlockState state = level.getBlockState(pos);
        if (state.is((Block) AllBlocks.METAL_GIRDER.get())) {
            if ((Boolean)state.getValue(GirderBlock.TOP) && face == Direction.UP) {
                return Default;
            } else if ((Boolean)state.getValue(GirderBlock.BOTTOM) && face == Direction.DOWN) {
                return Default;
            } else if ((Boolean)state.getValue(GirderBlock.X) && face.getAxis() == Direction.Axis.X) {
                return Default;
            } else {
                return (Boolean)state.getValue(GirderBlock.Z) && face.getAxis() == Direction.Axis.Z ? Default : Girder;
            }
        } else if (state.is((Block)AllBlocks.METAL_GIRDER_ENCASED_SHAFT.get())) {
            if (!(Boolean)state.getValue(GirderEncasedShaftBlock.TOP) && face == Direction.UP) {
                return Girder;
            } else {
                return !(Boolean)state.getValue(GirderEncasedShaftBlock.BOTTOM) && face == Direction.DOWN ? Girder : Default;
            }
        } else {
            return Default;
        }
    }
}