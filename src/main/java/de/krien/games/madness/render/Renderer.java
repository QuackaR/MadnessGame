package de.krien.games.madness.render;

import de.krien.games.madness.render.debug.CameraSight;
import de.krien.games.madness.render.hud.stats.FpsHudRenderer;
import de.krien.games.madness.render.voxel.Chunk;
import de.krien.games.madness.render.voxel.World;
import de.krien.games.madness.render.voxel.util.texture.TextureUtil;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.glu.GLU;

public class Renderer {

    private DisplayMode displayMode;
    private FpsHudRenderer fpsHudRenderer;

    public Renderer() {
        createWindow();
        initGL();

        fpsHudRenderer = new FpsHudRenderer();
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
        //GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); //White Background
        GL11.glClearDepth(1.0);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(45.0f, (float) displayMode.getWidth() / (float) displayMode.getHeight(), 0.1f, RenderConstants.VIEW_DISTANCE);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        TextureUtil.BORDERLESS.bind();
    }

    public void draw(World world) {
        try {
            clearMatrix();
            drawMatrix(world);
            if (CameraSight.INSTANCE.shouldDrawCameraSight()) {
                CameraSight.INSTANCE.drawCameraSight();
            }
            //RayPick.BORDERLESS.updateSelection();
            updateCamera(world);
            fpsHudRenderer.drawFps();

            Display.update();
            Display.sync(RenderConstants.MAX_FPS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearMatrix() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    private void updateCamera(World world) {
        GL11.glLoadIdentity();
        GL11.glRotatef(world.getCamera().getRotationX(), 1.0f, 0.0f, 0.0f); //Yaw
        GL11.glRotatef(world.getCamera().getRotationY(), 0.0f, 1.0f, 0.0f); // Pitch
        GL11.glRotatef(world.getCamera().getRotationZ(), 0.0f, 0.0f, 1.0f); //Roll
        GL11.glTranslatef(world.getCamera().getPositionX(), world.getCamera().getPositionY(), world.getCamera().getPositionZ());
    }

    private void drawMatrix(World world) {
        Chunk[][] chunks = world.getChunks();
        for (int x = 0; x < chunks.length; x++) {
            for (int y = 0; y < chunks[0].length; y++) {
                Chunk chunk = chunks[x][y];
                GL11.glPushMatrix();
                GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, chunk.getVboVertexHandle());
                GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);
                GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, chunk.getVboTextureHandle());
                GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0L);
                GL11.glDrawArrays(GL11.GL_QUADS, 0, RenderConstants.CHUNK_BLOCK_COUNT * 24);
                GL11.glPopMatrix();
            }
        }
    }
}
