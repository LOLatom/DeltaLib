package com.deltateam.deltalib;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.IForgeBakedModel;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public abstract class ConnectedTextureBlock extends Block {
    //TODO figure out block states
    public ConnectedTextureBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
//        builder.add(
//                DOWN/*,DOWNSOUTH,DOWNWEST,DOWNEAST,DOWNNORTH*/,
//                UP/*,UPSOUTH,UPEAST,UPNORTH,UPWEST*/,
//                NORTHWEST,NORTH,NORTHEAST,
//                SOUTHWEST,SOUTH,SOUTHEAST,
//                WEST,EAST
//            );
    }
    
    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block changedBlock, BlockPos changedBlockPos, boolean isMoving) {
//        if (changedBlockPos.equals(pos.up())) {
//            world.setBlockState(pos, state.with(UP, changedBlock.getBlock() instanceof ConnectedTextureBlock));
//        } else if (changedBlockPos.equals(pos.offset(Direction.DOWN))) {
//            world.setBlockState(pos,state.with(DOWN,changedBlock.getBlock() instanceof ConnectedTextureBlock));
//        }
        super.neighborChanged(state, world, pos, changedBlock, changedBlockPos, isMoving);
    }
    
    public abstract ResourceLocation[] getTextureName();
    
    @OnlyIn(Dist.CLIENT)
    public static class Model implements IBakedModel {
        IBakedModel original;
        TextureAtlasSprite spriteBorder;
        TextureAtlasSprite spriteBase;
        ModelProperty property=new ModelProperty<WorldSurroundings>();
    
        public Model(IBakedModel original) {
            this.original = original;
            this.spriteBorder = original.getParticleTexture();
            this.spriteBase = null;
        }
    
        ResourceLocation base;
        ResourceLocation frame;
        @Nonnull
        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
            base=((ConnectedTextureBlock)state.getBlock()).getTextureName()[1];
            frame=new ResourceLocation(base.toString()+"_frame");
//            if (spriteBase==null) {
//                for (BakedQuad qd:original.getQuads(state,null,null)) {
//                    if (qd.hasTintIndex()) {
////                        spriteBorder=qd.func_187508_a();
//                    } else {
////                        spriteBase=qd.func_187508_a();
//                    }
//                }
//            }
//            spriteBorder=Minecraft.getInstance().getModelManager().getAtlasTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).getSprite(frame);
            WorldSurroundings surroundings=(WorldSurroundings) extraData.getData(property);
            ArrayList<BakedQuad> quads=new ArrayList<>();
            for (BakedQuad quad:original.getQuads(state,side,rand)) {
                if (side.equals(Direction.DOWN)||side.equals(Direction.UP)&&quad.getTintIndex()==0) {
                    try {
//                        quads.add(quad);
//                        BakedQuad newQuad=BakedQuadRetextured.retexture(quad,spriteBase);
                        BakedQuad newQuad=new BakedQuad(
                                quad.getVertexData(),
                                0,
                                quad.getFace(),
                                spriteBase,
                                true
                        );
                        if (!surroundings.west) {
                            quads.add(newQuad);
                        }
                    } catch (Exception err) {}
                } else {
//                    quads.add(quad);
                }
            }
            return quads;
        }
    
        @Nonnull
        @Override
        public IModelData getModelData(@Nonnull ILightReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
            if (tileData!=null) {
                tileData=new IModelData() {
                    HashMap<BlockPos,Object> properties=new HashMap<>();
                    @Override
                    public boolean hasProperty(ModelProperty<?> prop) {
                        return properties.containsKey(prop);
                    }
        
                    @Nullable
                    @Override
                    public <T> T getData(ModelProperty<T> prop) {
                        return (T)properties.get(prop);
                    }
        
                    @Nullable
                    @Override
                    public <T> T setData(ModelProperty<T> prop, T data) {
                        properties.put(pos,data);
                        return data;
                    }
                };
            }
            tileData.setData(property,new WorldSurroundings(world,pos,state));
            return tileData;
        }
    
        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
            return null;
        }
    
        @Override
        public boolean isAmbientOcclusion() {
            return false;
        }
    
        @Override
        public boolean isGui3d() {
            return false;
        }
    
        @Override
        public boolean func_230044_c_() {
            return false;
        }
    
        @Override
        public boolean isBuiltInRenderer() {
            return false;
        }
    
        @Override
        public TextureAtlasSprite getParticleTexture() {
            spriteBase=Minecraft.getInstance().getModelManager().getAtlasTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).getSprite(frame);
            return spriteBase;
        }
    
        @Override
        public ItemOverrideList getOverrides() {
            return null;
        }
    }
    
    @Override
    public void observedNeighborChange(BlockState observerState, World world, BlockPos observerPos, Block changedBlock, BlockPos changedBlockPos) {
    }
    
    @Override
    public StateContainer<Block, BlockState> getStateContainer() {
        return super.getStateContainer();
    }
    
    public static BooleanProperty NORTH = BooleanProperty.create("north");
    public static BooleanProperty SOUTH = BooleanProperty.create("south");
    public static BooleanProperty EAST = BooleanProperty.create("east");
    public static BooleanProperty WEST = BooleanProperty.create("west");
    public static BooleanProperty UP = BooleanProperty.create("up");
    public static BooleanProperty DOWN = BooleanProperty.create("down");
    public static BooleanProperty DOWNNORTH = BooleanProperty.create("northdown");
    public static BooleanProperty DOWNEAST = BooleanProperty.create("eastdown");
    public static BooleanProperty DOWNWEST = BooleanProperty.create("westdown");
    public static BooleanProperty DOWNSOUTH = BooleanProperty.create("southdown");
    public static BooleanProperty UPNORTH = BooleanProperty.create("nothup");
    public static BooleanProperty UPEAST = BooleanProperty.create("eastup");
    public static BooleanProperty UPWEST = BooleanProperty.create("westup");
    public static BooleanProperty UPSOUTH = BooleanProperty.create("southup");
    public static BooleanProperty NORTHEAST = BooleanProperty.create("northeast");
    public static BooleanProperty SOUTHEAST = BooleanProperty.create("southeast");
    public static BooleanProperty NORTHWEST = BooleanProperty.create("northwest");
    public static BooleanProperty SOUTHWEST = BooleanProperty.create("southwest");
//        Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = (Map) Util.make(Maps.newEnumMap(Direction.class), (p_203421_0_) -> {
//            p_203421_0_.put(Direction.NORTH, NORTH);
//            p_203421_0_.put(Direction.EAST, EAST);
//            p_203421_0_.put(Direction.SOUTH, SOUTH);
//            p_203421_0_.put(Direction.WEST, WEST);
//            p_203421_0_.put(Direction.UP, UP);
//            p_203421_0_.put(Direction.DOWN, DOWN);
//            p_203421_0_.put(Direction.DOWNWEST, DOWNWEST);
//            p_203421_0_.put(Direction.DOWNEAST, DOWNEAST);
//            p_203421_0_.put(Direction.DOWNSOUTH, DOWNSOUTH);
//            p_203421_0_.put(Direction.DOWNNORTH, DOWNNORTH);
//            p_203421_0_.put(Direction.UPNORTH, UPNORTH);
//            p_203421_0_.put(Direction.UPSOUTH, UPSOUTH);
//            p_203421_0_.put(Direction.UPWEST, UPWEST);
//            p_203421_0_.put(Direction.UPEAST, UPEAST);
//            p_203421_0_.put(Direction.NORTHEAST, NORTHEAST);
//            p_203421_0_.put(Direction.NORTHWEST, NORTHWEST);
//            p_203421_0_.put(Direction.SOUTHEAST, SOUTHEAST);
//            p_203421_0_.put(Direction.SOUTHWEST, SOUTHWEST);
//        });
}
