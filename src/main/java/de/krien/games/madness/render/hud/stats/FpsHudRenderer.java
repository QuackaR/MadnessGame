package de.krien.games.madness.render.hud.stats;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.lwjgl.opengl.Display;

public class FpsHudRenderer {

    private long lastFrame;
    private long lastFPS;
    private long fps;

    public FpsHudRenderer() {
        lastFPS = getTime();
    }


    public void drawFps() {
        if (getTime() - lastFPS > 100) {
            Display.setTitle("Madness - The Game" + " - FPS: " + fps);
            fps = 0; //reset the FPS counter
            lastFPS += 1000; //add one second
        }
        fps++;
    }

    private long getTime() {
        return System.nanoTime() / 1000000;
    }

    private int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }
}
