package com.deltateam.deltalib.accessors;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.math.Matrix4f;

public interface ShaderAccessor {
	void setFramebufferOut(RenderTarget framebuffer);
	void setFramebufferIn(RenderTarget framebuffer);
	Matrix4f getMatrix();
}
