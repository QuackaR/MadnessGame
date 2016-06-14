package de.krien.games.madness.view.hud.stats;

public class FpsUtil {

    private long lastFrameTime;
    private long frameCounter;
    private long framesPerSecond;

    public FpsUtil() {
        lastFrameTime = getTime();
    }


    public void updateFps() {
        if (getTime() - lastFrameTime > 1000) {
            framesPerSecond = frameCounter;
            frameCounter = 0;
            lastFrameTime += 1000;
        }
        frameCounter++;
    }

    private long getTime() {
        return System.nanoTime() / 1000000;
    }

    public long getFramesPerSecond() {
        return framesPerSecond;
    }
}
