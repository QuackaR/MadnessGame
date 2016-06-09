package de.krien.games.madness.render.voxel.util.chunk;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.voxel.Block;
import de.krien.games.madness.render.voxel.Chunk;
import de.krien.games.madness.render.voxel.util.block.BlockEnvironmentUtil;
import de.krien.games.madness.render.voxel.util.block.BlockRenderer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;

public class ChunkRenderer {

    private Chunk chunk;

    private FloatBuffer vertexPositionData;
    private FloatBuffer vertexTextureData;

    private float chunkOffsetX;
    private float chunkOffsetY;

    public ChunkRenderer(Chunk chunk) {
        this.chunk = chunk;
    }

    public void render(int chunkX, int chunkY) {
        initBuffers(chunk);
        initVertexBuffer();
        calculateChunkOffset(chunkX, chunkY);

        renderChunk();

        flipVertexBuffer();
        bindVertexBuffer(chunk);
    }

    private void renderChunk() {
        for (int x = 0; x < RenderConstants.CHUNK_SIZE; x++) {
            for (int y = 0; y < RenderConstants.CHUNK_SIZE; y++) {
                for (int z = 0; z < RenderConstants.CHUNK_SIZE; z++) {
                    renderBlock(x, y, z);
                }
            }
        }
    }

    private void renderBlock(int x, int y, int z) {
        if (BlockEnvironmentUtil.shouldRenderBlock(chunk.getBlocks(), x, y, z)) {
            float blockPositionX = (float) x * RenderConstants.BLOCK_LENGTH + chunkOffsetX;
            float blockPositionY = (float) y * RenderConstants.BLOCK_LENGTH + chunkOffsetY;
            float blockPositionZ = (float) z * RenderConstants.BLOCK_LENGTH;
            int offset = RenderConstants.BLOCK_LENGTH / 2;

            BlockRenderer blockRenderer = new BlockRenderer(blockPositionX, blockPositionY, blockPositionZ, offset, vertexPositionData, vertexTextureData);

            blockRenderer.renderTopSurface(chunk.getBlocks(), x, y, z);
            blockRenderer.renderBottomSurface(chunk.getBlocks(), x, y, z);
            blockRenderer.renderFrontSurface(chunk.getBlocks(), x, y, z);
            blockRenderer.renderBackSurface(chunk.getBlocks(), x, y, z);
            blockRenderer.renderLeftSurface(chunk.getBlocks(), x, y, z);
            blockRenderer.renderRightSurface(chunk.getBlocks(), x, y, z);
        }
    }

    private void initBuffers(Chunk chunk) {
        chunk.setVboTextureHandle(GL15.glGenBuffers());
        chunk.setVboVertexHandle(GL15.glGenBuffers());
    }

    private void initVertexBuffer() {
        vertexPositionData = BufferUtils.createFloatBuffer((RenderConstants.CHUNK_BLOCK_COUNT) * 6 * 12);
        vertexTextureData = BufferUtils.createFloatBuffer((RenderConstants.CHUNK_BLOCK_COUNT) * 6 * 12);
    }

    private void calculateChunkOffset(int chunkX, int chunkY) {
        chunkOffsetX = RenderConstants.BLOCK_LENGTH * RenderConstants.CHUNK_SIZE * chunkX;
        chunkOffsetY = RenderConstants.BLOCK_LENGTH * RenderConstants.CHUNK_SIZE * chunkY;
    }

    private void flipVertexBuffer() {
        vertexTextureData.flip();
        vertexPositionData.flip();
    }

    private void bindVertexBuffer(Chunk chunk) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, chunk.getVboVertexHandle());
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexPositionData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, chunk.getVboTextureHandle());
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexTextureData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
}