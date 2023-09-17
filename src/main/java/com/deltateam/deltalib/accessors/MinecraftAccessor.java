package com.deltateam.deltalib.accessors;

import com.mojang.datafixers.DataFixer;
import net.minecraft.client.Timer;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.server.level.progress.StoringChunkProgressListener;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.profiling.ProfilerFiller;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public interface MinecraftAccessor {

    float getPausePartialTicks();

    Timer getTimer();

    DataFixer getFixerUpper();

    ProfilerFiller getProfiler();

    FrameTimer getFrameTimer();

    AtomicReference<StoringChunkProgressListener> getStoringChunkProgressListener();

    void takePanoramicScreenshot(File file, int Width, int Height);

    void openChatScreenAction(String defaultText);
}
