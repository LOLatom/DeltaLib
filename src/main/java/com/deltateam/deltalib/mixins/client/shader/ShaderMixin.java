package com.deltateam.deltalib.mixins.client.shader;

import com.deltateam.deltalib.accessors.ShaderAccessor;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostPass;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(PostPass.class)
public class ShaderMixin implements ShaderAccessor {
	@Mutable
	@Shadow
	@Final
	public RenderTarget inTarget;
	@Mutable
	@Shadow
	@Final
	public RenderTarget outTarget;
	@Unique
	public RenderTarget renderTarget;
	@Unique
	public RenderTarget sourceBuffer;
	
	@Shadow
	private Matrix4f shaderOrthoMatrix;
	
	@Shadow
	@Final
	private EffectInstance effect;
	@Unique
	HashMap<String, RenderTarget> auxTargs = new HashMap<>();
	HashMap<String, RenderTarget> depthTargs = new HashMap<>();
	
	@Override
	public void addAuxTarget(String name, RenderTarget target) {
		auxTargs.put(name, target);
	}
	
	@Override
	public void addDepthTarget(String name, RenderTarget target) {
		depthTargs.put(name, target);
	}
	
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
	
	@Inject(at = @At("HEAD"), method = "process")
	public void preProcess(float object, CallbackInfo ci) {
		for (String s : auxTargs.keySet()) {
			RenderTarget target = auxTargs.get(s);
			this.effect.setSampler(s, target::getColorTextureId);
			this.effect.safeGetUniform("AuxSize" + s).set(target.width, target.height);
		}
		for (String s : depthTargs.keySet()) {
			RenderTarget target = depthTargs.get(s);
			this.effect.setSampler(s, target::getDepthTextureId);
			this.effect.safeGetUniform("AuxSize" + s).set(target.width, target.height);
		}
	}
}