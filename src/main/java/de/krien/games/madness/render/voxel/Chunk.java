package de.krien.games.madness.render.voxel;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.voxel.util.chunk.ChunkCalculator;
import org.lwjgl.util.vector.Vector2f;

public class Chunk {

    private Block[][][] blocks;

    private int vboVertexHandle;
    private int vboTextureHandle;

    private Vector2f position;

    public Chunk(Vector2f position) {
        blocks = new Block[RenderConstants.CHUNK_SIZE][RenderConstants.CHUNK_SIZE][RenderConstants.CHUNK_SIZE];
        this.position = position;
    }

    public void render() {
        ChunkCalculator chunkCalculator = new ChunkCalculator(this);
        chunkCalculator.render();
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

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }
}
