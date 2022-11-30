package com.deltateam.deltalib.util.animation;

import com.deltateam.deltalib.API.animation.keyframes.keyframes.Keyframe;

public class MutableKeyframeGroup {
	public Keyframe position;
	public Keyframe rotation;
//	public Keyframe scale;
	
	public MutableKeyframeGroup() {
	}
	
	public MutableKeyframeGroup(KeyframeGroup source) {
		position = source.position;
		rotation = source.rotation;
	}
	
	public KeyframeGroup toImmutable() {
//		return new KeyframeGroup(position, rotation, scale);
		return new KeyframeGroup(position, rotation);
	}
}
