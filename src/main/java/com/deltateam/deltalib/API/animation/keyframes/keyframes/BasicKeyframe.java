package com.deltateam.deltalib.API.animation.keyframes.keyframes;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class BasicKeyframe extends Keyframe{

    private Vec3 target;
    
    public BasicKeyframe(Keyframe prev, Keyframe next, Vec3 targetPos, double duration) {
        super(prev, next, duration);
        this.target = targetPos;
    }

    public Vec3 getTarget() {
        return target;
    }

    @Override
    public Vec3 currentPos(Entity entity, Vec3 lastpos, Vec3 defaultPos, double progress) {
        return new Vec3(
                lastpos.x * (1 - progress) + target.x * progress,
                lastpos.y * (1 - progress) + target.y * progress,
                lastpos.z * (1 - progress) + target.z * progress
        );
    }
}
