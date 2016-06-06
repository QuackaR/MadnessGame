package de.krien.games.madness.render;

import de.krien.games.madness.render.voxel.Chunk;
import de.krien.games.madness.render.voxel.util.ChunkRenderer;
import de.krien.games.madness.render.voxel.util.ChunkGenerator;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Renderer {

    private DisplayMode displayMode;

    private float cameraPositionX, cameraPositionY, cameraPositionZ;
    private float cameraRotationAngle, cameraRotationX, cameraRotationY, cameraRotationZ;

    public Renderer() {
        createWindow();
        initGL();
    }

    private void createWindow() {
        try {
            Display.setFullscreen(false);
            DisplayMode d[] = Display.getAvailableDisplayModes();
            for (int i = 0; i < d.length; i++) {
                if (d[i].getWidth() == 1280 && d[i].getHeight() == 960
                        && d[i].getBitsPerPixel() == 32) {
                    displayMode = d[i];
                    break;
                }
            }
            Display.setDisplayMode(displayMode);
            Display.setTitle("Madness - The Game");
            Display.create();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initGL() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1.0);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        GLU.gluPerspective(45.0f, (float) displayMode.getWidth()
                / (float) displayMode.getHeight(), 0.1f, 300.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
    }

    public void draw(Chunk worldChunk) {

            try {
                processInput();
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
                GL11.glLoadIdentity();

                GL11.glTranslatef(-30f + cameraPositionX, -40f + cameraPositionY, -160f + cameraPositionZ);
                GL11.glRotatef(0f + cameraRotationAngle, 0f  + cameraRotationX, 0f  + cameraRotationY, 0f  + cameraRotationZ);

                worldChunk.draw();
                Display.update();
                Display.sync(60);
            } catch (Exception e) {

            }

    }

    private void processInput() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            cameraPositionY--;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            cameraPositionY++;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            cameraPositionX++;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            cameraPositionX--;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            cameraPositionZ--;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            cameraPositionZ++;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD7)) {
            cameraRotationX-=0.1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)) {
            cameraRotationX+=0.1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4)) {
            cameraRotationY-=0.1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5)) {
            cameraRotationY+=0.1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1)) {
            cameraRotationZ-=0.1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) {
            cameraRotationZ+=0.1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            cameraRotationAngle++;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            cameraRotationAngle--;
        }
    }

}
