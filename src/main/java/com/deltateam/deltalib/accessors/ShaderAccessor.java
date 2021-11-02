package com.deltateam.deltalib.accessors;

import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.math.vector.Matrix4f;

public interface ShaderAccessor {
	void setFramebufferOut(Framebuffer framebuffer);
	void setFramebufferIn(Framebuffer framebuffer);
	Matrix4f getMatrix();
}
