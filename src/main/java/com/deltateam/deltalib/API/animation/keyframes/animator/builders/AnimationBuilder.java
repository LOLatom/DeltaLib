package com.deltateam.deltalib.API.animation.keyframes.animator.builders;

import com.deltateam.deltalib.API.animation.keyframes.animator.Animation;
import com.deltateam.deltalib.API.animation.keyframes.keyframes.Keyframe;
import com.deltateam.deltalib.util.animation.KeyframeGroup;
import net.minecraft.client.model.geom.ModelPart;

// TODO: do this more properly?
public class AnimationBuilder {
	Animation animation = new Animation();
	
	public AnimationBuilder animatePart(ModelPart part, KeyframeGroup sequence) {
		animation.addPartAnimation(part, sequence);
		return this;
	}
	
	public Animation build() {
		return animation;
	}
}
