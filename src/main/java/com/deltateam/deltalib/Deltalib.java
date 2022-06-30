package com.deltateam.deltalib;

import com.deltateam.deltalib.API.rendering.overlays.OverlayHelper;
import com.deltateam.deltalib.API.rendering.shader.PostProcessingUtils;
import com.deltateam.deltalib.accessors.ShaderAccessor;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.io.File;

@Mod("deltalib")
public class Deltalib {
	public static String ModID = "deltalib";
	
	public Deltalib() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		MinecraftForge.EVENT_BUS.register(this);
		if (FMLEnvironment.dist.isClient()) {
			MinecraftForge.EVENT_BUS.addListener(EntityOverlayer::onRenderEntity);
			modEventBus.addListener(this::registerModels);
			modEventBus.addListener(this::stitchTextures);
		}
		if (!FMLEnvironment.production) MinecraftForge.EVENT_BUS.addListener(this::testingStuffs);
		if (!FMLEnvironment.production) MinecraftForge.EVENT_BUS.addListener(this::tick);
		if (!FMLEnvironment.production) {
			DeferredTester.BLOCKS.register(modEventBus);
			DeferredTester.ITEMS.register(modEventBus);
		}
	}
	
//	public static final RenderTarget glowTarget = new TextureTarget(1, 1, true, Minecraft.ON_OSX);
//	public static final RenderTarget glowTargetSwap = new TextureTarget(1, 1, true, Minecraft.ON_OSX);
	
	public static void preRenderLevel() {
//		if (
//				glowTarget.width != Minecraft.getInstance().getWindow().getWidth() ||
//						glowTarget.height != Minecraft.getInstance().getWindow().getHeight()
//		) {
//			// TODO: check that this is actually the FBO size
//			glowTarget.resize(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight(), Minecraft.ON_OSX);
//			glowTargetSwap.resize(Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight(), Minecraft.ON_OSX);
//		}
//
//		try {
//			String outputPath = "fbo";
//			String path = "glow";
//
//			File fboFile = new File(outputPath + "/" + path + "_color.png");
//			if (!fboFile.exists()) {
//				fboFile.getAbsoluteFile().getParentFile().mkdirs();
//				fboFile.createNewFile();
////					NativeImage img = ScreenshotRecorder.takeScreenshot(fbo);
//				NativeImage img;
//				{
//					int i = glowTargetSwap.width;
//					int j = glowTargetSwap.height;
//					NativeImage nativeImage = new NativeImage(i, j, false);
//					RenderSystem.bindTexture(glowTargetSwap.getColorTextureId());
//					nativeImage.downloadTexture(0, true);
//					nativeImage.flipY();
//					img = nativeImage;
//				}
//				img.writeToFile(fboFile);
//				img.close();
//			}
//
//			fboFile = new File(outputPath + "/src_" + path + "_color.png");
//			if (!fboFile.exists()) {
//				fboFile.getAbsoluteFile().getParentFile().mkdirs();
//				fboFile.createNewFile();
////					NativeImage img = ScreenshotRecorder.takeScreenshot(fbo);
//				NativeImage img;
//				{
//					int i = glowTarget.width;
//					int j = glowTarget.height;
//					NativeImage nativeImage = new NativeImage(i, j, false);
//					RenderSystem.bindTexture(glowTarget.getColorTextureId());
//					nativeImage.downloadTexture(0, true);
//					nativeImage.flipY();
//					img = nativeImage;
//				}
//				img.writeToFile(fboFile);
//				img.close();
//			}
//
//			fboFile = new File(outputPath + "/" + path + "_depth.png");
//			if (!fboFile.exists()) {
//				fboFile.getAbsoluteFile().getParentFile().mkdirs();
//				fboFile.createNewFile();
////					NativeImage img = ScreenshotRecorder.takeScreenshot(fbo);
//				NativeImage img;
//				{
//					int i = glowTarget.width;
//					int j = glowTarget.height;
//					NativeImage nativeImage = new NativeImage(i, j, false);
//					RenderSystem.bindTexture(glowTarget.getDepthTextureId());
//					nativeImage.downloadTexture(0, true);
//					nativeImage.flipY();
//					img = nativeImage;
//				}
//				img.writeToFile(fboFile);
//				img.close();
//			}
//		} catch (Throwable ignored) {
//		}
//
//		glowTarget.setClearColor(0, 0, 0, 0);
//		glowTarget.clear(Minecraft.ON_OSX);
//		glowTargetSwap.setClearColor(0, 0, 0, 0);
//		glowTargetSwap.clear(Minecraft.ON_OSX);
	}
	
	public void tick(TickEvent.ClientTickEvent event) {
//		if (event.phase == TickEvent.Phase.START) {
//			if (!PostProcessingUtils.hasPass(new ResourceLocation("deltalib:merge"))) {
//				PostPass shader;
//				shader = PostProcessingUtils.addPass(new ResourceLocation("deltalib:glow"), new ResourceLocation("deltalib:blur"));
//				((ShaderAccessor) shader).addDepthTarget("VanillaDepth", Minecraft.getInstance().getMainRenderTarget());
//				((ShaderAccessor) shader).addDepthTarget("GlowDepth", glowTarget);
//				((ShaderAccessor) shader).setSourceBuffer(glowTarget);
//				((ShaderAccessor) shader).setTargetBuffer(glowTargetSwap);
//				shader = PostProcessingUtils.addPass(new ResourceLocation("deltalib:merge"), new ResourceLocation("deltalib:draw"));
//				((ShaderAccessor) shader).addAuxTarget("glow", glowTargetSwap);
//				((ShaderAccessor) shader).addDepthTarget("GlowDepth", glowTarget);
//				shader = PostProcessingUtils.addPass(new ResourceLocation("deltalib:blit"), new ResourceLocation("minecraft:blit"));
//			}
//		}
	}
	
	private void testingStuffs(LivingEvent.LivingUpdateEvent event) {
//		for (int i = 0; i <= 1000; i++) {
//			OverlayHelper.removeOverlay(
//					event.getEntity().getUUID(),
//					new ResourceLocation("minecraft:textures/block/a" + i + ".png")
//			);
//		}
	}
	
	private void registerModels(final ModelRegistryEvent event) {
		ModelLoaderRegistry.registerLoader(new ResourceLocation("deltalib:connected_textures"), new ConnectedTexturesModelLoader());
//		System.out.println("models");
	}
	
	private void stitchTextures(final TextureStitchEvent event) {
//        if (event.getMap().location().equals(AtlasTexture.LOCATION_BLOCKS)) {
//            if (event instanceof TextureStitchEvent.Post) {
//            }
//        }
//        System.out.println("textures");
	}
}
