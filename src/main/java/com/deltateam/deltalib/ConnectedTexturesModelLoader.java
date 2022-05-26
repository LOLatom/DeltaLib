package com.deltateam.deltalib;

import com.deltateam.deltalib.API.rendering.BakeableQuad;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class ConnectedTexturesModelLoader implements IModelLoader<ConnectedTexturesModelLoader.ConnectedTextureGeometry> {
	public ConnectedTexturesModelLoader() {
	}
	
	@Override
	public void onResourceManagerReload(ResourceManager resourceManager) {
	}
	
	@Override
	public ConnectedTextureGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
		System.out.println(modelContents);
		String base = modelContents.getAsJsonPrimitive("base").getAsString();
		String border = modelContents.getAsJsonPrimitive("border").getAsString();
		int padding = modelContents.getAsJsonPrimitive("padding").getAsInt();
		return new ConnectedTextureGeometry(base, border, padding);
	}
	
	public static class ConnectedTextureGeometry implements IModelGeometry<ConnectedTextureGeometry> {
		String base, border;
		int padding;
		
		public ConnectedTextureGeometry(String base, String border, int padding) {
			this.base = base;
			this.border = border;
			this.padding = padding;
		}
		
		@Override
		public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation) {
			return new ConnectedTexturesModel(base, border, padding);
		}
		
		@Override
		public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
			return ImmutableSet.of(
					new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(base)),
					new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(border))
			);
		}
	}
	
	public static class ConnectedTexturesModelData implements IModelData {
		HashMap<ModelProperty<?>, Object> propertyHashMap = new HashMap<>();
		
		@Override
		public boolean hasProperty(ModelProperty<?> prop) {
			return propertyHashMap.containsKey(prop);
		}
		
		@Nullable
		@Override
		public <T> T getData(ModelProperty<T> prop) {
			return (T) propertyHashMap.get(prop);
		}
		
		@Nullable
		@Override
		public <T> T setData(ModelProperty<T> prop, T data) {
			return (T) propertyHashMap.put(prop, data);
		}
	}
	
	public static class ConnectedTexturesModel implements IDynamicBakedModel {
		String base, border;
		int padding;
		
		public ConnectedTexturesModel(String base, String border, int padding) {
			this.base = base;
			this.border = border;
			this.padding = padding;
		}
		
		@Nonnull
		@Override
		public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
			ArrayList<BakedQuad> quads = new ArrayList<>();
			ArrayList<BakeableQuad> unbakedQuads = new ArrayList<>();
			if (side == null) return quads;
			ModelProperty<BlockPos> eastProperty = offsets.get(new BlockPos(1, 0, 0));
			ModelProperty<BlockPos> westProperty = offsets.get(new BlockPos(-1, 0, 0));
			ModelProperty<BlockPos> upProperty = offsets.get(new BlockPos(0, 1, 0));
			ModelProperty<BlockPos> downProperty = offsets.get(new BlockPos(0, -1, 0));
			ModelProperty<BlockPos> northProperty = offsets.get(new BlockPos(0, 0, -1));
			ModelProperty<BlockPos> southProperty = offsets.get(new BlockPos(0, 0, 1));
			boolean east = !extraData.hasProperty(eastProperty) || extraData.getData(eastProperty) == null;
			boolean west = !extraData.hasProperty(westProperty) || extraData.getData(westProperty) == null;
			boolean up = !extraData.hasProperty(upProperty) || extraData.getData(upProperty) == null;
			boolean down = !extraData.hasProperty(downProperty) || extraData.getData(downProperty) == null;
			boolean north = !extraData.hasProperty(northProperty) || extraData.getData(northProperty) == null;
			boolean south = !extraData.hasProperty(southProperty) || extraData.getData(southProperty) == null;
			if (side.equals(Direction.NORTH) || side.equals(Direction.SOUTH)) {
				ModelProperty<BlockPos> eastUpProperty = offsets.get(new BlockPos(1, 1, 0));
				ModelProperty<BlockPos> westUpProperty = offsets.get(new BlockPos(-1, 1, 0));
				ModelProperty<BlockPos> eastDownProperty = offsets.get(new BlockPos(1, -1, 0));
				ModelProperty<BlockPos> westDownProperty = offsets.get(new BlockPos(-1, -1, 0));
				boolean eastUp = !extraData.hasProperty(eastUpProperty) || extraData.getData(eastUpProperty) == null;
				boolean westUp = !extraData.hasProperty(westUpProperty) || extraData.getData(westUpProperty) == null;
				boolean eastDown = !extraData.hasProperty(eastDownProperty) || extraData.getData(eastDownProperty) == null;
				boolean westDown = !extraData.hasProperty(westDownProperty) || extraData.getData(westDownProperty) == null;
				unbakedQuads.add(new BakeableQuad(new Vec3(padding, padding, 0), new Vec3(16 - padding, 16 - padding, 0), Direction.NORTH, new ResourceLocation(base)));
				unbakedQuads.add(new BakeableQuad(new Vec3(padding, 16 - padding, 0), new Vec3(16 - padding, 16, 0), Direction.NORTH, new ResourceLocation(up ? border : base)));
				unbakedQuads.add(new BakeableQuad(new Vec3(padding, 0, 0), new Vec3(16 - padding, padding, 0), Direction.NORTH, new ResourceLocation(down ? border : base)));
				unbakedQuads.add(new BakeableQuad(new Vec3(16 - padding, padding, 0), new Vec3(16, 16 - padding, 0), Direction.NORTH, new ResourceLocation(east ? border : base)));
				unbakedQuads.add(new BakeableQuad(new Vec3(0, padding, 0), new Vec3(padding, 16 - padding, 0), Direction.NORTH, new ResourceLocation(west ? border : base)));
				if (east || up) eastUp = true;
				unbakedQuads.add(new BakeableQuad(new Vec3(16 - padding, 16 - padding, 0), new Vec3(16, 16, 0), Direction.NORTH, new ResourceLocation(eastUp ? border : base)));
				if (west || up) westUp = true;
				unbakedQuads.add(new BakeableQuad(new Vec3(0, 16 - padding, 0), new Vec3(padding, 16, 0), Direction.NORTH, new ResourceLocation(westUp ? border : base)));
				if (west || down) westDown = true;
				unbakedQuads.add(new BakeableQuad(new Vec3(0, 0, 0), new Vec3(padding, padding, 0), Direction.NORTH, new ResourceLocation(westDown ? border : base)));
				if (east || down) eastDown = true;
				unbakedQuads.add(new BakeableQuad(new Vec3(16 - padding, 0, 0), new Vec3(16, padding, 0), Direction.NORTH, new ResourceLocation(eastDown ? border : base)));
			} else if (side.equals(Direction.EAST) || side.equals(Direction.WEST)) {
				ModelProperty<BlockPos> eastUpProperty = offsets.get(new BlockPos(0, 1, 1));
				ModelProperty<BlockPos> westUpProperty = offsets.get(new BlockPos(0, 1, -1));
				ModelProperty<BlockPos> eastDownProperty = offsets.get(new BlockPos(0, -1, 1));
				ModelProperty<BlockPos> westDownProperty = offsets.get(new BlockPos(0, -1, -1));
				boolean eastUp = !extraData.hasProperty(eastUpProperty) || extraData.getData(eastUpProperty) == null;
				boolean westUp = !extraData.hasProperty(westUpProperty) || extraData.getData(westUpProperty) == null;
				boolean eastDown = !extraData.hasProperty(eastDownProperty) || extraData.getData(eastDownProperty) == null;
				boolean westDown = !extraData.hasProperty(westDownProperty) || extraData.getData(westDownProperty) == null;
				unbakedQuads.add(new BakeableQuad(new Vec3(padding, padding, 0), new Vec3(16 - padding, 16 - padding, 0), Direction.NORTH, new ResourceLocation(base)));
				unbakedQuads.add(new BakeableQuad(new Vec3(padding, 16 - padding, 0), new Vec3(16 - padding, 16, 0), Direction.NORTH, new ResourceLocation(up ? border : base)));
				unbakedQuads.add(new BakeableQuad(new Vec3(padding, 0, 0), new Vec3(16 - padding, padding, 0), Direction.NORTH, new ResourceLocation(down ? border : base)));
				unbakedQuads.add(new BakeableQuad(new Vec3(16 - padding, padding, 0), new Vec3(16, 16 - padding, 0), Direction.NORTH, new ResourceLocation(south ? border : base)));
				unbakedQuads.add(new BakeableQuad(new Vec3(0, padding, 0), new Vec3(padding, 16 - padding, 0), Direction.NORTH, new ResourceLocation(north ? border : base)));
				if (south || up) eastUp = true;
				unbakedQuads.add(new BakeableQuad(new Vec3(16 - padding, 16 - padding, 0), new Vec3(16, 16, 0), Direction.NORTH, new ResourceLocation(eastUp ? border : base)));
				if (north || up) westUp = true;
				unbakedQuads.add(new BakeableQuad(new Vec3(0, 16 - padding, 0), new Vec3(padding, 16, 0), Direction.NORTH, new ResourceLocation(westUp ? border : base)));
				if (north || down) westDown = true;
				unbakedQuads.add(new BakeableQuad(new Vec3(0, 0, 0), new Vec3(padding, padding, 0), Direction.NORTH, new ResourceLocation(westDown ? border : base)));
				if (south || down) eastDown = true;
				unbakedQuads.add(new BakeableQuad(new Vec3(16 - padding, 0, 0), new Vec3(16, padding, 0), Direction.NORTH, new ResourceLocation(eastDown ? border : base)));
			} else if (side.equals(Direction.UP) || side.equals(Direction.DOWN)) {
				ModelProperty<BlockPos> eastUpProperty = offsets.get(new BlockPos(1, 0, 1));
				ModelProperty<BlockPos> westUpProperty = offsets.get(new BlockPos(1, 0, -1));
				ModelProperty<BlockPos> eastDownProperty = offsets.get(new BlockPos(-1, 0, 1));
				ModelProperty<BlockPos> westDownProperty = offsets.get(new BlockPos(-1, 0, -1));
				boolean eastUp = !extraData.hasProperty(eastUpProperty) || extraData.getData(eastUpProperty) == null;
				boolean westUp = !extraData.hasProperty(westUpProperty) || extraData.getData(westUpProperty) == null;
				boolean eastDown = !extraData.hasProperty(eastDownProperty) || extraData.getData(eastDownProperty) == null;
				boolean westDown = !extraData.hasProperty(westDownProperty) || extraData.getData(westDownProperty) == null;
				unbakedQuads.add(new BakeableQuad(new Vec3(padding, padding, 0), new Vec3(16 - padding, 16 - padding, 0), Direction.NORTH, new ResourceLocation(base)));
				unbakedQuads.add(new BakeableQuad(new Vec3(padding, 16 - padding, 0), new Vec3(16 - padding, 16, 0), Direction.NORTH, new ResourceLocation(east ? border : base)));
				unbakedQuads.add(new BakeableQuad(new Vec3(padding, 0, 0), new Vec3(16 - padding, padding, 0), Direction.NORTH, new ResourceLocation(west ? border : base)));
				unbakedQuads.add(new BakeableQuad(new Vec3(16 - padding, padding, 0), new Vec3(16, 16 - padding, 0), Direction.NORTH, new ResourceLocation(south ? border : base)));
				unbakedQuads.add(new BakeableQuad(new Vec3(0, padding, 0), new Vec3(padding, 16 - padding, 0), Direction.NORTH, new ResourceLocation(north ? border : base)));
				if (south || east) eastUp = true;
				unbakedQuads.add(new BakeableQuad(new Vec3(16 - padding, 16 - padding, 0), new Vec3(16, 16, 0), Direction.NORTH, new ResourceLocation(eastUp ? border : base)));
				if (north || east) westUp = true;
				unbakedQuads.add(new BakeableQuad(new Vec3(0, 16 - padding, 0), new Vec3(padding, 16, 0), Direction.NORTH, new ResourceLocation(westUp ? border : base)));
				if (north || west) westDown = true;
				unbakedQuads.add(new BakeableQuad(new Vec3(0, 0, 0), new Vec3(padding, padding, 0), Direction.NORTH, new ResourceLocation(westDown ? border : base)));
				if (south || west) eastDown = true;
				unbakedQuads.add(new BakeableQuad(new Vec3(16 - padding, 0, 0), new Vec3(16, padding, 0), Direction.NORTH, new ResourceLocation(eastDown ? border : base)));
			}
			
			for (BakeableQuad unbakedQuad : unbakedQuads) {
				quads.add(unbakedQuad.rotate(side).bake());
			}
			
			return quads;
		}
		
		public static final HashMap<BlockPos, ModelProperty<BlockPos>> offsets = new HashMap<>();
		
		static {
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					for (int z = -1; z <= 1; z++) {
						if (
								x != y ||
										y != z
						) {
							BlockPos offset = new BlockPos(x, y, z);
							offsets.put(offset, new ModelProperty<>((pos) -> pos.equals(offset)));
						}
					}
				}
			}
		}
		
		@Nonnull
		@Override
		public IModelData getModelData(@Nonnull BlockAndTintGetter world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
			ConnectedTexturesModelData data = new ConnectedTexturesModelData();
			for (BlockPos blockPos : offsets.keySet()) {
				ModelProperty<BlockPos> property = offsets.get(blockPos);
				BlockState state1 = world.getBlockState(pos.offset(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
				if (state.equals(state1)) {
					data.setData(property, blockPos);
				}
			}
			return data;
		}
		
		@Override
		public boolean useAmbientOcclusion() {
			return true;
		}
		
		@Override
		public boolean usesBlockLight() {
			return true;
		}
		
		@Override
		public boolean isCustomRenderer() {
			return false;
		}
		
		@Override
		public TextureAtlasSprite getParticleIcon() {
			TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(this.base));
			return sprite;
		}
		
		@Override
		public ItemOverrides getOverrides() {
			return null;
		}
		
		@Override
		public boolean isGui3d() {
			return true;
		}
	}
}
