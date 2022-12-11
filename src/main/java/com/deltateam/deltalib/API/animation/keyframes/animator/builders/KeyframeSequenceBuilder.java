package com.deltateam.deltalib.API.animation.keyframes.animator.builders;

import com.deltateam.deltalib.API.animation.keyframes.keyframes.BasicKeyframe;
import com.deltateam.deltalib.API.animation.keyframes.keyframes.Keyframe;
import com.deltateam.deltalib.util.animation.KeyframeGroup;
import com.deltateam.deltalib.util.animation.MutableKeyframeGroup;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.world.phys.Vec3;

import java.util.SortedSet;
import java.util.TreeSet;

// TODO: do this more properly?
public class KeyframeSequenceBuilder {
	Int2ObjectArrayMap<Keyframe> positions = new Int2ObjectArrayMap<>();
	Int2ObjectArrayMap<Keyframe> rotations = new Int2ObjectArrayMap<>();
	Int2ObjectArrayMap<Keyframe> scales = new Int2ObjectArrayMap<>();
	
	boolean loopPosition;
	Integer loopPointPosition;
	
	public KeyframeSequenceBuilder enablePositionLoop() {
		loopRotation = true;
		return this;
	}
	
	public KeyframeSequenceBuilder loopPositionToHere() {
		SortedSet<Integer> sortedTimestamps = new TreeSet<>(positions.keySet());
		for (Integer sortedTimestamp : sortedTimestamps)
			loopPointRotation = sortedTimestamp;
		return this;
	}
	
	boolean loopRotation;
	Integer loopPointRotation;
	
	public KeyframeSequenceBuilder enableRotationLoop() {
		loopRotation = true;
		return this;
	}
	
	public KeyframeSequenceBuilder loopRotationToHere() {
		SortedSet<Integer> sortedTimestamps = new TreeSet<>(positions.keySet());
		for (Integer sortedTimestamp : sortedTimestamps)
			loopPointRotation = sortedTimestamp;
		return this;
	}
	
	public KeyframeSequenceBuilder addPosition(int timestamp, Vec3 position) {
		positions.put(timestamp, new BasicKeyframe(null, null, position, 0));
		return this;
	}
	
	public KeyframeSequenceBuilder addRotation(int timestamp, Vec3 rotation, boolean isDegrees) {
		if (isDegrees) {
			rotation = new Vec3(
					Math.toRadians(rotation.x),
					Math.toRadians(rotation.y),
					Math.toRadians(rotation.z)
			);
		}
		rotations.put(timestamp, new BasicKeyframe(null, null, rotation, 0));
		return this;
	}
	
	public KeyframeGroup build() {
		MutableKeyframeGroup output = new MutableKeyframeGroup();
		
		{
			SortedSet<Integer> sortedTimestamps = new TreeSet<>(positions.keySet());
			int previous = 0;
			Keyframe first = null;
			Keyframe current = null;
			Keyframe last = null;
			
			Keyframe loopTo = null;
			
			for (Integer sortedTimestamp : sortedTimestamps) {
				Keyframe keyframe = positions.get((int) sortedTimestamp);
				keyframe.keyframeDuration = sortedTimestamp - previous;
				keyframe.prevframe = current;
				if (first == null) first = current = keyframe;
				else current = current.nextframe = keyframe;
				
				// loop handling
				if (loopPosition) {
					if (loopTo == null) loopTo = first;
					if (sortedTimestamp.intValue() == loopPointPosition.intValue()) loopTo = current;
				}
				
				last = keyframe;
				previous = sortedTimestamp;
			}
			output.position = first;
			
			last.nextframe = loopTo;
		}
		
		{
			SortedSet<Integer> sortedTimestamps = new TreeSet<>(rotations.keySet());
			int previous = 0;
			Keyframe first = null;
			Keyframe current = null;
			Keyframe last = null;
			
			Keyframe loopTo = null;
			
			for (Integer sortedTimestamp : sortedTimestamps) {
				Keyframe keyframe = rotations.get((int) sortedTimestamp);
				keyframe.keyframeDuration = sortedTimestamp - previous;
				keyframe.prevframe = current;
				if (first == null) first = current = keyframe;
				else current = current.nextframe = keyframe;
				
				// loop handling
				if (loopRotation) {
					if (loopTo == null) loopTo = first;
					if (sortedTimestamp.intValue() == loopPointRotation.intValue()) loopTo = current;
				}
				
				last = keyframe;
				previous = sortedTimestamp;
			}
			output.rotation = first;
			
			last.nextframe = loopTo;
		}
		
		return output.toImmutable();
	}
}
