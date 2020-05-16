package com.deltateam.deltalib;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

public class ModelLoader {
	public static class Geometry implements IModelGeometry<Geometry> {
		TextureAtlasSprite base;
		TextureAtlasSprite frame;
		
		public Geometry(TextureAtlasSprite base, TextureAtlasSprite frame) {
			this.base = base;
			this.frame = frame;
		}
		
		@Override
		public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
			return new ConnectedTextureBlock.Model(bakery.bake(new ResourceLocation("deltalib:block/ctbbase_sample"),modelTransform)).getBakedModel();
		}
		
		@Override
		public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
			return Arrays.asList(
					owner.resolveTexture("particle"),
					owner.resolveTexture("border"),
					owner.resolveTexture("base")
			);
		}
	}
	public static class Loader implements IModelLoader<Geometry> {
		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {
		}
		
		@Override
		public Geometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
//			JsonArray textureArray = (JsonArray)modelContents.get("textures");
//			String base=JSONUtils.getString(textureArray.get(0), "textures[0]");
//			String frame=JSONUtils.getString(textureArray.get(1), "textures[1]");
			Geometry geometry=new Geometry(null,null
//					Minecraft.getInstance().getAtlasSpriteGetter(new ResourceLocation(base)).apply(new ResourceLocation(base)),
//					Minecraft.getInstance().getAtlasSpriteGetter(new ResourceLocation(base)).apply(new ResourceLocation(frame))
			);
			return geometry;
		}
	}
}
