package com.deltateam.deltalib.mixins.client.shader;

import com.deltateam.deltalib.ClientVars;
import com.deltateam.deltalib.accessors.ShaderAccessor;
import com.deltateam.deltalib.accessors.ShaderGroupAccessor;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.math.Matrix4f;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@Mixin(PostChain.class)
public abstract class ShaderGroupMixin implements ShaderGroupAccessor {
	@Shadow
	@Final
	private List<PostPass> passes;
	
	@Shadow
	private Matrix4f shaderOrthoMatrix;
	@Shadow
	@Final
	private RenderTarget screenTarget;
	@Shadow
	@Final
	private Map<String, RenderTarget> customRenderTargets;
	
	@Shadow
	public abstract void addTempTarget(String name, int width, int height);
	
	@Shadow
	private int screenWidth;
	@Shadow
	private int screenHeight;
	
	@Shadow
	@Nullable
	protected abstract RenderTarget getRenderTarget(@org.jetbrains.annotations.Nullable String p_110050_);
	
	@Unique
	Map<ResourceLocation, PostPass> shaderUtilShaders = new Object2ObjectArrayMap<>();

//	@Inject(at = @At("HEAD"), method = "process")
//	public void preProcess(float tickDelta, CallbackInfo ci) {
//		RenderSystem.enableBlend();
//		RenderSystem.defaultBlendFunc();
//	}
	
	RenderTarget handDepthSampler = new TextureTarget(1, 1, true, Minecraft.ON_OSX);
	
	@Inject(at = @At("HEAD"), method = "process")
	public void copyDepth(float tickDelta, CallbackInfo ci) {
		if (handDepthSampler.height != screenTarget.height || handDepthSampler.width != screenTarget.width) {
			handDepthSampler.resize(screenTarget.width, screenTarget.height, Minecraft.ON_OSX);
		}
		handDepthSampler.copyDepthFrom(screenTarget);
	}
	
	@Inject(at = @At("TAIL"), method = "process")
	public void drawShaderUtilShaders(float tickDelta, CallbackInfo ci) {
		if (shaderUtilShaders.isEmpty()) return;
		
		if (customRenderTargets.isEmpty()) {
			// currently; if this ever gets called, the game will likely crash
			addTempTarget("deltalib:foolproofing", screenWidth, screenHeight);
		}
		RenderTarget buffer = null;
		for (String s : customRenderTargets.keySet()) {
			buffer = customRenderTargets.get(s);
			if (buffer != screenTarget) {
				break;
			}
		}
//		Framebuffer alternator = defaultSizedTargets.get(0);
//		Framebuffer src = alternator;
//		Framebuffer alt = (alternator == mainTarget) ? mainTarget : defaultSizedTargets.get(1);
		RenderTarget alternator = screenTarget;
		RenderTarget src = screenTarget;
		RenderTarget alt = buffer;
		
		for (PostPass shaderUtilShader : shaderUtilShaders.values()) {
			if (((ShaderAccessor) shaderUtilShader).getMatrix() != null) {
				((ShaderAccessor) shaderUtilShader).addDepthTarget("TerrainDepthSampler", ClientVars.terrainDepth);
				((ShaderAccessor) shaderUtilShader).addDepthTarget("HandDepthSampler", handDepthSampler);
				
				if (((ShaderAccessor) shaderUtilShader).getSourceBuffer() != null)
					((ShaderAccessor) shaderUtilShader).setFramebufferIn(((ShaderAccessor) shaderUtilShader).getSourceBuffer());
				else
					((ShaderAccessor) shaderUtilShader).setFramebufferIn(alternator);
				
				if (((ShaderAccessor) shaderUtilShader).getTargetBuffer() != null)
					((ShaderAccessor) shaderUtilShader).setFramebufferOut(((ShaderAccessor) shaderUtilShader).getTargetBuffer());
				else {
					if (alternator == src) alternator = alt;
					else alternator = src;
					
					((ShaderAccessor) shaderUtilShader).setFramebufferOut(alternator);
				}
				shaderUtilShader.process(tickDelta);
			}
		}
//		if (alternator == src) alternator = alt;
//		else alternator = src;
		if (alternator != src) {
			PostPass endShader = passes.get(passes.size() - 1);
			endShader.process(tickDelta);
		}
//		PostProcessShader endShader = passes.get(passes.size() - 1);
//		((PostProcessShaderAccessor) endShader).setOutput(endTarg);
	}
	
	@Inject(at = @At("TAIL"), method = "resize")
	public void updateDimensions(int targetsWidth, int targetsHeight, CallbackInfo ci) {
		if (shaderUtilShaders.isEmpty()) return;
		
		for (PostPass shaderUtilShader : shaderUtilShaders.values())
			shaderUtilShader.setOrthoMatrix(shaderOrthoMatrix);
	}
	
	@Inject(at = @At("TAIL"), method = "close")
	public void close(CallbackInfo ci) {
		if (shaderUtilShaders.isEmpty()) return;
		
		for (PostPass shaderUtilShader : shaderUtilShaders.values())
			shaderUtilShader.close();
		shaderUtilShaders.clear();
	}
	
	@Override
	public void addPass(ResourceLocation passId, PostPass shader) {
		shaderUtilShaders.put(passId, shader);
	}
	
	@Override
	public Map<ResourceLocation, PostPass> getPasses() {
		return shaderUtilShaders;
	}
	
	@Override
	public void clearPasses() {
		shaderUtilShaders.clear();
	}
	
	@Override
	public void removeLast() {
		passes.remove(passes.size() - 1);
	}
	
	@Override
	public PostPass removePass(ResourceLocation passId) {
		return shaderUtilShaders.remove(passId);
	}
	
	@Override
	public PostPass getPass(ResourceLocation passId) {
		return shaderUtilShaders.get(passId);
	}
	
	@Override
	public void removePass(PostPass pass) {
		passes.remove(pass);
	}
	
	@Override
	public void setShaderOrthoMatrix(Matrix4f matrix) {
		this.shaderOrthoMatrix = matrix;
	}
}
