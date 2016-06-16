package de.krien.games.madness.view.hud.crosshair;

import de.krien.games.madness.view.hud.GameObject2D;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector2f;

import java.nio.FloatBuffer;

public class Crosshair implements GameObject2D {

    private int crosshairId;

    private int crosshairSize;
    private int distanceFromCenter;

    public Crosshair(int crosshairSize, int distanceFromCenter) {
        this.crosshairSize = crosshairSize;
        this.distanceFromCenter = distanceFromCenter;
        crosshairId = calculate();
    }

    public void draw() {
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.5F); //Replace with FloatBuffer and GL11.glColorPointer();
        GL11.glPushMatrix();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, crosshairId);
        GL11.glVertexPointer(2, GL11.GL_FLOAT, 0, 0L);
        GL11.glDrawArrays(GL11.GL_QUADS, 0, 16);
        GL11.glPopMatrix();
    }


    private int calculate() {
        Vector2f center = getDisplayCenter();

        float[] crosshairData = {
                center.getX() - 1, center.getY() - crosshairSize, center.getX() + 1, center.getY() - crosshairSize, center.getX() + 1, center.getY() - distanceFromCenter, center.getX() - 1, center.getY() - distanceFromCenter, // Top-to-Center
                center.getX() - 1, center.getY() + distanceFromCenter, center.getX() + 1, center.getY() + distanceFromCenter, center.getX() + 1, center.getY() + crosshairSize, center.getX() - 1, center.getY() + crosshairSize, // Bottom-to-Center
                center.getX() - crosshairSize, center.getY() - 1, center.getX() - distanceFromCenter, center.getY() - 1, center.getX() - distanceFromCenter, center.getY() + 1, center.getX() - crosshairSize, center.getY() + 1, // Left-to-Center
                center.getX() + distanceFromCenter, center.getY() - 1, center.getX() + crosshairSize, center.getY() - 1, center.getX() + crosshairSize, center.getY() + 1, center.getX() + distanceFromCenter, center.getY() + 1 // Right-to-Center
        };

        FloatBuffer crosshair = BufferUtils.createFloatBuffer(crosshairData.length);
        crosshair.put(crosshairData);
        crosshair.flip();

        crosshairId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, crosshairId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, crosshair, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return crosshairId;
    }

    private Vector2f getDisplayCenter() {
        float centerX = Display.getDisplayMode().getWidth() / 2;
        float centerY = Display.getDisplayMode().getHeight() / 2;
        return new Vector2f(centerX, centerY);
    }

}
