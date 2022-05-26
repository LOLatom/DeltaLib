package com.deltateam.deltalib.accessors;

import net.minecraft.client.shader.Shader;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

import java.util.HashMap;

public interface ShaderGroupAccessor {
	void addPass(ResourceLocation passId, Shader shader);
	Shader removePass(ResourceLocation passId);
	void removePass(Shader pass);
	void removeLast();
	void setShaderOrthoMatrix(Matrix4f matrix);
	HashMap<ResourceLocation, Shader> getPasses();
	void clearPasses();
	Shader getPass(ResourceLocation passId);
}
