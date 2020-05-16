package com.deltateam.deltalib;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;

public class testCTB extends ConnectedTextureBlock {
    public testCTB(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        try {
            super.fillStateContainer(builder);
        } catch (Exception err) {
            LOGGER.log(Level.FATAL,err.getMessage());
            LOGGER.log(Level.FATAL,err.getCause());
            for (StackTraceElement element:err.getStackTrace()) {
                LOGGER.log(Level.FATAL,element.getMethodName());
                LOGGER.log(Level.FATAL,element.getClassName());
                LOGGER.log(Level.FATAL,element.toString());
            }
        }
    }
    
    @Override
    public ResourceLocation[] getTextureName() {
        return new ResourceLocation[]{
                new ResourceLocation("deltalib:block/ctbtest"),
                new ResourceLocation("deltalib:block/ctbtest")
        };
    }
}
