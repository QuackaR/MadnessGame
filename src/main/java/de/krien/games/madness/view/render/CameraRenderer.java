package de.krien.games.madness.view.render;

import de.krien.games.madness.view.camera.Camera;
import org.lwjgl.opengl.GL11;

public class CameraRenderer {

    private Camera camera;

    public CameraRenderer(Camera camera) {
        this.camera = camera;
    }

    public void update() {
        GL11.glLoadIdentity();
        GL11.glRotatef(camera.getRotationX(), 1.0f, 0.0f, 0.0f); //Yaw
        GL11.glRotatef(camera.getRotationY(), 0.0f, 1.0f, 0.0f); // Pitch
        GL11.glRotatef(camera.getRotationZ(), 0.0f, 0.0f, 1.0f); //Roll
        GL11.glTranslatef(camera.getPositionX(), camera.getPositionY(), camera.getPositionZ());
    }

}
