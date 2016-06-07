package de.krien.games.madness.render;

import de.krien.games.madness.render.voxel.Chunk;
import de.krien.games.madness.render.voxel.World;
import de.krien.games.madness.render.voxel.util.ChunkRenderer;
import de.krien.games.madness.render.voxel.util.ChunkGenerator;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Renderer {

    private DisplayMode displayMode;

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

    public void draw(World world) {
        try {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glLoadIdentity();

            GL11.glRotatef(world.getCamera().getRotationX(), 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(world.getCamera().getRotationY(), 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(world.getCamera().getRotationZ(), 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-30f + world.getCamera().getPositionX(), -40f + world.getCamera().getPositionY(), -160f + world.getCamera().getPositionZ());


            world.getChunk().draw();
            Display.update();
            Display.sync(60);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
