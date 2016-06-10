package de.krien.games.madness.render.voxel;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.voxel.util.chunk.ChunkCalculator;

public class Chunk {

    private Block[][][] blocks;

    private int vboVertexHandle;
    private int vboTextureHandle;

    public Chunk() {
        blocks = new Block[RenderConstants.CHUNK_SIZE][RenderConstants.CHUNK_SIZE][RenderConstants.CHUNK_SIZE];
    }

    public void render(int x, int y) {
        ChunkCalculator chunkCalculator = new ChunkCalculator(this);
        chunkCalculator.render(x, y);
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
