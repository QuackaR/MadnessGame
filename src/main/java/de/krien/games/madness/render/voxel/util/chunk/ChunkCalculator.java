package de.krien.games.madness.render.voxel.util.chunk;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.voxel.Block;
import de.krien.games.madness.render.voxel.Chunk;
import de.krien.games.madness.render.voxel.util.block.BlockEnvironmentUtil;
import de.krien.games.madness.render.voxel.util.block.BlockCalculator;
import de.krien.games.madness.render.voxel.util.block.BlockPositionUtil;
import de.krien.games.madness.util.Vector3i;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;

public class ChunkCalculator {

    private Chunk chunk;

    private FloatBuffer vertexPositionData;
    private FloatBuffer vertexTextureData;

    public ChunkCalculator(Chunk chunk) {
        this.chunk = chunk;
    }

    public void render() {
        initBuffers(chunk);
        initVertexBuffer();

        renderChunk();

        fillVertexBuffer();
        flipVertexBuffer();
        bindVertexBuffer(chunk);
    }

    public void rerenderBlock(Vector3i index) {
        initBuffers(chunk);
        resetVertexBuffer();
        renderBlock(index);
        fillVertexBuffer();
        flipVertexBuffer();
        bindVertexBuffer(chunk);
    }

    private void renderChunk() {
        for (int x = 0; x < RenderConstants.CHUNK_SIZE; x++) {
            for (int y = 0; y < RenderConstants.CHUNK_SIZE; y++) {
                for (int z = 0; z < RenderConstants.CHUNK_SIZE; z++) {
                    renderBlock(new Vector3i(x, y, z));
                }
            }
        }
    }

    private void renderBlock(Vector3i index) {
        if (BlockEnvironmentUtil.shouldRenderBlock(chunk.getBlocks(), index)) {
            Vector3f blockPosition = BlockPositionUtil.getBlockPosition(chunk, index);
            int offset = BlockPositionUtil.getBlockOffset();

            BlockCalculator blockCalculator = new BlockCalculator(blockPosition, offset);
            blockCalculator.renderBlock(chunk.getBlocks(), index);
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

    private void resetVertexBuffer() {
        vertexPositionData.clear();
        vertexTextureData.clear();
    }

    private void fillVertexBuffer() {
        for (int x = 0; x < RenderConstants.CHUNK_SIZE; x++) {
            for (int y = 0; y < RenderConstants.CHUNK_SIZE; y++) {
                for (int z = 0; z < RenderConstants.CHUNK_SIZE; z++) {
                    Block block = chunk.getBlocks()[x][y][z];
                    if (block != null) {
                        if (block.getTopSurface() != null && block.getTopSurfaceTexture() != null) {
                            vertexPositionData.put(block.getTopSurface());
                            vertexTextureData.put(block.getTopSurfaceTexture());
                        }
                        if (block.getBottomSurface() != null && block.getBottomSurfaceTexture() != null) {
                            vertexPositionData.put(block.getBottomSurface());
                            vertexTextureData.put(block.getBottomSurfaceTexture());
                        }
                        if (block.getFrontSurface() != null && block.getFrontSurfaceTexture() != null) {
                            vertexPositionData.put(block.getFrontSurface());
                            vertexTextureData.put(block.getFrontSurfaceTexture());
                        }
                        if (block.getBackSurface() != null && block.getBackSurfaceTexture() != null) {
                            vertexPositionData.put(block.getBackSurface());
                            vertexTextureData.put(block.getBackSurfaceTexture());
                        }
                        if (block.getLeftSurface() != null && block.getLeftSurfaceTexture() != null) {
                            vertexPositionData.put(block.getLeftSurface());
                            vertexTextureData.put(block.getLeftSurfaceTexture());
                        }
                        if (block.getRightSurface() != null && block.getRightSurfaceTexture() != null) {
                            vertexPositionData.put(block.getRightSurface());
                            vertexTextureData.put(block.getRightSurfaceTexture());
                        }
                    }
                }
            }
        }
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
