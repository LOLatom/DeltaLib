package com.deltateam.deltalib.util.animation;

import com.deltateam.deltalib.API.animation.keyframes.keyframes.Keyframe;
import net.minecraft.world.phys.Vec3;

public class AnimationTickData {
	public Vec3 current;
	public Vec3 last;
	public Keyframe frame;
	public int previousTick;
	public int keyframeTick;
}
