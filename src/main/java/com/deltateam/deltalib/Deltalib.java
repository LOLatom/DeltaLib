package com.deltateam.deltalib;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod("deltalib")
public class Deltalib {
    public Deltalib() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
