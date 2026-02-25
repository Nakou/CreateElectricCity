package dev.nakou.createelectriccity.sound;

import com.simibubi.create.infrastructure.config.AllConfigs;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

import static dev.nakou.createelectriccity.sound.CECSoundScapes.getGroupFromPitch;

public class CECSoundScape {
    List<CECContinuousSound> continuous;
    //	List<RepeatingSound> repeating;
    private final float pitch;
    private final CECSoundScapes.AmbienceGroup group;
    private Vec3 meanPos;
    private final CECSoundScapes.PitchGroup pitchGroup;

    public CECSoundScape(float pitch, CECSoundScapes.AmbienceGroup group) {
        this.pitchGroup = getGroupFromPitch(pitch);
        this.pitch = pitch;
        this.group = group;
        continuous = new ArrayList<>();
    }

    public CECSoundScape continuous(SoundEvent sound, float relativeVolume, float relativePitch) {
        return add(new CECContinuousSound(sound, this, pitch * relativePitch, relativeVolume));
    }

//	public CECSoundScape repeating(SoundEvent sound, float relativeVolume, float relativePitch, int delay) {
//		return add(new RepeatingSound(sound, this, pitch * relativePitch, relativeVolume, delay));
//	}

    public CECSoundScape add(CECContinuousSound continuousSound) {
        continuous.add(continuousSound);
        return this;
    }

//	public CECSoundScape add(RepeatingSound repeatingSound) {
//		repeating.add(repeatingSound);
//		return this;
//	}

    public void play() {
        continuous.forEach(Minecraft.getInstance()
                .getSoundManager()::play);
    }

    public void tick() {
        if (AnimationTickHolder.getTicks() % CECSoundScapes.UPDATE_INTERVAL == 0)
            meanPos = null;
//		repeating.forEach(RepeatingSound::tick);
    }

    public void remove() {
        continuous.forEach(CECContinuousSound::remove);
    }

    public Vec3 getMeanPos() {
        return meanPos == null ? meanPos = determineMeanPos() : meanPos;
    }

    private Vec3 determineMeanPos() {
        meanPos = Vec3.ZERO;
        int amount = 0;
        for (BlockPos blockPos : CECSoundScapes.getAllLocations(group, pitchGroup)) {
            meanPos = meanPos.add(VecHelper.getCenterOf(blockPos));
            amount++;
        }
        if (amount == 0)
            return meanPos;
        return meanPos.scale(1f / amount);
    }

    public float getVolume() {
        Entity renderViewEntity = Minecraft.getInstance().cameraEntity;
        float distanceMultiplier = 0;
        if (renderViewEntity != null) {
            double distanceTo = renderViewEntity.position()
                    .distanceTo(getMeanPos());
            distanceMultiplier = (float) Mth.lerp(distanceTo / CECSoundScapes.MAX_AMBIENT_SOURCE_DISTANCE, 2, 0);
        }
        int soundCount = CECSoundScapes.getSoundCount(group, pitchGroup);
        float max = AllConfigs.client().ambientVolumeCap.getF();
        float argMax = (float) CECSoundScapes.SOUND_VOLUME_ARG_MAX;
        return Mth.clamp(soundCount / (argMax * 10f), 0.025f, max) * distanceMultiplier;
    }
}
