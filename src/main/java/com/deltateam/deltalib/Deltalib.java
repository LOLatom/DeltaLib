package com.deltateam.deltalib;

import com.deltateam.deltalib.API.rendering.overlays.OverlayHelper;
import com.deltateam.deltalib.API.rendering.shader.PostProcessingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod("deltalib")
public class Deltalib {
	public static String ModID = "deltalib";
	
	public Deltalib() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.addListener(EntityOverlayer::onRenderEntity);
		modEventBus.addListener(this::registerModels);
		modEventBus.addListener(this::stitchTextures);
        if (!FMLEnvironment.production) MinecraftForge.EVENT_BUS.addListener(this::testingStuffs);
		if (!FMLEnvironment.production) MinecraftForge.EVENT_BUS.addListener(this::tick);
		if (!FMLEnvironment.production) {
			DeferredTester.BLOCKS.register(modEventBus);
			DeferredTester.ITEMS.register(modEventBus);
		}
	}
	
	public void tick(TickEvent.ClientTickEvent event) {
//        if (!PostProcessingUtils.hasPass(new ResourceLocation("deltalib:blur_x"))) {
//            PostPass shader = PostProcessingUtils.addPass(new ResourceLocation("deltalib:blur_x"), new ResourceLocation("minecraft:blur"));
//            shader.getEffect().getUniform("BlurDir").set(1f, 1f);
//			shader.getEffect().getUniform("Radius").set(1f);
//			shader = PostProcessingUtils.addPass(new ResourceLocation("deltalib:blit"), new ResourceLocation("minecraft:blit"));
//		}
	}
	
	private void testingStuffs(LivingEvent.LivingUpdateEvent event) {
		for (int i = 0; i <= 1000; i++) {
			OverlayHelper.removeOverlay(
					event.getEntity().getUUID(),
					new ResourceLocation("minecraft:textures/block/a" + i + ".png")
			);
		}
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
