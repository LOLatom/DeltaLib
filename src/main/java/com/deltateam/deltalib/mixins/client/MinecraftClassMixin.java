package com.deltateam.deltalib.mixins.client;

import com.deltateam.deltalib.accessors.MinecraftAccessor;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Minecraft.class)
public abstract class MinecraftClassMixin implements MinecraftAccessor {


    @Shadow private float pausePartialTick;

    @Shadow protected abstract void openChatScreen(String pDefaultText);

    @Override
    public float getPausePartialTicks() {
        return pausePartialTick;
    }

    @Override
    public void openChatScreenAction(String defaultText) {
        openChatScreen(defaultText);
    }


}
