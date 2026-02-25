package dev.nakou.createelectriccity.content.common;

import com.mrh0.createaddition.blocks.connector.ConnectorType;
import com.mrh0.createaddition.config.CommonConfig;
import com.mrh0.createaddition.debug.IDebugDrawer;
import com.mrh0.createaddition.energy.IEnergyProvider;
import com.mrh0.createaddition.energy.IWireNode;
import com.mrh0.createaddition.energy.LocalNode;
import com.mrh0.createaddition.energy.NodeRotation;
import com.mrh0.createaddition.energy.WireType;
import com.mrh0.createaddition.energy.network.EnergyNetwork;
import com.mrh0.createaddition.index.CALang;
import com.mrh0.createaddition.network.EnergyNetworkPacketPayload;
import com.mrh0.createaddition.network.IObserveBlockEntity;
import com.mrh0.createaddition.network.ObservePacketPayload;
import com.mrh0.createaddition.util.Util;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dev.nakou.createelectriccity.content.smalllightbulb.SmallLightBulbBlock;
import dev.nakou.createelectriccity.sound.CECSoundScapes;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.outliner.Outliner;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractLightBlockEntity extends SmartBlockEntity implements IWireNode, IObserveBlockEntity, IHaveGoggleInformation, IDebugDrawer {

    private final static float OFFSET_HEIGHT = 1f;
    private final static float OFFSET_DEVIDER = 2f;
    public final static Vec3 OFFSET_DOWN = new Vec3(0f, -OFFSET_HEIGHT/OFFSET_DEVIDER, 0f);
    public final static Vec3 OFFSET_UP = new Vec3(0f, OFFSET_HEIGHT/OFFSET_DEVIDER, 0f);
    public final static Vec3 OFFSET_NORTH = new Vec3(0f, 0f, -OFFSET_HEIGHT/OFFSET_DEVIDER);
    public final static Vec3 OFFSET_WEST = new Vec3(-OFFSET_HEIGHT/OFFSET_DEVIDER, 0f, 0f);
    public final static Vec3 OFFSET_SOUTH = new Vec3(0f, 0f, OFFSET_HEIGHT/OFFSET_DEVIDER);
    public final static Vec3 OFFSET_EAST = new Vec3(OFFSET_HEIGHT/OFFSET_DEVIDER, 0f, 0f);


    private final Set<LocalNode> wireCache = new HashSet<>();
    private final LocalNode[] localNodes = new LocalNode[this.getNodeCount()];
    private final IWireNode[] nodeCache = new IWireNode[this.getNodeCount()];
    private EnergyNetwork network;
    //private int demand = 0;
    private boolean wasContraption = false;
    private boolean firstTick = true;
    protected InterfaceEnergyHandler internal = new InterfaceEnergyHandler();
    protected BlockCapabilityCache<IEnergyStorage, Direction> external;
    boolean externalStorageInvalid = false;
    public LerpedFloat glow = LerpedFloat.linear();
    public DyeColor color = DyeColor.WHITE;

    private int posTimeOffset = 0;

    public AbstractLightBlockEntity(BlockEntityType<?> blockEntityTypeIn, BlockPos pos, BlockState state) {
        super(blockEntityTypeIn, pos, state);
        posTimeOffset = 10 + (Math.abs(pos.getX()*31 + pos.getY()*45 + pos.getZ()*33) % 7) * 3;
    }

    public abstract int getConsumption();

    public abstract int getLightProduction();

    public int getCapacity() {
        return Math.min(this.getMaxIn(), this.getMaxOut());
    }

    public @Nullable IWireNode getWireNode(int index) {
        return IWireNode.getWireNodeFrom(index, this, this.localNodes, this.nodeCache, this.level);
    }

    public void setColor(DyeColor color) {
        if(color==DyeColor.BLACK||color == DyeColor.LIGHT_GRAY|| color == DyeColor.GRAY)
            return;

        this.color = color;
        notifyUpdate();
    }

    @Override
    public ConnectorType getConnectorType() { // this is the last remaining connection to the "connector" object as the IWireNode needs the info in that format.
        return ConnectorType.Small; // for now all lights are small except maybe some heavy ones
    }

    public @Nullable LocalNode getLocalNode(int index) {
        return this.localNodes[index];
    }

    public void setNode(int index, int other, BlockPos pos, WireType type) {
        this.localNodes[index] = new LocalNode(this, index, other, type, pos);
        this.notifyUpdate();
        if (this.network != null) {
            this.network.invalidate();
        }

    }

    public void removeNode(int index, boolean dropWire) {
        LocalNode old = this.localNodes[index];
        this.localNodes[index] = null;
        this.nodeCache[index] = null;
        this.invalidateNodeCache();
        this.notifyUpdate();
        if (this.network != null) {
            this.network.invalidate();
        }

        if (dropWire && old != null) {
            this.wireCache.add(old);
        }

    }

    public BlockPos getPos() {
        return this.getBlockPos();
    }

    public void setNetwork(int node, EnergyNetwork network) {
        this.network = network;
    }

    public EnergyNetwork getNetwork(int node) {
        return this.network;
    }

    public int getMaxIn() {
        return dev.nakou.createelectriccity.config.CommonConfig.LIGHTS_MAX_INPUT.get();
    }

    public int getMaxOut() {
        return dev.nakou.createelectriccity.config.CommonConfig.LIGHTS_MAX_OUTPUT.get();
    }

    @Override
    public int getNodeCount() {
        return 4;
    }

    @Override
    public Vec3 getNodeOffset(int node) {
        return switch (getBlockState().getValue(AbstractLightBlock.FACING)) {
            case DOWN -> OFFSET_DOWN;
            case UP -> OFFSET_UP;
            case NORTH -> OFFSET_NORTH;
            case WEST -> OFFSET_WEST;
            case SOUTH -> OFFSET_SOUTH;
            case EAST -> OFFSET_EAST;
        };
    }

    public int getMaxWireLength() {
        return dev.nakou.createelectriccity.config.CommonConfig.LIGHTS_MAX_LENGTH.get();
    }

    public void read(CompoundTag nbt, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(nbt, registries, clientPacket);
        if (!clientPacket && nbt.contains("node0")) {
            this.convertOldNbt(nbt);
            this.setChanged();
        }

        this.invalidateLocalNodes();
        this.invalidateNodeCache();
        ListTag nodes = nbt.getList("nodes", 10);
        nodes.forEach((tag) -> {
            LocalNode localNode = new LocalNode(this, (CompoundTag) tag);
            this.localNodes[localNode.getIndex()] = localNode;
        });
        if (nbt.contains("contraption") && !clientPacket) {
            this.wasContraption = nbt.getBoolean("contraption");
            NodeRotation rotation = (NodeRotation) this.getBlockState().getValue(NodeRotation.ROTATION);
            if (this.level == null) {
                return;
            }

            if (rotation != NodeRotation.NONE) {
                this.level.setBlock(this.getBlockPos(), (BlockState) this.getBlockState().setValue(NodeRotation.ROTATION, NodeRotation.NONE), 0);
            }

            for (LocalNode localNode : this.localNodes) {
                if (localNode != null) {
                    localNode.updateRelative(rotation);
                }
            }
        }

        if (!nodes.isEmpty() && this.network != null) {
            this.network.invalidate();
        }

    }

    public void write(CompoundTag nbt, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(nbt, registries, clientPacket);
        ListTag nodes = new ListTag();

        for (int i = 0; i < this.getNodeCount(); ++i) {
            LocalNode localNode = this.localNodes[i];
            if (localNode != null) {
                CompoundTag tag = new CompoundTag();
                localNode.write(tag);
                nodes.add(tag);
            }
        }

        nbt.put("nodes", nodes);
    }

    private void validateNodes() {
        boolean changed = this.validateLocalNodes(this.localNodes);
        this.notifyUpdate();
        if (changed) {
            this.invalidateNodeCache();
            if (this.network != null) {
                this.network.invalidate();
            }
        }

    }

    public void firstTick() {
        this.firstTick = false;
        if (this.level != null) {
            if (this.wasContraption && !this.level.isClientSide()) {
                this.wasContraption = false;
                this.validateNodes();
            }

            this.updateExternalEnergyStorage();
        }
    }

    private int tickToggleTimer = 0;

    protected void specialTick() {
        if(getLevel() == null) return;
        if(level.isClientSide()) return;
        EnergyNetwork network = getNetwork(0);
        if (network != null) network.demand(1);
        boolean hasEnergy = network != null && network.pull(getConsumption(), false) > 0;
        tickToggleTimer = tickToggleTimer + (hasEnergy ? 1 : -1);

        if (tickToggleTimer >= posTimeOffset) {
            tickToggleTimer = posTimeOffset;
            if (!getBlockState().getValue(SmallLightBulbBlock.POWERED))
                getLevel().setBlockAndUpdate(getBlockPos(), getBlockState()
                        .setValue(SmallLightBulbBlock.POWERED, true)
                        .setValue(SmallLightBulbBlock.LEVEL,getLightProduction()));
        }

        if (tickToggleTimer <= -posTimeOffset) {
            tickToggleTimer = -posTimeOffset;
            if (getBlockState().getValue(SmallLightBulbBlock.POWERED))
                getLevel().setBlockAndUpdate(getBlockPos(), getBlockState()
                        .setValue(SmallLightBulbBlock.POWERED, false)
                        .setValue(SmallLightBulbBlock.LEVEL, 0));
        }
    }

    public void tick() {
        if (this.firstTick) {
            this.firstTick();
        }
        if (level.isClientSide()) {
            CatnipServices.PLATFORM.executeOnClientOnly(() -> this::tickAudio);
            glow.chase(getConsumption() * 25, 0.4, LerpedFloat.Chaser.EXP);
            glow.tickChaser();
        }
        if (this.level != null) {
            if (this.level.isLoaded(this.getBlockPos())) {
                if (!this.wireCache.isEmpty() && !this.isRemoved()) {
                    this.handleWireCache(this.level, this.wireCache);
                }

                this.specialTick();
                if (this.getMode() != LightMode.None) {
                    super.tick();
                    if (this.level != null) {
                        if (!this.level.isClientSide()) {
                            if (this.awakeNetwork(this.level)) {
                                this.notifyUpdate();
                            }

                            this.networkTick(this.network);
                            if (this.externalStorageInvalid) {
                                this.updateExternalEnergyStorage();
                            }

                        }
                    }
                }
            }
        }
    }

    private void networkTick(EnergyNetwork network) {
        LightMode mode = this.getMode();
        if (this.level != null) {
            if (!this.level.isClientSide()) {
                IEnergyStorage otherStorage = (IEnergyStorage) this.external.getCapability();
                if (otherStorage != null) {
                    if (mode == LightMode.Push) {
                        int pulled = network.pull(network.demand(otherStorage.receiveEnergy(this.getMaxOut(), true)));
                        otherStorage.receiveEnergy(pulled, false);
                    }

                    if (mode == LightMode.Pull) {
                        int toPush = otherStorage.extractEnergy(network.push(this.getMaxIn(), true), false);
                        network.push(toPush);
                    }

                }
            }
        }
    }

    public void remove() {
        if (this.level != null) {
            if (!this.level.isClientSide()) {
                for (int i = 0; i < this.getNodeCount(); ++i) {
                    LocalNode localNode = this.getLocalNode(i);
                    if (localNode != null) {
                        IWireNode otherNode = this.getWireNode(i);
                        if (otherNode != null) {
                            int ourNode = localNode.getOtherIndex();
                            if (localNode.isInvalid()) {
                                otherNode.removeNode(ourNode);
                            } else {
                                otherNode.removeNode(ourNode, true);
                            }
                        }
                    }
                }

                this.invalidateNodeCache();
                if (this.network != null) {
                    this.network.invalidate();
                }

            }
        }
    }

    public void invalidateLocalNodes() {
        for (int i = 0; i < this.getNodeCount(); ++i) {
            this.localNodes[i] = null;
        }

    }

    public void invalidateNodeCache() {
        for (int i = 0; i < this.getNodeCount(); ++i) {
            this.nodeCache[i] = null;
        }

    }

    public LightMode getMode() {
        return (LightMode) this.getBlockState().getValue(AbstractLightBlock.MODE);
    }

    public void onObserved(ServerPlayer player, ObservePacketPayload pack) {
        if (this.isNetworkValid(0)) {
            EnergyNetworkPacketPayload.send(this.worldPosition, this.getNetwork(0).getPulled(), this.getNetwork(0).getPushed(), player);
        }

    }

    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        ObservePacketPayload.send(this.worldPosition, 0);
        String spacing = " ";
        CALang.builder().add(Component.translatable("create.tooltip.createelectriccity.lights.info").withStyle(ChatFormatting.WHITE)).forGoggles(tooltip);
        CALang.builder().add(Component.translatable("createelectriccity.tooltip.energy.currentStatus").withStyle(ChatFormatting.GRAY)).forGoggles(tooltip);
        if(this.getBlockState().getValue(AbstractLightBlock.POWERED)){
            CALang.builder().add(Component.translatable("create.tooltip.createelectriccity.lights.on").withStyle(ChatFormatting.AQUA)).forGoggles(tooltip);
            CALang.builder().add(Component.translatable("create.tooltip.createelectriccity.energy_usage").withStyle(ChatFormatting.GRAY)).forGoggles(tooltip);
            CALang.builder().add(Component.literal(" ").append(Util.format(getConsumption())).append("⚡/t").withStyle(ChatFormatting.AQUA)).forGoggles(tooltip);
        } else {
            CALang.builder().add(Component.translatable("create.tooltip.createelectriccity.lights.off").withStyle(ChatFormatting.RED)).forGoggles(tooltip);
            CALang.builder().add(Component.translatable("create.tooltip.createelectriccity.energy_usage").withStyle(ChatFormatting.GRAY)).forGoggles(tooltip);
            CALang.builder().add(Component.literal(" 0⚡/t").withStyle(ChatFormatting.RED)).forGoggles(tooltip);
        }
        return IHaveGoggleInformation.super.addToGoggleTooltip(tooltip, isPlayerSneaking);
    }

    public void updateExternalEnergyStorage() {
        if (this.level != null) {
            if (this.level instanceof ServerLevel) {
                if (this.level.isLoaded(this.getBlockPos())) {
                    Direction side = (Direction) this.getBlockState().getValue(AbstractLightBlock.FACING);
                    if (this.level.isLoaded(this.getBlockPos().relative(side))) {
                        this.externalStorageInvalid = false;
                        this.external = BlockCapabilityCache.create(Capabilities.EnergyStorage.BLOCK, (ServerLevel) this.level, this.getPos().relative(side), side.getOpposite(), () -> !this.isRemoved(), () -> this.externalStorageInvalid = true);
                    }
                }
            }
        }
    }

    public void drawDebug() {
        if (this.level != null) {
            for (int i = 0; i < this.getNodeCount(); ++i) {
                LocalNode localNode = this.localNodes[i];
                if (localNode != null) {
                    BlockPos pos = localNode.getPos();
                    BlockState state = this.level.getBlockState(pos);
                    VoxelShape shape = state.getBlockSupportShape(this.level, pos);
                    int color;
                    if (i == 0) {
                        color = 16711680;
                    } else if (i == 1) {
                        color = 65280;
                    } else if (i == 2) {
                        color = 255;
                    } else {
                        color = 16777215;
                    }

                    if (!(this.level.getBlockEntity(pos) instanceof IWireNode)) {
                        shape = Shapes.block();
                        color = 16711935;
                    }

                    Outliner.getInstance().chaseAABB("ca_nodes_" + i, shape.bounds().move(pos)).lineWidth(0.0625F).colored(color);
                }
            }

            BlockPos pos = this.worldPosition.relative((Direction) this.getBlockState().getValue(AbstractLightBlock.FACING));
            IEnergyStorage cap = (IEnergyStorage) this.level.getCapability(Capabilities.EnergyStorage.BLOCK, pos, ((Direction) this.getBlockState().getValue(AbstractLightBlock.FACING)).getOpposite());
            if (cap != null) {
                VoxelShape shape = this.level.getBlockState(pos).getBlockSupportShape(this.level, pos);
                Outliner.getInstance().chaseAABB("ca_output", shape.bounds().move(pos)).lineWidth(0.0625F).colored(5987327);
            }
        }
    }

    public void tickAudio() {
        float pitch = 0.75f;
        CECSoundScapes.play(CECSoundScapes.AmbienceGroup.LIGHT, worldPosition, pitch);
    }


    protected class InterfaceEnergyHandler implements IEnergyStorage {
        public InterfaceEnergyHandler() {
        }

        public int receiveEnergy(int maxReceive, boolean simulate) {
            if (!(Boolean) CommonConfig.CONNECTOR_ALLOW_PASSIVE_IO.get()) {
                return 0;
            } else if (AbstractLightBlockEntity.this.getMode() != LightMode.Pull) {
                return 0;
            } else if (AbstractLightBlockEntity.this.network == null) {
                return 0;
            } else {
                maxReceive = Math.min(maxReceive, AbstractLightBlockEntity.this.getMaxIn());
                return AbstractLightBlockEntity.this.network.push(maxReceive, simulate);
            }
        }

        public int extractEnergy(int maxExtract, boolean simulate) {
            if (!(Boolean) CommonConfig.CONNECTOR_ALLOW_PASSIVE_IO.get()) {
                return 0;
            } else if (AbstractLightBlockEntity.this.getMode() != LightMode.Push) {
                return 0;
            } else if (AbstractLightBlockEntity.this.network == null) {
                return 0;
            } else {
                maxExtract = Math.min(maxExtract, AbstractLightBlockEntity.this.getMaxOut());
                return AbstractLightBlockEntity.this.network.pull(maxExtract, simulate);
            }
        }

        public int getEnergyStored() {
            return AbstractLightBlockEntity.this.network == null ? 0 : Math.min(AbstractLightBlockEntity.this.getCapacity(), AbstractLightBlockEntity.this.network.getBuff());
        }

        public int getMaxEnergyStored() {
            return AbstractLightBlockEntity.this.getCapacity();
        }

        public boolean canExtract() {
            return true;
        }

        public boolean canReceive() {
            return true;
        }
    }
}