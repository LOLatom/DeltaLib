package com.deltateam.deltalib.mixins.client.shader;

import com.deltateam.deltalib.accessors.ShaderAccessor;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.util.math.vector.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Shader.class)
public class ShaderMixin implements ShaderAccessor {
	@Mutable @Shadow @Final public Framebuffer framebufferIn;
	@Mutable @Shadow @Final public Framebuffer framebufferOut;
	
	@Shadow private Matrix4f projectionMatrix;
	
	@Override
	public void setFramebufferOut(Framebuffer framebuffer) {
		framebufferOut = framebuffer;
	}
	
	@Override
	public void setFramebufferIn(Framebuffer framebuffer) {
		framebufferIn = framebuffer;
	}
	
	@Override
	public Matrix4f getMatrix() {
		return projectionMatrix;
	}
	
	public void uniform(float[] floats) {
	}
}