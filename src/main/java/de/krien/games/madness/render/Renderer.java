package de.krien.games.madness.render;

import de.krien.games.madness.render.voxel.Chunk;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.glu.GLU;

import java.nio.FloatBuffer;

public class Renderer {

    private DisplayMode displayMode;
    private int VBOVertexHandle;
    private int VBOColorHandle;

    private Chunk testChunk;
    private float PX, PY, PZ;

    public Renderer() {
        createWindow();
        initGL();
    }

    private void createWindow() {
        try {
            Display.setFullscreen(false);
            DisplayMode d[] = Display.getAvailableDisplayModes();
            for (int i = 0; i < d.length; i++) {
                if (d[i].getWidth() == 640 && d[i].getHeight() == 480
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


    public void run() {
        testChunk = new Chunk(0, 0, 0);

        float rotateYaw = 1;
        while (!Display.isCloseRequested()) {
            try {
                processInput();
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
                GL11.glLoadIdentity();

                GL11.glTranslatef(-30f + PX, -40f + PY, -160f + PZ);
                GL11.glRotatef(45f, 0.4f, 1.0f, 0.1f);
                GL11.glRotatef(45f, 0f, 1.0f, 0f);

                /* Re-Build a random chunk every 60 loops
                rotateYaw += 1;
                if (rotateYaw % 60 == 0) {
                    testChunk.rebuildMesh(0, 0, 0);
                } */

                testChunk.draw();
                Display.update();
                Display.sync(60);
            } catch (Exception e) {

            }
        }
        Display.destroy();
    }

    private void processInput() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            PY--;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            PY++;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            PX++;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            PX--;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            PZ--;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            PZ++;
        }
    }

    private void drawVBO() {
        GL11.glPushMatrix();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOVertexHandle);
        GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOColorHandle);
        GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0L);
        GL11.glDrawArrays(GL11.GL_QUADS, 0, 24);
        GL11.glPopMatrix();
    }

    private void createVBO() {
        VBOColorHandle = GL15.glGenBuffers();
        VBOVertexHandle = GL15.glGenBuffers();
        FloatBuffer VertexPositionData = BufferUtils.createFloatBuffer(24 * 3);
        VertexPositionData.put(new float[]{1.0f, 1.0f, -1.0f, -1.0f, 1.0f,
                -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,

                1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,

                1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f,
                -1.0f, 1.0f,

                1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f,
                1.0f, 1.0f, -1.0f,

                -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f, 1.0f,

                1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f,
                -1.0f, -1.0f});
        VertexPositionData.flip();
        FloatBuffer VertexColorData = BufferUtils.createFloatBuffer(24 * 3);
        VertexColorData.put(new float[]{1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1,
                1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1,
                0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1,
                0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1,});
        VertexColorData.flip();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOVertexHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertexPositionData,
                GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOColorHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertexColorData,
                GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();
        GL11.glTranslatef(-20f, 0.0f, -100f);
        GL11.glRotatef(45f, 0.4f, 1.0f, 0.1f);
        GL11.glColor3f(0.5f, 0.5f, 1.0f);

        int cubeSize = 20;
        float decrement = cubeSize * -2;

        for (int x = 0; x < cubeSize; x++) {
            for (int y = 0; y < cubeSize; y++) {
                for (int z = 0; z < cubeSize; z++) {
                    renderCube();
                    GL11.glTranslatef(0f, 0.0f, 2f);
                }
                GL11.glTranslatef(0f, 2f, decrement);
            }
            GL11.glTranslatef(2f, decrement, 0);
        }
    }

    private void renderCube() {
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glColor3f(0.0f, 1.0f, 0.0f);
        GL11.glVertex3f(1.0f, 1.0f, -1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, -1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(1.0f, 1.0f, 1.0f);
        GL11.glColor3f(1.0f, 0.5f, 0.0f);
        GL11.glVertex3f(1.0f, -1.0f, 1.0f);
        GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
        GL11.glVertex3f(-1.0f, -1.0f, -1.0f);
        GL11.glVertex3f(1.0f, -1.0f, -1.0f);
        GL11.glColor3f(1.0f, 0.0f, 0.0f);
        GL11.glVertex3f(1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
        GL11.glVertex3f(1.0f, -1.0f, 1.0f);
        GL11.glColor3f(1.0f, 1.0f, 0.0f);
        GL11.glVertex3f(1.0f, -1.0f, -1.0f);
        GL11.glVertex3f(-1.0f, -1.0f, -1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, -1.0f);
        GL11.glVertex3f(1.0f, 1.0f, -1.0f);
        GL11.glColor3f(0.0f, 0.0f, 1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, -1.0f);
        GL11.glVertex3f(-1.0f, -1.0f, -1.0f);
        GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
        GL11.glColor3f(1.0f, 0.0f, 1.0f);
        GL11.glVertex3f(1.0f, 1.0f, -1.0f);
        GL11.glVertex3f(1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(1.0f, -1.0f, 1.0f);
        GL11.glVertex3f(1.0f, -1.0f, -1.0f);
        GL11.glEnd();
    }

}
