package com.deltateam.deltalib.mixins.client;

import com.deltateam.deltalib.accessors.MinecraftAccessor;
import com.mojang.datafixers.DataFixer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Timer;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.progress.StoringChunkProgressListener;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

@Mixin(Minecraft.class)
public abstract class MinecraftClassMixin implements MinecraftAccessor {

    @Shadow private float pausePartialTick;

    @Shadow protected abstract void openChatScreen(String pDefaultText);

    @Shadow @Final private Tutorial tutorial;

    @Shadow @Final private Timer timer;

    @Shadow @Final public FrameTimer frameTimer;

    @Shadow @Final private DataFixer fixerUpper;

    @Shadow private ProfilerFiller profiler;

    @Shadow public abstract Component grabPanoramixScreenshot(File p_167900_, int pWidth, int pHeight);

    @Shadow @Final private AtomicReference<StoringChunkProgressListener> progressListener;

    @Override
    public float getPausePartialTicks() {
        return pausePartialTick;
    }

    @Override
    public Timer getTimer() {
        return timer;
    }

    @Override
    public FrameTimer getFrameTimer() {
        return frameTimer;
    }

    @Override
    public DataFixer getFixerUpper() {
        return fixerUpper;
    }

    @Override
    public ProfilerFiller getProfiler() {
        return profiler;
    }

    @Override
    public AtomicReference<StoringChunkProgressListener> getStoringChunkProgressListener() {
        return progressListener;
    }

    @Override
    public void takePanoramicScreenshot(File file, int width, int height) {
        grabPanoramixScreenshot(file,width,height);
    }

    @Override
    public void openChatScreenAction(String defaultText) {
        openChatScreen(defaultText);
    }


}
