package com.deltateam.deltalib.mixins.client.shader;

import com.deltateam.deltalib.ClientVars;
import com.mojang.blaze3d.platform.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class WindowMixin {
	@Shadow
	private int framebufferWidth;
	
	@Shadow private int framebufferHeight;
	
	@Inject(at = @At("TAIL"), method = "onFramebufferResize")
	public void preUpdateFBOSize(CallbackInfo ci) {
		ClientVars.onFboResize(framebufferWidth, framebufferHeight);
	}
}
