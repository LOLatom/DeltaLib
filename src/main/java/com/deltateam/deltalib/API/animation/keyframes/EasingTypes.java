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
    BOUNCE((val, start, end) -> {
        double amplitude = 0.3;
        double frequency = 5.0;

        double y = Math.abs(Math.sin(frequency * Math.PI * val));
        return new Vec3(
                start.x + (end.x - start.x) * val,
                start.y + (end.y - start.y) * y * amplitude,
                start.z + (end.z - start.z) * val
        );
    }),
    ELASTIC((val, start, end) -> {
        double amplitude = 0.1;
        double period = 0.4;

        double scaledVal = val * 2 - 1;
        double y = amplitude * Math.pow(2, -10 * scaledVal) * Math.sin((scaledVal - period / 4) * (2 * Math.PI) / period) + 1;

        return new Vec3(
                start.x + (end.x - start.x) * y,
                start.y + (end.y - start.y) * y,
                start.z + (end.z - start.z) * y
        );
    }),
    CUBIC_IN((val, start, end) -> {
        return new Vec3(
                start.x + (end.x - start.x) * (val * val * val),
                start.y + (end.y - start.y) * (val * val * val),
                start.z + (end.z - start.z) * (val * val * val)
        );
    }),
    CUBIC_OUT((val, start, end) -> {
        double v = (1 - val) * (1 - val) * (1 - val);
        return new Vec3(
                start.x + (end.x - start.x) * (1 - v),
                start.y + (end.y - start.y) * (1 - v),
                start.z + (end.z - start.z) * (1 - v)
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
