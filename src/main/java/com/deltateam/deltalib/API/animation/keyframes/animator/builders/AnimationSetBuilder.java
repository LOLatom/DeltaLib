package com.deltateam.deltalib.API.animation.keyframes.animator.builders;

import com.deltateam.deltalib.API.animation.keyframes.animator.Animation;
import com.deltateam.deltalib.API.animation.keyframes.animator.AnimationSet;

// TODO: do this more properly?
public class AnimationSetBuilder {
	AnimationSet set = new AnimationSet();
	
	public AnimationSetBuilder addAnimation(String key, Animation animation) {
		set.addAnimation(key, animation);
		return this;
	}
	
	public AnimationSet build() {
		return set;
	}
}
