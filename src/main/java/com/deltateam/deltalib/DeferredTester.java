package com.deltateam.deltalib;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DeferredTester {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Deltalib.ModID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Deltalib.ModID);
	public static RegistryObject<Block> ctbTest = BLOCKS.register("ctbtest", () -> new Block(Block.Properties.copy(Blocks.STONE)));
	public static RegistryObject<Item> ctbTest_item = ITEMS.register("ctbtest", () -> new BlockItem(ctbTest.get(), new Item.Properties()));
}
