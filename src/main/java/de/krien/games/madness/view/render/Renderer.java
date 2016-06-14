package de.krien.games.madness.view.render;

import de.krien.games.madness.view.voxel.World;
import de.krien.games.madness.view.voxel.util.texture.Texture;
import de.krien.games.madness.view.voxel.util.texture.TextureManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Renderer {

    private Renderer2D renderer2D;
    private Renderer3D renderer3D;

    public Renderer() {
        createWindow();
        initGL();

        renderer2D = new Renderer2D();
        renderer3D = new Renderer3D();
    }

    private void createWindow() {
        try {
            Display.setFullscreen(false);
            DisplayMode displayMode = new DisplayMode(1280, 960);
            //DisplayMode d[] = Display.getAvailableDisplayModes();
            /*for (int i = 0; i < d.length; i++) {
                if (d[i].getWidth() == 1280 && d[i].getHeight() == 960
                        && d[i].getBitsPerPixel() == 32) {
                    displayMode = d[i];
                    break;
                }
            }*/
            Display.setDisplayMode(displayMode);
            Display.setTitle("Madness - The Game");
            Display.create();
            //Mouse.setGrabbed(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initGL() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); //White Background
        GL11.glClearDepth(1.0);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(45.0f, (float) Display.getDisplayMode().getWidth() / (float) Display.getDisplayMode().getHeight(), 0.1f, RenderConstants.VIEW_DISTANCE);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        TextureManager.INSTANCE.setActiveTexture(Texture.BORDERLESS);
    }

    public void draw(World world) {
        clearDisplay();
        renderer3D.draw(world);
        renderer2D.draw(world);
        updateDisplay();
    }

    private void clearDisplay() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    private void updateDisplay() {
        try {
            Display.update();
            Display.sync(RenderConstants.MAX_FPS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
