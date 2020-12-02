package com.deltateam.deltalib;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;

public class RedirectingBuffer implements IRenderTypeBuffer {
	private final IVertexBuilder builder;
	public RedirectingBuffer(IRenderTypeBuffer parent, RenderType type) {
		builder = parent.getBuffer(type);
	}
	
	@Override
	public IVertexBuilder getBuffer(RenderType p_getBuffer_1_) {
		return builder;
	}
}
