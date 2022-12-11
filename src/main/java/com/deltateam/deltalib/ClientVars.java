package com.deltateam.deltalib;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import net.minecraft.client.Minecraft;

public class ClientVars {
	public static final RenderTarget terrainDepth = new TextureTarget(1, 1, true, Minecraft.ON_OSX);
	
	public static void onFboResize(int width, int height) {
		// TODO: setup an API or smth
		terrainDepth.resize(width, height, Minecraft.ON_OSX);
	}
}
