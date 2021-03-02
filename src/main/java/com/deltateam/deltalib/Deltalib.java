package com.deltateam.deltalib;

import com.deltateam.deltalib.API.rendering.overlays.OverlayHelper;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod("deltalib")
public class Deltalib {
    public static String ModID= "deltalib";
    
    public Deltalib() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(EntityOverlayer::onRenderEntity);
        modEventBus.addListener(this::registerModels);
        modEventBus.addListener(this::stitchTextures);
//        if (!FMLEnvironment.production) MinecraftForge.EVENT_BUS.addListener(this::testingStuffs);
        if (!FMLEnvironment.production) {
            DeferredTester.BLOCKS.register(modEventBus);
            DeferredTester.ITEMS.register(modEventBus);
        }
    }
    
    private void testingStuffs(LivingEvent.LivingUpdateEvent event) {
        for (int i=0;i<=1000;i++) {
            OverlayHelper.removeOverlay(
                    event.getEntity().getUniqueID(),
                    new ResourceLocation("minecraft:textures/block/a"+i+".png")
            );
        }
    }
    
    private void registerModels(final ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(new ResourceLocation("deltalib:connected_textures"),new ConnectedTexturesModelLoader());
        System.out.println("models");
    }
    
    private void stitchTextures(final TextureStitchEvent event) {
        if (event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)) {
            if (event instanceof TextureStitchEvent.Post) {
            }
        }
        System.out.println("textures");
    }
}
