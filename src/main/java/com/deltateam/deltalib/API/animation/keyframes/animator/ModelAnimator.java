package com.deltateam.deltalib.API.animation.keyframes.animator;

import com.deltateam.deltalib.API.animation.keyframes.keyframes.BasicKeyframe;
import com.deltateam.deltalib.API.animation.keyframes.EasingDirection;
import com.deltateam.deltalib.API.animation.keyframes.EasingTypes;
import com.deltateam.deltalib.API.animation.keyframes.keyframes.Keyframe;
import com.deltateam.deltalib.util.animation.KeyframeGroup;
import com.deltateam.deltalib.util.animation.MutableKeyframeGroup;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;

// TODO: generalize
public class ModelAnimator<T, V> {
	HashMap<ModelPart, PartAnimator> partAnimators = new HashMap<>();
	Entity entity;
	
	public ModelAnimator(Entity entity) {
		this.entity = entity;
	}
	
	public void addPartAnimator(ModelPart part, PartAnimator animator) {
		partAnimators.put(part, animator);
	}
	
	public void animatePart(ModelPart part, KeyframeGroup start) {
		PartAnimator animator = partAnimators.get(part);
		Vec3 initialPos = new Vec3(part.x, part.y, part.z).subtract(animator.defaultPosition.x(), animator.defaultPosition.y(), animator.defaultPosition.z());
		Vec3 initialRot = new Vec3(part.xRot, part.yRot, part.zRot);
		animator.lastPos = initialPos;
		animator.lastRot = initialRot;
		animator.current = new MutableKeyframeGroup(start);
		animator.play(entity);
	}
	
//	public void animatePart(int startupTime, EasingTypes easing, EasingDirection direction, ModelPart part, KeyframeGroup start) {
//		PartAnimator animator = partAnimators.get(part);
//		Vec3 initialPos = new Vec3(part.x, part.y, part.z).subtract(animator.defaultPosition.x(), animator.defaultPosition.y(), animator.defaultPosition.z());
//		// TODO: figure out how to essentially modulo this to be in bounds 0-2PI, except without actually using modulo, as that doesn't work how I need it to work when in negatives
//		Vec3 initialRot = new Vec3(part.xRot, part.yRot, part.zRot);
//		if (startupTime != 0) {
//			Vec3 firstFramePos = start.currentPos(entity, initialPos, new Vec3(animator.defaultPosition), 1);
//			// TODO: easing
//			start = new BasicKeyframe(null, start, firstFramePos, firstFrameRot, startupTime);
//		}
//		animator.lastPos = initialPos;
//		animator.lastRot = initialRot;
//		// TODO: decide if this is right or not?
//		// thought: if an animation eases in from non-keyframe based animation, the first keyframe should define the target position
//		animator.current = start.nextframe;
//		animator.play(entity);
//	}
	
	public void startAnimation(Animation animation) {
		for (Pair<ModelPart, KeyframeGroup> partAnimation : animation.partAnimations) {
			animatePart(partAnimation.getFirst(), partAnimation.getSecond());
		}
	}
	
//	public void startAnimation(int startupTime, EasingTypes easing, EasingDirection direction, Animation animation) {
//		for (Pair<ModelPart, KeyframeGroup> partAnimation : animation.partAnimations) {
//			animatePart(startupTime, easing, direction, partAnimation.getFirst(), partAnimation.getSecond());
//		}
//	}
	
	public void resetAnimation() {
		for (PartAnimator value : partAnimators.values())
			value.resetAnimation();
	}
	
	public <T extends LivingEntity> void tick(T entity, float frameTime) {
		for (PartAnimator value : partAnimators.values()) {
			value.tick(entity, frameTime);
		}
	}
	
	// TODO: smooth out of an animation
	
	public boolean hasPart(ModelPart rightArm) {
		return partAnimators.containsKey(rightArm);
	}
}
