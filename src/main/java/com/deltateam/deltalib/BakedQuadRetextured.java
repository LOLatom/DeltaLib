package com.deltateam.deltalib;

import java.util.Arrays;

import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.FaceBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;

@OnlyIn(Dist.CLIENT)
public class BakedQuadRetextured
{
	public static BakedQuad retexture(BakedQuad quad, TextureAtlasSprite textureIn) {
		BakedQuadBuilder builder=new BakedQuadBuilder();
		for (int i=0;i<quad.getVertexData().length;i++) {
			builder.put(i,quad.getVertexData()[i]);
		}
		builder.setTexture(textureIn);
		builder.setQuadOrientation(quad.getFace());
		builder.setQuadTint(quad.getTintIndex());
		return builder.build();
	}
}
