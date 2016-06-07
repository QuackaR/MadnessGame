package de.krien.games.madness.render.voxel;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.voxel.util.ChunkRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class Chunk {

    private Block[][][] blocks;

    private int vboVertexHandle;
    private int vboTextureHandle;

    public Chunk() {
        blocks = new Block[RenderConstants.CHUNK_SIZE][RenderConstants.CHUNK_SIZE][RenderConstants.CHUNK_SIZE];
    }

    public void draw() {
        GL11.glPushMatrix();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboVertexHandle);
        GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboTextureHandle);
        GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0L);

        GL11.glDrawArrays(GL11.GL_QUADS, 0, RenderConstants.CHUNK_BLOCK_COUNT * 24);
        GL11.glPopMatrix();
    }

    public void render() {
        ChunkRenderer chunkRenderer = new ChunkRenderer();
        chunkRenderer.renderMesh(this);
    }

    public void update() {
    }

    public Block[][][] getBlocks() {
        return blocks;
    }

    public int getVboVertexHandle() {
        return vboVertexHandle;
    }

    public void setVboVertexHandle(int vboVertexHandle) {
        this.vboVertexHandle = vboVertexHandle;
    }

    public int getVboTextureHandle() {
        return vboTextureHandle;
    }

    public void setVboTextureHandle(int vboTextureHandle) {
        this.vboTextureHandle = vboTextureHandle;
    }
}
