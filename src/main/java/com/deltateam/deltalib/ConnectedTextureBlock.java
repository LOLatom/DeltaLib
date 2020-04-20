package com.deltateam.deltalib;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SixWayBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Util;

import java.util.Map;

public abstract class ConnectedTextureBlock extends Block {
    //TODO figure out block states
    public ConnectedTextureBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        super.fillStateContainer(p_206840_1_);
    }

    @Override
    public StateContainer<Block, BlockState> getStateContainer() {
        return super.getStateContainer();
    }

    BooleanProperty NORTH = BooleanProperty.create("NORTH");
    BooleanProperty SOUTH = BooleanProperty.create("SOUTH");
    BooleanProperty EAST = BooleanProperty.create("EAST");
    BooleanProperty WEST = BooleanProperty.create("WEST");
    BooleanProperty UP = BooleanProperty.create("UP");
    BooleanProperty DOWN = BooleanProperty.create("DOWN");
    BooleanProperty DOWNNORTH = BooleanProperty.create("NORTH_DOWN");
    BooleanProperty DOWNEAST = BooleanProperty.create("EAST_DOWN");
    BooleanProperty DOWNWEST = BooleanProperty.create("WEST_DOWN");
    BooleanProperty DOWNSOUTH = BooleanProperty.create("SOUTH_DOWN");
    BooleanProperty UPNORTH = BooleanProperty.create("NORTH_UP");
    BooleanProperty UPEAST = BooleanProperty.create("EAST_UP");
    BooleanProperty UPWEST = BooleanProperty.create("WEST_UP");
    BooleanProperty UPSOUTH = BooleanProperty.create("SOUTH_UP");
    BooleanProperty NORTHEAST = BooleanProperty.create("NORTH_EAST");
    BooleanProperty SOUTHEAST = BooleanProperty.create("SOUTH_EAST");
    BooleanProperty NORTHWEST = BooleanProperty.create("NORTH_WEST");
    BooleanProperty SOUTHWEST = BooleanProperty.create("SOUTH_WEST");
        Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = (Map) Util.make(Maps.newEnumMap(Direction.class), (p_203421_0_) -> {
            p_203421_0_.put(Direction.NORTH, NORTH);
            p_203421_0_.put(Direction.EAST, EAST);
            p_203421_0_.put(Direction.SOUTH, SOUTH);
            p_203421_0_.put(Direction.WEST, WEST);
            p_203421_0_.put(Direction.UP, UP);
            p_203421_0_.put(Direction.DOWN, DOWN);
            p_203421_0_.put(Direction.DOWNWEST, DOWNWEST);
            p_203421_0_.put(Direction.DOWNEAST, DOWNEAST);
            p_203421_0_.put(Direction.DOWNSOUTH, DOWNSOUTH);
            p_203421_0_.put(Direction.DOWNNORTH, DOWNNORTH);
            p_203421_0_.put(Direction.UPNORTH, UPNORTH);
            p_203421_0_.put(Direction.UPSOUTH, UPSOUTH);
            p_203421_0_.put(Direction.UPWEST, UPWEST);
            p_203421_0_.put(Direction.UPEAST, UPEAST);
            p_203421_0_.put(Direction.NORTHEAST, NORTHEAST);
            p_203421_0_.put(Direction.NORTHWEST, NORTHWEST);
            p_203421_0_.put(Direction.SOUTHEAST, SOUTHEAST);
            p_203421_0_.put(Direction.SOUTHWEST, SOUTHWEST);
        });
}
