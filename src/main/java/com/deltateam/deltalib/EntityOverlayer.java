package com.deltateam.deltalib;

import com.deltateam.deltalib.API.rendering.overlays.OverlayHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderLivingEvent;

public class EntityOverlayer {
	private static boolean isRendering = false;
	
	public static void onRenderEntity(RenderLivingEvent<LivingEntity, ?> event) {
		if (event instanceof RenderLivingEvent.Post && !isRendering) {
			if (OverlayHelper.hasOverlayForEntity(event.getEntity().getUUID())) {
				isRendering = true;
				for (ResourceLocation location : OverlayHelper.getForEntity(event.getEntity().getUUID()).toArray(new ResourceLocation[0])) {
					event.getRenderer().render(
							event.getEntity(),
							event.getEntity().getViewYRot(event.getPartialTick()),
							event.getPartialTick(), event.getPoseStack(),
							new RedirectingBuffer(event.getMultiBufferSource(), RenderType.entityTranslucent(location)),
							event.getPackedLight()
					);
				}
				isRendering = false;
			}
		}
	}
}
