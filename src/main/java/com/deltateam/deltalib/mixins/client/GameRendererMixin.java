//package com.deltateam.deltalib.mixins.client;
//
//import com.deltateam.deltalib.Deltalib;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.GameRenderer;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(GameRenderer.class)
//public class GameRendererMixin {
//	@Inject(at = @At("HEAD"), method = "render")
//	public void preRender(float crashreport, long crashreportcategory, boolean throwable2, CallbackInfo ci) {
//		Deltalib.preRenderLevel();
//		Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
//	}
//}
