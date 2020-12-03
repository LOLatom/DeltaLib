package com.deltateam.deltalib;

import com.deltateam.deltalib.API.rendering.overlays.OverlayHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;

public class EntityOverlayer {
	private static boolean isRendering = false;
	public static void onRenderEntity(RenderLivingEvent<LivingEntity, ?> event) {
		if (event instanceof RenderLivingEvent.Post && !isRendering) {
			if (OverlayHelper.hasOverlayForEntity(event.getEntity().getUniqueID())) {
				isRendering = true;
				for (ResourceLocation location : OverlayHelper.getForEntity(event.getEntity().getUniqueID())) {
					event.getRenderer().render(
							event.getEntity(),
							event.getEntity().getYaw(event.getPartialRenderTick()),
							event.getPartialRenderTick(), event.getMatrixStack(),
							new RedirectingBuffer(event.getBuffers(),RenderType.getEntityTranslucent(location)),
							event.getLight()
					);
				}
				isRendering = false;
			}
		}
	}
}
