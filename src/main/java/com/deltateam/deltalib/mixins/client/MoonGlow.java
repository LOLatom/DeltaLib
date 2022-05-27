//package com.deltateam.deltalib.mixins.client;
//
//import com.deltateam.deltalib.Deltalib;
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.*;
//import com.mojang.math.Matrix4f;
//import net.minecraft.client.Camera;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.multiplayer.ClientLevel;
//import net.minecraft.client.renderer.GameRenderer;
//import net.minecraft.client.renderer.LevelRenderer;
//import net.minecraft.client.renderer.LightTexture;
//import net.minecraft.resources.ResourceLocation;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//import javax.annotation.Nullable;
//
//@Mixin(LevelRenderer.class)
//public class MoonGlow {
//	@Shadow
//	@Final
//	private static ResourceLocation MOON_LOCATION;
//
//	@Shadow
//	@Nullable
//	private ClientLevel level;
//
//	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getStarBrightness(F)F"), method = "renderSky")
//	public void preGetStarBrightness(PoseStack f6, Matrix4f f7, float f8, Camera j, boolean f2, Runnable f3, CallbackInfo ci) {
//		// TODO: sun
//		Deltalib.glowTarget.bindWrite(true);
//		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
//		float f12 = 20.0F;
//		Matrix4f matrix4f1 = f6.last().pose();
//		RenderSystem.setShaderTexture(0, MOON_LOCATION);
//		int k = this.level.getMoonPhase();
//		int l = k % 4;
//		int i1 = k / 4 % 2;
//		float f13 = (float) (l + 0) / 4.0F;
//		float f14 = (float) (i1 + 0) / 2.0F;
//		float f15 = (float) (l + 1) / 4.0F;
//		float f16 = (float) (i1 + 1) / 2.0F;
//		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
//		bufferbuilder.vertex(matrix4f1, -f12, -100.0F, f12).uv(f15, f16).endVertex();
//		bufferbuilder.vertex(matrix4f1, f12, -100.0F, f12).uv(f13, f16).endVertex();
//		bufferbuilder.vertex(matrix4f1, f12, -100.0F, -f12).uv(f13, f14).endVertex();
//		bufferbuilder.vertex(matrix4f1, -f12, -100.0F, -f12).uv(f15, f14).endVertex();
//		bufferbuilder.end();
//		BufferUploader.end(bufferbuilder);
//		RenderSystem.disableTexture();
//		Deltalib.glowTarget.unbindWrite();
//		Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
//	}
//}
