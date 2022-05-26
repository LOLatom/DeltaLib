package com.deltateam.deltalib;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

public class RedirectingBuffer implements MultiBufferSource {
	private final VertexConsumer builder;
	public RedirectingBuffer(MultiBufferSource parent, RenderType type) {
		builder = parent.getBuffer(type);
	}
	
	@Override
	public VertexConsumer getBuffer(RenderType p_getBuffer_1_) {
		return builder;
	}
}
