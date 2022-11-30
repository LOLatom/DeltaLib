package com.deltateam.deltalib.util.animation;

import com.deltateam.deltalib.API.animation.keyframes.keyframes.Keyframe;

public class KeyframeGroup {
	public final Keyframe position;
	public final Keyframe rotation;
//	public final Keyframe scale;
	
//	public KeyframeGroup(Keyframe position, Keyframe rotation, Keyframe scale) {
	public KeyframeGroup(Keyframe position, Keyframe rotation) {
		this.position = position;
		this.rotation = rotation;
//		this.scale = scale;
	}
}
