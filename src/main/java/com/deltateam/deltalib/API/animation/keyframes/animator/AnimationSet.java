package com.deltateam.deltalib.API.animation.keyframes.animator;

import com.deltateam.deltalib.API.animation.keyframes.EasingDirection;
import com.deltateam.deltalib.API.animation.keyframes.EasingTypes;

import java.util.HashMap;

public class AnimationSet {
	HashMap<String, Animation> animationHashMap = new HashMap<>();
	
	public void startAnimation(String name, ModelAnimator animator) {
		animator.startAnimation(animationHashMap.get(name));
	}
	
//	public void startAnimation(String name, int startupTime, EasingTypes type, EasingDirection direction, ModelAnimator animator) {
//		animator.startAnimation(startupTime, type, direction, animationHashMap.get(name));
//	}
	
	public void addAnimation(String key, Animation animation) {
		animationHashMap.put(key, animation);
	}
}
