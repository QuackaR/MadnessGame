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

    public void render() {
        ChunkRenderer chunkRenderer = new ChunkRenderer();
        chunkRenderer.renderMesh(this);
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
