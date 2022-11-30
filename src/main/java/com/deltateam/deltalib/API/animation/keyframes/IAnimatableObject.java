package com.deltateam.deltalib.API.animation.keyframes;

import com.deltateam.deltalib.API.animation.keyframes.animator.ModelAnimator;

public interface IAnimatableObject<T, MODEL> {
	T getObject();
	ModelAnimator<T, MODEL> animator();
	void setup(MODEL model);
	boolean isSetup();
}
