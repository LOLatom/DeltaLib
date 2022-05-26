package com.deltateam.deltalib.mixins.client.shader;

import com.deltateam.deltalib.accessors.ShaderAccessor;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.PostPass;
import org.spongepowered.asm.mixin.*;

@Mixin(PostPass.class)
public class ShaderMixin implements ShaderAccessor {
	@Mutable @Shadow @Final public RenderTarget inTarget;
	@Mutable @Shadow @Final public RenderTarget outTarget;
	@Unique public RenderTarget renderTarget;
	@Unique public RenderTarget sourceBuffer;
	
	@Shadow private Matrix4f shaderOrthoMatrix;
	
	@Override
	public void setFramebufferOut(RenderTarget framebuffer) {
		outTarget = framebuffer;
	}
	
	@Override
	public void setFramebufferIn(RenderTarget framebuffer) {
		inTarget = framebuffer;
	}
	
	@Override
	public void setTargetBuffer(RenderTarget framebuffer) {
		this.renderTarget = framebuffer;
	}
	
	@Override
	public RenderTarget getTargetBuffer() {
		return renderTarget;
	}
	
	@Override
	public void setSourceBuffer(RenderTarget framebuffer) {
		this.sourceBuffer = framebuffer;
	}
	
	@Override
	public RenderTarget getSourceBuffer() {
		return sourceBuffer;
	}
	
	@Override
	public Matrix4f getMatrix() {
		return shaderOrthoMatrix;
	}
}