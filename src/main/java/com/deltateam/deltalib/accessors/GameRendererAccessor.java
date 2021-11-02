package com.deltateam.deltalib.accessors;

import net.minecraft.client.shader.Shader;
import net.minecraft.util.ResourceLocation;

public interface GameRendererAccessor {
	Shader addPass(ResourceLocation passId, ResourceLocation shader);
	void removePass(ResourceLocation passId);
	boolean isShaderEnabled(ResourceLocation passId);
	Shader getPass(ResourceLocation passId);
}
