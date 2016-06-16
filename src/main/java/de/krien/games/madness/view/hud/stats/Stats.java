package de.krien.games.madness.view.hud.stats;

import de.krien.games.madness.view.hud.GameObject2D;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

public class Stats implements GameObject2D {

    private Font font;
    private TrueTypeFont typeFont;

    private long lastFrameTime;
    private long frameCounter;
    private long framesPerSecond;

    public Stats() {
        font = new Font("Times New Roman", Font.BOLD, 16);
        typeFont = new TrueTypeFont(font, false);
        lastFrameTime = getTime();
    }

    public void draw() {
        drawFps();
    }

    private void drawFps() {
        updateFps();
        typeFont.drawString(5, 5, "FPS: " + getFramesPerSecond(), Color.black);
    }

    private void updateFps() {
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

    private long getFramesPerSecond() {
        return framesPerSecond;
    }
}
