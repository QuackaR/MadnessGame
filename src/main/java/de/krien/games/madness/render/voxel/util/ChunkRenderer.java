package de.krien.games.madness.render.voxel.util;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.voxel.Block;
import de.krien.games.madness.render.voxel.Chunk;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;

public class ChunkRenderer {

   public void renderMesh(Chunk chunk) {
        chunk.setVboColorHandle(GL15.glGenBuffers());
        chunk.setVboVertexHandle(GL15.glGenBuffers());

        FloatBuffer vertexPositionData = BufferUtils.createFloatBuffer((RenderConstants.CHUNK_BLOCK_COUNT) * 6 * 12);
        FloatBuffer vertexColorData = BufferUtils.createFloatBuffer((RenderConstants.CHUNK_BLOCK_COUNT) * 6 * 12);

        for (int x = 0; x < RenderConstants.CHUNK_SIZE; x++) {
            for (int y = 0; y < RenderConstants.CHUNK_SIZE; y++) {
                for (int z = 0; z < RenderConstants.CHUNK_SIZE; z++) {
                    Block block = chunk.getBlocks()[x][y][z];
                    if(block != null && block.isActive()) {
                        float blockPositionX = (float) x * RenderConstants.BLOCK_LENGTH;
                        float blockPositionY = (float) y * RenderConstants.BLOCK_LENGTH;
                        float blockPositionZ = (float) z * RenderConstants.BLOCK_LENGTH;
                        float[] cube = createCube(blockPositionX, blockPositionY, blockPositionZ);
                        vertexPositionData.put(cube);

                        float[] blockColor = block.getBlockType().getColor();
                        float[] cubeVertexColor = createCubeVertexColor(blockColor);
                        vertexColorData.put(cubeVertexColor);
                    }
                }
            }

        }

        vertexColorData.flip();
        vertexPositionData.flip();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, chunk.getVboVertexHandle());
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexPositionData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, chunk.getVboColorHandle());
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexColorData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private float[] createCube(float x, float y, float z) {
        int offset = RenderConstants.BLOCK_LENGTH / 2;
        return new float[]{
                // BOTTOM QUAD(DOWN=+Y)
                x + offset,
                y + offset,
                z,
                x - offset,
                y + offset,
                z,
                x - offset,
                y + offset,
                z - RenderConstants.BLOCK_LENGTH,
                x + offset,
                y + offset,
                z - RenderConstants.BLOCK_LENGTH,
                // TOP!
                x + offset, y - offset, z - RenderConstants.BLOCK_LENGTH, x - offset,
                y - offset,
                z - RenderConstants.BLOCK_LENGTH,
                x - offset,
                y - offset,
                z,
                x + offset,
                y - offset,
                z,
                // FRONT QUAD
                x + offset, y + offset, z - RenderConstants.BLOCK_LENGTH, x - offset,
                y + offset, z - RenderConstants.BLOCK_LENGTH, x - offset,
                y - offset,
                z - RenderConstants.BLOCK_LENGTH,
                x + offset,
                y - offset,
                z - RenderConstants.BLOCK_LENGTH,
                // BACK QUAD
                x + offset, y - offset, z, x - offset, y - offset, z,
                x - offset, y + offset, z,
                x + offset,
                y + offset,
                z,
                // LEFT QUAD
                x - offset, y + offset, z - RenderConstants.BLOCK_LENGTH, x - offset,
                y + offset, z, x - offset, y - offset, z, x - offset,
                y - offset,
                z - RenderConstants.BLOCK_LENGTH,
                // RIGHT QUAD
                x + offset, y + offset, z, x + offset, y + offset,
                z - RenderConstants.BLOCK_LENGTH, x + offset, y - offset, z - RenderConstants.BLOCK_LENGTH,
                x + offset, y - offset, z};
    }

    private float[] createCubeVertexColor(float[] cubeColorArray) {
        float[] cubeColors = new float[cubeColorArray.length * 4 * 6];
        for (int i = 0; i < cubeColors.length; i++) {
            cubeColors[i] = cubeColorArray[i % cubeColorArray.length];
        }
        return cubeColors;
    }

}
