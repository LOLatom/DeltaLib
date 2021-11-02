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
	private List<Shader> listShaders;
	
	@Shadow private Matrix4f projectionMatrix;
	@Shadow @Final private Framebuffer mainFramebuffer;
	@Shadow @Final private Map<String, Framebuffer> mapFramebuffers;
	
	@Shadow public abstract void addFramebuffer(String name, int width, int height);
	
	@Shadow private int mainFramebufferWidth;
	@Shadow
	private int mainFramebufferHeight;
	@Unique
	HashMap<ResourceLocation, Shader> shaderUtilShaders = new HashMap<>();
	
	@Inject(at = @At("TAIL"), method = "render")
	public void drawShaderUtilShaders(float tickDelta, CallbackInfo ci) {
		if (shaderUtilShaders.isEmpty()) return;
		
		if (mapFramebuffers.isEmpty()) {
			// currently; if this ever gets called, the game will likely crash
			addFramebuffer("deltalib:foolproofing", mainFramebufferWidth, mainFramebufferHeight);
		}
		
		Framebuffer buffer = null;
		for (String s : mapFramebuffers.keySet()) {
			buffer = mapFramebuffers.get(s);
			if (buffer != mainFramebuffer) {
				break;
			}
		}
//		Framebuffer alternator = defaultSizedTargets.get(0);
//		Framebuffer src = alternator;
//		Framebuffer alt = (alternator == mainTarget) ? mainTarget : defaultSizedTargets.get(1);
		Framebuffer alternator = mainFramebuffer;
		Framebuffer src = mainFramebuffer;
		Framebuffer alt = buffer;
		
		for (Shader shaderUtilShader : shaderUtilShaders.values()) {
			if (((ShaderAccessor)shaderUtilShader).getMatrix() != null) {
				((ShaderAccessor) shaderUtilShader).setFramebufferOut(alternator);
				if (alternator == src) alternator = alt;
				else alternator = src;
				((ShaderAccessor) shaderUtilShader).setFramebufferIn(alternator);
				shaderUtilShader.render(tickDelta);
			}
		}
//		if (alternator == src) alternator = alt;
//		else alternator = src;
		if (alternator != alt) {
			Shader endShader = listShaders.get(listShaders.size() - 1);
			endShader.render(tickDelta);
		}

//		PostProcessShader endShader = passes.get(passes.size() - 1);
//		((PostProcessShaderAccessor) endShader).setOutput(endTarg);
	}
	
	@Inject(at = @At("TAIL"), method = "createBindFramebuffers")
	public void updateDimensions(int targetsWidth, int targetsHeight, CallbackInfo ci) {
		if (shaderUtilShaders.isEmpty()) return;
		
		for (Shader shaderUtilShader : shaderUtilShaders.values())
			shaderUtilShader.setProjectionMatrix(projectionMatrix);
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
	public HashMap<ResourceLocation, Shader> getListShaders() {
		return shaderUtilShaders;
	}
	
	@Override
	public void clearPasses() {
		shaderUtilShaders.clear();
	}
	
	@Override
	public void removeLast() {
		listShaders.remove(listShaders.size() - 1);
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
		listShaders.remove(pass);
	}
	
	@Override
	public void setProjectionMatrix(Matrix4f matrix) {
		this.projectionMatrix = matrix;
	}
}
