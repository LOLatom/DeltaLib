package com.deltateam.deltalib.accessors;

import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public interface ShaderGroupAccessor {
	void addPass(ResourceLocation passId, PostPass shader);
	PostPass removePass(ResourceLocation passId);
	void removePass(PostPass pass);
	void removeLast();
	void setShaderOrthoMatrix(Matrix4f matrix);
	Map<ResourceLocation, PostPass> getPasses();
	void clearPasses();
	PostPass getPass(ResourceLocation passId);
}
