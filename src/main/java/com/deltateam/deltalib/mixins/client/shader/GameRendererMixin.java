package com.deltateam.deltalib.mixins.client.shader;

import com.deltateam.deltalib.accessors.GameRendererAccessor;
import com.deltateam.deltalib.accessors.ShaderGroupAccessor;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.HashMap;

@Mixin(GameRenderer.class)
public class GameRendererMixin implements GameRendererAccessor {
	@Shadow
	private boolean effectActive;
	
	@Shadow
	@Nullable
	private PostChain postEffect;
	
	@Shadow
	@Final
	private Minecraft minecraft;
	
	@Shadow
	@Final
	private ResourceManager resourceManager;

//	@Inject(at = @At(value = "TAIL"), method = "loadShaders")
//	public void preClear(IResourceManager manager, CallbackInfo ci) {
//		CoreShaderRegistry.reload(manager);
//	}
	
	@Inject(at = @At("HEAD"), method = "shutdownEffect")
	public void preDisableShaders(CallbackInfo ci) {
		preSwapShaders();
	}
	
	@Inject(at = @At("HEAD"), method = "loadEffect")
	public void preLoadShader(ResourceLocation id, CallbackInfo ci) {
		preSwapShaders();
	}
	
	@Inject(at = @At("TAIL"), method = "onResourceManagerReload")
	public void postReload(ResourceManager manager, CallbackInfo ci) {
		postSwapShaders();
	}
	
	@Inject(at = @At("TAIL"), method = "onResourceManagerReload")
	public void postLoadShaders(ResourceManager manager, CallbackInfo ci) {
//		System.out.println(isShaderEnabled(new ResourceLocation("shaderutil:blur_x")));
		onLoad();
	}
	
	@Inject(at = @At("TAIL"), method = "<init>")
	public void postLoadShaders(Minecraft mcIn, ResourceManager resourceManagerIn, RenderBuffers renderTypeBuffersIn, CallbackInfo ci) {
//		System.out.println(isShaderEnabled(new ResourceLocation("shaderutil:blur_x")));
//		onLoad();
	}
	
	private void onLoad() {
		passes.clear();
		if (postEffect == dummyEffect) postEffect = null;
		if (dummyEffect != null) {
			dummyEffect.close();
		}
		try {
			PostChain tempDummyEffect = new PostChain(
					this.minecraft.getTextureManager(),
					this.resourceManager,
					this.minecraft.getMainRenderTarget(),
					new ResourceLocation("deltalib:shaders/post/blit.json")
			);
			tempDummyEffect.resize(this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight());
			effectActive = false;
//			postEffect = null;
			this.dummyEffect = tempDummyEffect;
//			if (shader == null) shader = dummyEffect;
//			((ShaderGroupAccessor) dummyEffect).setProjectionMatrix(Matrix4f.projectionMatrix(1, 1, 1, 1));
		} catch (Throwable err) {
			err.printStackTrace();
			if (err instanceof RuntimeException) throw (RuntimeException) err;
		}
		postSwapShaders();
	}
	
	@Override
	public boolean isShaderEnabled(ResourceLocation passId) {
		return passes.containsKey(passId);
	}
	
	@Inject(at = @At("TAIL"), method = "checkEntityPostEffect")
	public void postSetCamEntity(Entity entity, CallbackInfo ci) {
		postSwapShaders();
	}
	
	@Inject(at = @At("HEAD"), method = "checkEntityPostEffect")
	public void preSetCamEntity(Entity entity, CallbackInfo ci) {
		preSwapShaders();
	}
	
	@Inject(at = @At("TAIL"), method = "shutdownEffect")
	public void postDisableShaders(CallbackInfo ci) {
		postSwapShaders();
	}
	
	@Inject(at = @At("TAIL"), method = "resize")
	public void postResize(int width, int height, CallbackInfo ci) {
		if (dummyEffect != null)
			dummyEffect.resize(width, height);
	}
	
	@Unique
	private void preSwapShaders() {
		if (postEffect == dummyEffect) {
			postEffect = null;
			effectActive = false;
		} else {
			if (postEffect != null)
				((ShaderGroupAccessor) postEffect).clearPasses();
		}
	}
	
	@Unique
	private void postSwapShaders() {
		if (postEffect == null) {
			postEffect = dummyEffect;
			effectActive = true;
		} else {
			HashMap<ResourceLocation, PostPass> shaderMap = ((ShaderGroupAccessor) dummyEffect).getPasses();
			for (ResourceLocation identifier : shaderMap.keySet())
				((ShaderGroupAccessor) postEffect).addPass(identifier, shaderMap.get(identifier));
		}
	}
	
	@Unique
	PostChain dummyEffect;
	
	@Unique
	private static final Logger LOGGER = LogManager.getLogger("ShaderUtil");
	
	@Inject(at = @At("TAIL"), method = "resetProjectionMatrix")
	public void postLoadProjMat(Matrix4f matrix, CallbackInfo ci) {
//		((ShaderGroupAccessor) dummyEffect).setProjectionMatrix(matrix);
	}
	
	@Override
	public PostPass addPass(ResourceLocation passId, ResourceLocation shader) {
		PostPass shader1 = null;
		try {
			shader1 = dummyEffect.addPass(shader.toString(), minecraft.getMainRenderTarget(), minecraft.getMainRenderTarget());
			passes.put(passId, shader1);
			if (shader1 != null) {
				((ShaderGroupAccessor) dummyEffect).addPass(passId, shader1);
				if (this.postEffect != null && this.postEffect != dummyEffect)
					((ShaderGroupAccessor) this.postEffect).addPass(passId, shader1);
			}
			((ShaderGroupAccessor) dummyEffect).removeLast();
		} catch (Throwable err) {
			Throwable cause = err;
			StringBuilder exception = new StringBuilder();
			// heck you too mojang, I don't care about your stupid wrapper which consumes the actual issue
			if (cause instanceof LevelRenderer.TransparencyShaderException || cause instanceof RuntimeException) {
				if (cause.getCause() != null) cause = cause.getCause();
			}
			exception.append(cause.getClass().getName()).append(" : ").append(cause.getMessage());
			for (StackTraceElement stackTraceElement : cause.getStackTrace())
				exception.append("\n  ").append(stackTraceElement.toString());
			LOGGER.error(exception.toString());
		}
		
		dummyEffect.resize(this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight());
		
		return shader1;
	}
	
	@Unique
	HashMap<ResourceLocation, PostPass> passes = new HashMap<>();
	
	@Override
	public void removePass(ResourceLocation passId) {
		passes.remove(passId);
		PostPass shader1 = ((ShaderGroupAccessor) dummyEffect).removePass(passId);
		((ShaderGroupAccessor) dummyEffect).removePass(shader1);
		shader1.close();
		if (postEffect != dummyEffect) {
			shader1 = ((ShaderGroupAccessor) dummyEffect).removePass(passId);
		}
	}
	
	@Override
	public PostPass getPass(ResourceLocation passId) {
		passes.remove(passId);
		PostPass shader1 = ((ShaderGroupAccessor) dummyEffect).getPass(passId);
		return shader1;
	}
}
