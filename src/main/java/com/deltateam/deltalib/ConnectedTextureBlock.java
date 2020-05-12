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

    BooleanProperty NORTH = BooleanProperty.create("north");
    BooleanProperty SOUTH = BooleanProperty.create("south");
    BooleanProperty EAST = BooleanProperty.create("east");
    BooleanProperty WEST = BooleanProperty.create("west");
    BooleanProperty UP = BooleanProperty.create("up");
    BooleanProperty DOWN = BooleanProperty.create("down");
    BooleanProperty DOWNNORTH = BooleanProperty.create("north_down");
    BooleanProperty DOWNEAST = BooleanProperty.create("east_down");
    BooleanProperty DOWNWEST = BooleanProperty.create("west_down");
    BooleanProperty DOWNSOUTH = BooleanProperty.create("south_down");
    BooleanProperty UPNORTH = BooleanProperty.create("noth_up");
    BooleanProperty UPEAST = BooleanProperty.create("east_up");
    BooleanProperty UPWEST = BooleanProperty.create("west_up");
    BooleanProperty UPSOUTH = BooleanProperty.create("south_up");
    BooleanProperty NORTHEAST = BooleanProperty.create("north_east");
    BooleanProperty SOUTHEAST = BooleanProperty.create("south_east");
    BooleanProperty NORTHWEST = BooleanProperty.create("north_west");
    BooleanProperty SOUTHWEST = BooleanProperty.create("south_west");
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
