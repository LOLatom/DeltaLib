package com.deltateam.deltalib.API.animation.keyframes.animator;

import com.deltateam.deltalib.API.animation.keyframes.keyframes.Keyframe;
import com.deltateam.deltalib.util.animation.KeyframeGroup;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.geom.ModelPart;

import java.util.HashSet;
import java.util.Set;

public class Animation {
	Set<Pair<ModelPart, KeyframeGroup>> partAnimations = new HashSet<>();
	
	public void addPartAnimation(ModelPart part, KeyframeGroup animation) {
		partAnimations.add(Pair.of(part, animation));
	}
}
