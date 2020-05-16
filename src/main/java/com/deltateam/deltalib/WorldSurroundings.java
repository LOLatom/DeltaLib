package com.deltateam.deltalib;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;

public class WorldSurroundings {
	public WorldSurroundings(ILightReader world, BlockPos pos, BlockState state) {
		if (world.getBlockState(pos.west()).getBlock().toString().equals(state.getBlock().toString())) {
			west=true;
		}
	}
	
	boolean north=false;
	boolean east=false;
	boolean south=false;
	boolean west=false;
	boolean up=false;
	boolean down=false;
}
