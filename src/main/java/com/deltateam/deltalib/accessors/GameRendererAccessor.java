package com.deltateam.deltalib.accessors;

import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;

public interface GameRendererAccessor {
	PostPass addPass(ResourceLocation passId, ResourceLocation shader);
	void removePass(ResourceLocation passId);
	boolean isShaderEnabled(ResourceLocation passId);
	PostPass getPass(ResourceLocation passId);
}
