package com.deltateam.deltalib.API.rendering.overlays;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class OverlayHelper {
	private static HashMap<UUID, List<ResourceLocation>> overlayMap = new HashMap<>();
	
	public static void addOverlay(UUID entity, ResourceLocation texture) {
		if (!overlayMap.containsKey(entity)) overlayMap.put(entity, new ArrayList<>());
		List<ResourceLocation> locations = overlayMap.get(entity);
		if (!locations.contains(texture)) locations.add(texture);
	}
	
	public static void removeOverlay(UUID entity, ResourceLocation texture) {
		if (!overlayMap.containsKey(entity)) overlayMap.put(entity, new ArrayList<>());
		List<ResourceLocation> locations = overlayMap.get(entity);
		if (locations.contains(texture)) locations.remove(texture);
	}
	
	public static boolean hasOverlayForEntity(UUID entity) {
		return overlayMap.containsKey(entity);
	}
	
	public static List<ResourceLocation> getForEntity(UUID entity) {
		return overlayMap.get(entity);
	}
}
