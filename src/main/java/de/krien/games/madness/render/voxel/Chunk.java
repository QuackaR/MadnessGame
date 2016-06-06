package de.krien.games.madness.render.voxel;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class Chunk {

    static final int CHUNK_SIZE = 64;
    static final int CUBE_LENGTH = 2;
    private Block[][][] blocks;

    private int vboVertexHandle;
    private int vboColorHandle;

    private int positionX;
    private int positionY;
    private int positionZ;

    public Chunk(int positionX, int positionY, int positionZ) {
        blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;
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

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getPositionZ() {
        return positionZ;
    }

    public void setPositionZ(int positionZ) {
        this.positionZ = positionZ;
    }
}
