package dev.nakou.createelectriccity.content.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

public enum LightMode implements StringRepresentable {
    Push("push"),
    Pull("pull"),
    None("none"),
    Passive("passive");

    private String name;

    private LightMode(String name) {
        this.name = name;
    }

    public String getSerializedName() {
        return this.name;
    }

    public LightMode getNext() {
        LightMode var10000;
        switch (this.ordinal()) {
            case 0 -> var10000 = None;
            case 1 -> var10000 = Push;
            case 2 -> var10000 = Pull;
            default -> var10000 = None;
        }

        return var10000;
    }

    public MutableComponent getTooltip() {
        MutableComponent var10000;
        switch (this.ordinal()) {
            case 0 -> var10000 = Component.translatable("createaddition.tooltip.energy.push");
            case 1 -> var10000 = Component.translatable("createaddition.tooltip.energy.pull");
            case 2 -> var10000 = Component.translatable("createaddition.tooltip.energy.none");
            case 3 -> var10000 = Component.translatable("createaddition.tooltip.energy.passive");
            default -> throw new MatchException((String)null, (Throwable)null);
        }

        return var10000;
    }

    public boolean isActive() {
        return this == Push || this == Pull;
    }

    public static LightMode test(Level level, BlockPos pos, Direction face) {
        BlockEntity be = level.getBlockEntity(pos);
        return None;
    }
}
