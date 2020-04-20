package com.deltateam.deltalib;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;
import org.apache.logging.log4j.Level;

public class testCTB extends ConnectedTextureBlock {
    public testCTB(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        try {
            builder.add(NORTH, EAST, SOUTH, WEST,
                    NORTHEAST,NORTHWEST,SOUTHEAST,SOUTHWEST,
                    UPNORTH,UPEAST,UPWEST,UPSOUTH,
                    DOWNEAST,DOWNWEST,DOWNSOUTH,DOWNNORTH,
                    UP, DOWN);
        } catch (Exception err) {
            super.fillStateContainer(builder);
            LOGGER.log(Level.FATAL,err.getMessage());
            LOGGER.log(Level.FATAL,err.getCause());
            for (StackTraceElement element:err.getStackTrace()) {
                LOGGER.log(Level.FATAL,element.getMethodName());
                LOGGER.log(Level.FATAL,element.getClassName());
                LOGGER.log(Level.FATAL,element.toString());
            }
        }
    }
}
