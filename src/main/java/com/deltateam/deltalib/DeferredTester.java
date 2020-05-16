package com.deltateam.deltalib;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DeferredTester {
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS,Deltalib.ModID);
    public static RegistryObject<Block> ctbTest = BLOCKS.register("ctbtest",()->new testCTB(Block.Properties.from(Blocks.STONE)));
}
