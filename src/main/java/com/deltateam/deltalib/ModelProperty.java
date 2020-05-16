package com.deltateam.deltalib;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nullable;
import java.util.HashMap;

public class ModelProperty implements IModelData {
	HashMap<BlockPos,Object> properties=new HashMap<>();
	
	@Override
	public boolean hasProperty(net.minecraftforge.client.model.data.ModelProperty<?> prop) {
		return properties.containsKey(prop);
	}
	
	@Nullable
	@Override
	public <T> T getData(net.minecraftforge.client.model.data.ModelProperty<T> prop) {
		return (T)properties.get(prop);
	}
	
	@Nullable
//	@Override
	public <T> T getData(net.minecraftforge.client.model.data.ModelProperty<T> prop, BlockPos pos) {
		return (T)properties.get(pos);
	}
	
	@Nullable
	@Override
	public <T> T setData(net.minecraftforge.client.model.data.ModelProperty<T> prop, T data) {
//		properties.put(pos,data);
		return data;
	}
	
	@Nullable
//	@Override
	public <T> T setData(net.minecraftforge.client.model.data.ModelProperty<T> prop, T data,BlockPos pos) {
		properties.put(pos,data);
		return data;
	}
};
