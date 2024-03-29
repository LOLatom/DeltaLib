package com.deltateam.deltalib.accessors;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.math.Matrix4f;

public interface ShaderAccessor {
	void setFramebufferOut(RenderTarget framebuffer);
	void setFramebufferIn(RenderTarget framebuffer);
	void setTargetBuffer(RenderTarget framebuffer);
	RenderTarget getTargetBuffer();
	void setSourceBuffer(RenderTarget framebuffer);
	RenderTarget getSourceBuffer();
	void addAuxTarget(String  name, RenderTarget target);
	void addDepthTarget(String glowDepth, RenderTarget mainRenderTarget);
	Matrix4f getMatrix();
}
