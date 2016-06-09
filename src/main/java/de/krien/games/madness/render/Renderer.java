package de.krien.games.madness.render;

import de.krien.games.madness.render.ray.Ray;
import de.krien.games.madness.render.voxel.Chunk;
import de.krien.games.madness.render.voxel.World;
import de.krien.games.madness.render.voxel.util.block.BlockRenderer;
import de.krien.games.madness.render.voxel.util.texture.TextureUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.glu.GLU;

import javax.vecmath.Vector3f;
import java.nio.FloatBuffer;

public class Renderer {

    private DisplayMode displayMode;
    private FpsHudRenderer fpsHudRenderer;

    //RayTrace
    int rayPosId;
    int rayTexId;
    FloatBuffer rayPosBuffer;
    FloatBuffer rayTexBuffer;


    public Renderer() {
        createWindow();
        initGL();

        fpsHudRenderer = new FpsHudRenderer();

        rayPosId = GL15.glGenBuffers();
        rayTexId = GL15.glGenBuffers();
        rayPosBuffer = BufferUtils.createFloatBuffer(150*6*12);
        rayTexBuffer = BufferUtils.createFloatBuffer(150*6*12);
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
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(45.0f, (float) displayMode.getWidth() / (float) displayMode.getHeight(), 0.1f, RenderConstants.VIEW_DISTANCE);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
    }

    public void draw(World world) {
        try {
            clearMatrix();
            updateCamera(world);
            fpsHudRenderer.drawFps();

            drawMatrix(world);

            Display.update();
            Display.sync(RenderConstants.MAX_FPS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void addRayTrace(World world) {
        Vector3f origin = new Vector3f(world.getCamera().getPosition().getX(), world.getCamera().getPosition().getY(), world.getCamera().getPosition().getZ());
        Vector3f direction = new Vector3f(world.getCamera().getRotation().getX(), world.getCamera().getRotation().getY(), world.getCamera().getRotation().getZ());
        Ray ray = new Ray(origin, direction);

        rayPosBuffer.clear();
        rayTexBuffer.clear();

        System.out.println("Ray Origin: " + ray.origin.x + ":" + ray.origin.y + ":" + ray.origin.z);
        System.out.println("Ray Direction: " + ray.direction.x + ":" + ray.direction.y + ":" + ray.direction.z);
        for(float i = 0.1f; i < RenderConstants.VIEW_DISTANCE; i+=2.0f) {
            Vector3f renderPos = ray.getEndPoint(i);
            BlockRenderer blockRenderer = new BlockRenderer(renderPos.x, renderPos.y, renderPos.z - 50, 0, rayPosBuffer, rayTexBuffer);
            blockRenderer.renderBlock();

            System.out.println("Block Coords. at Distance " + i + ": " + renderPos.x+ ": " + renderPos.y+ ": " + renderPos.z);
        }

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, rayPosId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, rayPosBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, rayTexId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, rayTexBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
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
        TextureUtil.INSTANCE.bind();
        Chunk[][] chunks = world.getChunks();
        for(int x = 0; x < chunks.length; x++) {
            for(int y = 0; y < chunks[0].length; y++) {
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
