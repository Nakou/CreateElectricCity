package dev.nakou.createelectriccity.sound;

import com.simibubi.create.infrastructure.config.AllConfigs;
import dev.nakou.createelectriccity.CreateElectricCity;
import dev.nakou.createelectriccity.registry.CECSounds;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.data.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

import java.util.*;
import java.util.function.BiFunction;

public class CECSoundScapes {
    static final int MAX_AMBIENT_SOURCE_DISTANCE = 16;
    static final int UPDATE_INTERVAL = 5;
    static final int SOUND_VOLUME_ARG_MAX = 15;

    public enum AmbienceGroup {
        LIGHT(CECSoundScapes::light)
        ;

        private final BiFunction<Float, AmbienceGroup, CECSoundScape> factory;

        AmbienceGroup(BiFunction<Float, AmbienceGroup, CECSoundScape> factory) {
            this.factory = factory;
        }

        CECSoundScape instantiate(float pitch) {
            return factory.apply(pitch, this);
        }
    }

    private static CECSoundScape light(float pitch, AmbienceGroup group) {
        return new CECSoundScape(pitch, group).continuous(CECSounds.LIGHT.get(), 0.75f, 1f);
    }


    public enum PitchGroup {
        VERY_LOW, LOW, NORMAL, HIGH, VERY_HIGH
    }

    private static final Map<AmbienceGroup, Map<PitchGroup, Set<BlockPos>>> counter = new IdentityHashMap<>();
    private static final Map<Pair<AmbienceGroup, PitchGroup>, CECSoundScape> activeSounds = new HashMap<>();

    public static void play(AmbienceGroup group, BlockPos pos, float pitch) {
        if (!AllConfigs.client().enableAmbientSounds.get()) return;
        if(!CECSounds.LIGHT.isBound()) return;

        if (!outOfRange(pos)) addSound(group, pos, pitch);
    }

    public static void tick() {
        if (Minecraft.getInstance().level == null) return;
        activeSounds.values()
                .forEach(CECSoundScape::tick);

        if (AnimationTickHolder.getTicks() % UPDATE_INTERVAL != 0) return;

        boolean disable = false;
        try {
            disable = !AllConfigs.client().enableAmbientSounds.get();
        } catch (Exception error) {
            CreateElectricCity.LOGGER.error("Suppressed crash in CECSoundScapes");
        }
        for (Iterator<Map.Entry<Pair<AmbienceGroup, PitchGroup>, CECSoundScape>> iterator = activeSounds.entrySet()
                .iterator(); iterator.hasNext();) {

            Map.Entry<Pair<AmbienceGroup, PitchGroup>, CECSoundScape> entry = iterator.next();
            Pair<AmbienceGroup, PitchGroup> key = entry.getKey();
            CECSoundScape value = entry.getValue();

            if (disable || getSoundCount(key.getFirst(), key.getSecond()) == 0) {
                value.remove();
                iterator.remove();
            }
        }

        counter.values()
                .forEach(m -> m.values()
                        .forEach(Set::clear));
    }

    private static void addSound(AmbienceGroup group, BlockPos pos, float pitch) {
        PitchGroup groupFromPitch = getGroupFromPitch(pitch);
        Set<BlockPos> set = counter.computeIfAbsent(group, ag -> new IdentityHashMap<>())
                .computeIfAbsent(groupFromPitch, pg -> new HashSet<>());
        set.add(pos);

        Pair<AmbienceGroup, PitchGroup> pair = Pair.of(group, groupFromPitch);
        activeSounds.computeIfAbsent(pair, $ -> {
            CECSoundScape soundScape = group.instantiate(pitch);
            soundScape.play();
            return soundScape;
        });
    }

    public static void invalidateAll() {
        counter.clear();
        activeSounds.forEach(($, sound) -> sound.remove());
        activeSounds.clear();
    }

    protected static boolean outOfRange(BlockPos pos) {
        return !getCameraPos().closerThan(pos, MAX_AMBIENT_SOURCE_DISTANCE);
    }

    protected static BlockPos getCameraPos() {
        Entity renderViewEntity = Minecraft.getInstance().cameraEntity;
        if (renderViewEntity == null) return BlockPos.ZERO;
        return renderViewEntity.blockPosition();
    }

    public static int getSoundCount(AmbienceGroup group, PitchGroup pitchGroup) {
        return getAllLocations(group, pitchGroup).size();
    }

    public static Set<BlockPos> getAllLocations(AmbienceGroup group, PitchGroup pitchGroup) {
        return counter.getOrDefault(group, Collections.emptyMap())
                .getOrDefault(pitchGroup, Collections.emptySet());
    }

    public static PitchGroup getGroupFromPitch(float pitch) {
        if (pitch < .70) return PitchGroup.VERY_LOW;
        if (pitch < .90) return PitchGroup.LOW;
        if (pitch < 1.10) return PitchGroup.NORMAL;
        if (pitch < 1.30) return PitchGroup.HIGH;
        return PitchGroup.VERY_HIGH;
    }
}
