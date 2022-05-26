package com.deltateam.deltalib.API.rendering.shader;

import com.deltateam.deltalib.accessors.GameRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;

public class PostProcessingUtils {
	public static PostPass addPass(String passId, String name) {
		return addPass(new ResourceLocation(passId), new ResourceLocation(name));
	}
	
	public static PostPass addPass(String passId, ResourceLocation name) {
		return addPass(new ResourceLocation(passId), name);
	}
	
	public static PostPass addPass(ResourceLocation passId, String name) {
		return addPass(passId, new ResourceLocation(name));
	}
	
	public static PostPass addPass(ResourceLocation passId, ResourceLocation name) {
		return ((GameRendererAccessor) Minecraft.getInstance().gameRenderer).addPass(passId, name);
	}
	
	public static void removePass(ResourceLocation passId) {
		((GameRendererAccessor) Minecraft.getInstance().gameRenderer).removePass(passId);
	}
	
	public static PostPass getPass(ResourceLocation passId) {
		return ((GameRendererAccessor) Minecraft.getInstance().gameRenderer).getPass(passId);
	}
	
	public static boolean hasPass(ResourceLocation passId) {
		return ((GameRendererAccessor) Minecraft.getInstance().gameRenderer).isShaderEnabled(passId);
	}
}
