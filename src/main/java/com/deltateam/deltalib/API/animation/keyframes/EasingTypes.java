package com.deltateam.deltalib.API.animation.keyframes;

import com.deltateam.deltalib.API.TriFunction;
import net.minecraft.world.phys.Vec3;

public enum EasingTypes {
    EXPONENTIAL((val, start, end) -> {
        double pct = val * val;
        return new Vec3(
                start.x * pct + end.x * (1 - pct),
                start.y * pct + end.y * (1 - pct),
                start.z * pct + end.z * (1 - pct)
        );
    }),
    LINEAR((val, start, end) -> {
        return new Vec3(
                start.x * val + end.x * (1 - val),
                start.y * val + end.y * (1 - val),
                start.z * val + end.z * (1 - val)
        );
    });
    
    TriFunction<Double, Vec3, Vec3, Vec3> function;
    
    EasingTypes(TriFunction<Double, Vec3, Vec3, Vec3> function) {
        this.function = function;
    }
}
