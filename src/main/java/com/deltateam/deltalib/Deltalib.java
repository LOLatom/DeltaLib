package com.deltateam.deltalib;

import com.deltateam.deltalib.API.rendering.overlays.OverlayHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod("deltalib")
public class Deltalib {
    public Deltalib() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(EntityOverlayer::onRenderEntity);
//        if (!FMLEnvironment.production) MinecraftForge.EVENT_BUS.addListener(this::testingStuffs);
    }
    
    private void testingStuffs(LivingEvent.LivingUpdateEvent event) {
        for (int i=0;i<=1000;i++) {
            OverlayHelper.removeOverlay(
                    event.getEntity().getUniqueID(),
                    new ResourceLocation("minecraft:textures/block/a"+i+".png")
            );
        }
    }
}
