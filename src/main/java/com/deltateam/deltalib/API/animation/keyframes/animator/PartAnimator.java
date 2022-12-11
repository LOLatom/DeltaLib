package com.deltateam.deltalib.API.animation.keyframes.animator;

import com.deltateam.deltalib.API.animation.keyframes.keyframes.Keyframe;
import com.deltateam.deltalib.util.animation.AnimationTickData;
import com.deltateam.deltalib.util.animation.MutableKeyframeGroup;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class PartAnimator {
	MutableKeyframeGroup current;
	Vec3 lastPos;
	Vec3 lastRot;
	int posTick;
	int rotTick;
	int lastTickPos;
	int lastTickRot;
	
	Vector3f defaultPosition;
	
	ModelPart part;
	
	public PartAnimator(MutableKeyframeGroup current, Vec3 lastPos, Vec3 lastRot, ModelPart part) {
		this.current = current;
		this.lastPos = lastPos;
		this.lastRot = lastRot;
		this.part = part;
		
		defaultPosition = new Vector3f(part.x, part.y, part.z);
	}
	
	private final AnimationTickData tickData = new AnimationTickData();
	
	boolean ticking = false;
	
	public void tick(Entity entity, double pct) {
		if (current == null) return;
		
		if (current.position == null && current.rotation == null) {
			current = null; // TODO: ease out
			return;
		}
		
		Vec3 defaultVec = new Vec3(defaultPosition.x(), defaultPosition.y(), defaultPosition.z());
		if (entity != null) {
			if (current.position != null) {
				tickData.frame = current.position;
				tickData.previousTick = lastTickPos;
				tickData.last = lastPos;
				tickData.current = new Vec3(part.x, part.y, part.z);
				tickKeyframe(posTick + Math.min(pct, 1), entity, defaultVec, tickData);
				part.x = (float) (tickData.current.x + defaultVec.x);
				part.y = (float) (tickData.current.y + defaultVec.y);
				part.z = (float) (tickData.current.z + defaultVec.z);
				lastPos = tickData.last;
				current.position = tickData.frame;
				posTick = tickData.keyframeTick;
			}
			
			if (current.rotation != null) {
				tickData.frame = current.rotation;
				tickData.previousTick = lastTickRot;
				tickData.last = lastRot;
				tickData.current = new Vec3(part.zRot, part.yRot, part.zRot);
				tickKeyframe(posTick + Math.min(pct, 1), entity, defaultVec, tickData);
				part.xRot = (float) tickData.current.x;
				part.yRot = (float) tickData.current.y;
				part.zRot = (float) tickData.current.z;
				lastRot = tickData.last;
				current.rotation = tickData.frame;
				rotTick = tickData.keyframeTick;
			}
			
			lastTickPos = lastTickRot = entity.tickCount;
		}
	}
	
	protected void tickKeyframe(double progress, Entity entity, Vec3 defaultVec, AnimationTickData tickData) {
		if (tickData.frame == null) return;
		if (tickData.frame.keyframeDuration == 0) {
			tickData.frame = tickData.frame.next(entity);
		}
		
		// TODO: animation flow equalization
		int lastTick = tickData.previousTick;
		Keyframe current = tickData.frame;
		progress /= current.keyframeDuration;
		if (progress > 1) progress = 1;
		tickData.current = current.currentPos(entity, tickData.last, defaultVec, progress);
		if (ticking) {
			tickData.keyframeTick += (entity.tickCount - lastTick);
			if (tickData.keyframeTick > current.keyframeDuration) {
				tickData.last = current.currentPos(entity, tickData.last, defaultVec, 1);
				tickData.current = tickData.last;
				tickData.frame = current.next(entity);
				tickData.keyframeTick = 0;
			}
		}
	}
	
	public void pause() {
		ticking = false;
	}
	
	public void play(Entity entity) {
		ticking = true;
		lastTickRot = lastTickPos = entity.tickCount;
	}
	
	public boolean isPaused() {
		return !ticking;
	}
	
	public void resetAnimation() {
		part.x = defaultPosition.x();
		part.y = defaultPosition.y();
		part.z = defaultPosition.z();
	}
}
