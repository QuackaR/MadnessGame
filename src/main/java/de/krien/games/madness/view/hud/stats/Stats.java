package de.krien.games.madness.view.hud.stats;

import de.krien.games.madness.view.camera.Camera;
import de.krien.games.madness.view.hud.GameObject2D;
import org.lwjgl.util.vector.Vector3f;
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
        drawCameraPosition();
        drawCameraRotation();
    }

    private void drawFps() {
        updateFps();
        typeFont.drawString(5, 5, "FPS: " + getFramesPerSecond(), Color.black);
    }

    private void drawCameraPosition() {
        Vector3f position = Camera.INSTANCE.getPosition();
        float positionX = Math.round(position.getX()* 100f) / 100f;
        float positionY = Math.round(position.getY()* 100f) / 100f;
        float positionZ = Math.round(position.getZ()* 100f) / 100f;
        typeFont.drawString(5, 25, "Camera Position: " + positionX + "/" + positionY + "/" + positionZ, Color.black);
    }


    private void drawCameraRotation() {
        Vector3f rotation = Camera.INSTANCE.getRotation();
        float rotationX = Math.round(rotation.getX()* 100f) / 100f;
        float rotationY = Math.round(rotation.getY()* 100f) / 100f;
        float rotationZ = Math.round(rotation.getZ()* 100f) / 100f;
        typeFont.drawString(5, 45, "Camera Position: " + rotationX + "/" + rotationY + "/" + rotationZ, Color.black);
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
