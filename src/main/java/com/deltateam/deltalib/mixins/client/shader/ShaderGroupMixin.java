package com.deltateam.deltalib.mixins.client.shader;

import com.deltateam.deltalib.accessors.ShaderAccessor;
import com.deltateam.deltalib.accessors.ShaderGroupAccessor;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(ShaderGroup.class)
public abstract class ShaderGroupMixin implements ShaderGroupAccessor {
	@Shadow
	@Final
	private List<Shader> passes;
	
	@Shadow private Matrix4f shaderOrthoMatrix;
	@Shadow @Final private Framebuffer screenTarget;
	@Shadow @Final private Map<String, Framebuffer> customRenderTargets;
	
	@Shadow public abstract void addTempTarget(String name, int width, int height);
	
	@Shadow private int screenWidth;
	@Shadow
	private int screenHeight;
	@Unique
	HashMap<ResourceLocation, Shader> shaderUtilShaders = new HashMap<>();
	
	@Inject(at = @At("TAIL"), method = "process")
	public void drawShaderUtilShaders(float tickDelta, CallbackInfo ci) {
		if (shaderUtilShaders.isEmpty()) return;
		
		if (customRenderTargets.isEmpty()) {
			// currently; if this ever gets called, the game will likely crash
			addTempTarget("deltalib:foolproofing", screenWidth, screenHeight);
		}
		
		Framebuffer buffer = null;
		for (String s : customRenderTargets.keySet()) {
			buffer = customRenderTargets.get(s);
			if (buffer != screenTarget) {
				break;
			}
		}
//		Framebuffer alternator = defaultSizedTargets.get(0);
//		Framebuffer src = alternator;
//		Framebuffer alt = (alternator == mainTarget) ? mainTarget : defaultSizedTargets.get(1);
		Framebuffer alternator = screenTarget;
		Framebuffer src = screenTarget;
		Framebuffer alt = buffer;
		
		for (Shader shaderUtilShader : shaderUtilShaders.values()) {
			if (((ShaderAccessor)shaderUtilShader).getMatrix() != null) {
				((ShaderAccessor) shaderUtilShader).setFramebufferOut(alternator);
				if (alternator == src) alternator = alt;
				else alternator = src;
				((ShaderAccessor) shaderUtilShader).setFramebufferIn(alternator);
				shaderUtilShader.process(tickDelta);
			}
		}
//		if (alternator == src) alternator = alt;
//		else alternator = src;
		if (alternator != alt) {
			Shader endShader = passes.get(passes.size() - 1);
			endShader.process(tickDelta);
		}

//		PostProcessShader endShader = passes.get(passes.size() - 1);
//		((PostProcessShaderAccessor) endShader).setOutput(endTarg);
	}
	
	@Inject(at = @At("TAIL"), method = "resize")
	public void updateDimensions(int targetsWidth, int targetsHeight, CallbackInfo ci) {
		if (shaderUtilShaders.isEmpty()) return;
		
		for (Shader shaderUtilShader : shaderUtilShaders.values())
			shaderUtilShader.setOrthoMatrix(shaderOrthoMatrix);
	}
	
	@Inject(at = @At("TAIL"), method = "close")
	public void close(CallbackInfo ci) {
		if (shaderUtilShaders.isEmpty()) return;
		
		for (Shader shaderUtilShader : shaderUtilShaders.values())
			shaderUtilShader.close();
		shaderUtilShaders.clear();
	}
	
	@Override
	public void addPass(ResourceLocation passId, Shader shader) {
		shaderUtilShaders.put(passId, shader);
	}
	
	@Override
	public HashMap<ResourceLocation, Shader> getPasses() {
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
	public Shader removePass(ResourceLocation passId) {
		return shaderUtilShaders.remove(passId);
	}
	
	@Override
	public Shader getPass(ResourceLocation passId) {
		return shaderUtilShaders.get(passId);
	}
	
	@Override
	public void removePass(Shader pass) {
		passes.remove(pass);
	}
	
	@Override
	public void setShaderOrthoMatrix(Matrix4f matrix) {
		this.shaderOrthoMatrix = matrix;
	}
}
