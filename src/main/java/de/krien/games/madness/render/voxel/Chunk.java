package de.krien.games.madness.render.voxel;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector3f;

public class Chunk {

    static final int CHUNK_SIZE = 64;
    static final int CUBE_LENGTH = 2;
    private Block[][][] blocks;

    private int vboVertexHandle;
    private int vboColorHandle;

    private Vector3f position;

    public Chunk(int positionX, int positionY, int positionZ) {
        blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        this.position = new Vector3f(positionX, positionY, positionZ);
    }


    public void draw() {
        GL11.glPushMatrix();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboVertexHandle);
        GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboColorHandle);
        GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0L);
        GL11.glDrawArrays(GL11.GL_QUADS, 0, CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE * 24);
        GL11.glPopMatrix();
    }

    public void update() {
    }

    public static void enableLighting(boolean enabled) {
        GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT0);
    }

    public int getChunkSize() {
        return CHUNK_SIZE;
    }

    public int getCubeLength() {
        return CUBE_LENGTH;
    }

    public Block[][][] getBlocks() {
        return blocks;
    }

    public void setBlocks(Block[][][] blocks) {
        this.blocks = blocks;
    }

    public int getVboVertexHandle() {
        return vboVertexHandle;
    }

    public void setVboVertexHandle(int vboVertexHandle) {
        this.vboVertexHandle = vboVertexHandle;
    }

    public int getVboColorHandle() {
        return vboColorHandle;
    }

    public void setVboColorHandle(int vboColorHandle) {
        this.vboColorHandle = vboColorHandle;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }
}
