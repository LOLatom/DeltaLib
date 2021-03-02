package com.deltateam.deltalib.API.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class BakeableQuad {
	private static final FaceBakery bakery = new FaceBakery();
	
	public Vector3d min, max;
	public Direction face;
	public ResourceLocation texture;
	
	boolean useZasU = false;
	boolean useZasV = false;
	
	public BakeableQuad(Vector3d min, Vector3d max, Direction face, ResourceLocation texture) {
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
		this.min = new Vector3d(maxX, maxY, maxZ);
		this.max = new Vector3d(minX, minY, minZ);
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
					new BlockPartFace(Direction.NORTH, 0, texture.toString(),
							new BlockFaceUV(
									new float[]{
											getMinU(), getMinV(),
											getMaxU(), getMaxV(),
											getMaxU(), getMinV(),
											getMinU(), getMaxV()
									}, 0
							)
					),
					Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(texture), face, ModelRotation.X0_Y0,
					new BlockPartRotation(new Vector3f(0, 0, 0), Direction.Axis.X, 0, false),
					true, new ResourceLocation("a:a")
			);
		} else if (face.equals(Direction.NORTH) || face.equals(Direction.EAST)) {
			return bakery.bakeQuad(new Vector3f((float) min.x, (float) min.y, (float) min.z), new Vector3f((float) max.x, (float) max.y, (float) max.z),
					new BlockPartFace(Direction.NORTH, 0, texture.toString(),
							new BlockFaceUV(
									new float[]{
											getMaxU(), 16-getMaxV(),
											getMinU(), 16-getMinV(),
											getMaxU(), 16-getMinV(),
											getMinU(), 16-getMaxV()
									}, 0
							)
					),
					Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(texture), face, ModelRotation.X0_Y0,
					new BlockPartRotation(new Vector3f(0, 0, 0), Direction.Axis.X, 0, false),
					true, new ResourceLocation("a:a")
			);
		} else {
			return bakery.bakeQuad(new Vector3f((float) min.x, (float) min.y, (float) min.z), new Vector3f((float) max.x, (float) max.y, (float) max.z),
					new BlockPartFace(Direction.NORTH, 0, texture.toString(),
							new BlockFaceUV(
									new float[]{
											getMinU(), 16-getMaxV(),
											getMaxU(), 16-getMinV(),
											getMaxU(), 16-getMinV(),
											getMinU(), 16-getMaxV()
									}, 0
							)
					),
					Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(texture), face, ModelRotation.X0_Y0,
					new BlockPartRotation(new Vector3f(0, 0, 0), Direction.Axis.X, 0, false),
					true, new ResourceLocation("a:a")
			);
		}
	}
	
	public BakeableQuad rotate(Direction dir) {
		if (dir.equals(face)) {
			return this;
		} else if (dir.equals(face.getOpposite())) {
			return reverse().move(dir.getXOffset()*16,dir.getYOffset()*16,dir.getZOffset()*16);
		} else if (isLeft(face,dir)) {
			min = new Vector3d(min.z,min.y,min.x);
			max = new Vector3d(max.z,max.y,max.x);
			if (face.equals(Direction.NORTH)) {
				face = dir;
			} else {
				this.face = Direction.fromAngle(dir.getHorizontalAngle() - 90);
			}
			useZasU = !useZasU;
			return this;
		} else if (isRigt(face,dir)) {
			return rotate(dir.getOpposite()).rotate(face.getOpposite());
		} else if (dir.equals(Direction.DOWN)) {
			min = new Vector3d(min.y,min.z,min.x);
			max = new Vector3d(max.y,max.z,max.x);
			useZasV = !useZasV;
			this.face = dir;
			return this;
		} else if (dir.equals(Direction.UP)) {
			rotate(Direction.DOWN).reverse().move(0,16,0);
			this.face = dir;
			return this;
		}
		return this;
	}
	
	private static boolean isLeft(Direction dir1, Direction dir2) {
		return Direction.byHorizontalIndex(dir1.getHorizontalIndex() - 1).equals(dir2);
	}
	
	private static boolean isRigt(Direction dir1, Direction dir2) {
		return Direction.byHorizontalIndex(dir1.getHorizontalIndex() + 1).equals(dir2);
	}
}
