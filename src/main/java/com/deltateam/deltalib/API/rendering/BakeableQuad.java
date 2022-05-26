package com.deltateam.deltalib.API.rendering;

import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class BakeableQuad {
	private static final FaceBakery bakery = new FaceBakery();
	
	public Vec3 min, max;
	public Direction face;
	public ResourceLocation texture;
	
	boolean useZasU = false;
	boolean useZasV = false;
	
	public BakeableQuad(Vec3 min, Vec3 max, Direction face, ResourceLocation texture) {
		this.min = min;
		this.max = max;
		this.face = face;
		this.texture = texture;
	}
	
	public BakeableQuad useZAsU() {
		this.useZasU = true;
		return this;
	}
	
	public BakeableQuad useZAsV() {
		this.useZasV = true;
		return this;
	}
	
	public BakeableQuad reverse() {
		double minX = min.x;
		double minZ = min.z;
		double minY = min.y;
		double maxX = max.x;
		double maxY = max.y;
		double maxZ = max.z;
		face = face.getOpposite();
		this.min = new Vec3(maxX, maxY, maxZ);
		this.max = new Vec3(minX, minY, minZ);
		return this;
	}
	
	public BakeableQuad move(int x, int y, int z) {
		this.min = min.add(x, y, z);
		this.max = max.add(x, y, z);
		return this;
	}
	
	public float getMinU() {
		return (float) (useZasU ? min.z : min.x);
	}
	
	public float getMaxU() {
		return (float) (useZasU ? max.z : max.x);
	}
	
	public float getMinV() {
		return (float) (useZasV ? min.z : min.y);
	}
	
	public float getMaxV() {
		return (float) (useZasV ? max.z : max.y);
	}
	
	public BakedQuad bake() {
		if (face.equals(Direction.UP)) {
			return bakery.bakeQuad(new Vector3f((float) min.x, (float) min.y, (float) min.z), new Vector3f((float) max.x, (float) max.y, (float) max.z),
					new BlockElementFace(Direction.NORTH, 0, texture.toString(),
							new BlockFaceUV(
									new float[]{
											getMinU(), getMinV(),
											getMaxU(), getMaxV(),
											getMaxU(), getMinV(),
											getMinU(), getMaxV()
									}, 0
							)
					),
					Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(texture), face, BlockModelRotation.X0_Y0,
					new BlockElementRotation(new Vector3f(0, 0, 0), Direction.Axis.X, 0, false),
					true, new ResourceLocation("a:a")
			);
		} else if (face.equals(Direction.NORTH) || face.equals(Direction.EAST)) {
			return bakery.bakeQuad(new Vector3f((float) min.x, (float) min.y, (float) min.z), new Vector3f((float) max.x, (float) max.y, (float) max.z),
					new BlockElementFace(Direction.NORTH, 0, texture.toString(),
							new BlockFaceUV(
									new float[]{
											getMaxU(), 16 - getMaxV(),
											getMinU(), 16 - getMinV(),
											getMaxU(), 16 - getMinV(),
											getMinU(), 16 - getMaxV()
									}, 0
							)
					),
					Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(texture), face, BlockModelRotation.X0_Y0,
					new BlockElementRotation(new Vector3f(0, 0, 0), Direction.Axis.X, 0, false),
					true, new ResourceLocation("a:a")
			);
		} else {
			return bakery.bakeQuad(new Vector3f((float) min.x, (float) min.y, (float) min.z), new Vector3f((float) max.x, (float) max.y, (float) max.z),
					new BlockElementFace(Direction.NORTH, 0, texture.toString(),
							new BlockFaceUV(
									new float[]{
											getMinU(), 16 - getMaxV(),
											getMaxU(), 16 - getMinV(),
											getMaxU(), 16 - getMinV(),
											getMinU(), 16 - getMaxV()
									}, 0
							)
					),
					Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(texture), face, BlockModelRotation.X0_Y0,
					new BlockElementRotation(new Vector3f(0, 0, 0), Direction.Axis.X, 0, false),
					true, new ResourceLocation("a:a")
			);
		}
	}
	
	public BakeableQuad rotate(Direction dir) {
		if (dir.equals(face)) {
			return this;
		} else if (dir.equals(face.getOpposite())) {
			return reverse().move(dir.getStepX() * 16, dir.getStepY() * 16, dir.getStepZ() * 16);
		} else if (isLeft(face, dir)) {
			min = new Vec3(min.z, min.y, min.x);
			max = new Vec3(max.z, max.y, max.x);
			if (face.equals(Direction.NORTH)) {
				face = dir;
			} else {
				this.face = Direction.fromYRot(dir.toYRot() - 90);
			}
			useZasU = !useZasU;
			return this;
		} else if (isRigt(face, dir)) {
			return rotate(dir.getOpposite()).rotate(face.getOpposite());
		} else if (dir.equals(Direction.DOWN)) {
			min = new Vec3(min.y, min.z, min.x);
			max = new Vec3(max.y, max.z, max.x);
			useZasV = !useZasV;
			this.face = dir;
			return this;
		} else if (dir.equals(Direction.UP)) {
			rotate(Direction.DOWN).reverse().move(0, 16, 0);
			this.face = dir;
			return this;
		}
		return this;
	}
	
	private static boolean isLeft(Direction dir1, Direction dir2) {
		return Direction.from2DDataValue(dir1.get2DDataValue() - 1).equals(dir2);
	}
	
	private static boolean isRigt(Direction dir1, Direction dir2) {
		return Direction.from2DDataValue(dir1.get2DDataValue() + 1).equals(dir2);
	}
}
