package de.krien.games.madness.render.voxel.util;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.voxel.Block;
import de.krien.games.madness.render.voxel.Chunk;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.newdawn.slick.opengl.Texture;
import java.nio.FloatBuffer;
import java.util.Arrays;

public class ChunkRenderer {

    public void renderMesh(Chunk chunk, int chunkX, int chunkY) {
        chunk.setVboTextureHandle(GL15.glGenBuffers());
        chunk.setVboVertexHandle(GL15.glGenBuffers());

        FloatBuffer vertexPositionData = BufferUtils.createFloatBuffer((RenderConstants.CHUNK_BLOCK_COUNT) * 6 * 12);
        FloatBuffer vertexTextureData = BufferUtils.createFloatBuffer((RenderConstants.CHUNK_BLOCK_COUNT) * 6 * 12);

        float chunkOffsetX = RenderConstants.BLOCK_LENGTH * RenderConstants.CHUNK_SIZE * chunkX;
        float chunkOffsetY = RenderConstants.BLOCK_LENGTH * RenderConstants.CHUNK_SIZE * chunkY;

        for (int x = 0; x < RenderConstants.CHUNK_SIZE; x++) {
            for (int y = 0; y < RenderConstants.CHUNK_SIZE; y++) {
                for (int z = 0; z < RenderConstants.CHUNK_SIZE; z++) {
                    Block block = chunk.getBlocks()[x][y][z];
                    if (block != null && block.isActive()) {
                        float blockPositionX = (float) x * RenderConstants.BLOCK_LENGTH + chunkOffsetX;
                        float blockPositionY = (float) y * RenderConstants.BLOCK_LENGTH + chunkOffsetY;
                        float blockPositionZ = (float) z * RenderConstants.BLOCK_LENGTH;

                        if(chunkX == 0 && chunkY == 0 && x == 0 && y == 0 && z == 56) {
                            System.out.print("Chunk: " + chunkX + ":" + chunkY + " - Block " + x + ":" + y + ":" + z + " has pos (" + blockPositionX + "/"+ blockPositionY + "/" + blockPositionZ + ").\n");
                        }
                        if(chunkX == 1 && chunkY == 0 && x == 0 && y == 0 && z == 56) {
                            System.out.print("Chunk: " + chunkX + ":" + chunkY + " - Block " + x + ":" + y + ":" + z + " has pos (" + blockPositionX + "/"+ blockPositionY + "/" + blockPositionZ + ").\n");
                        }
                        if(chunkX == 0 && chunkY == 1 && x == 0 && y == 0 && z == 56) {
                            System.out.print("Chunk: " + chunkX + ":" + chunkY + " - Block " + x + ":" + y + ":" + z + " has pos (" + blockPositionX + "/"+ blockPositionY + "/" + blockPositionZ + ").\n");
                        }
                        if(chunkX == 1 && chunkY == 1 && x == 0 && y == 0 && z == 56) {
                            System.out.print("Chunk: " + chunkX + ":" + chunkY + " - Block " + x + ":" + y + ":" + z + " has pos (" + blockPositionX + "/"+ blockPositionY + "/" + blockPositionZ + ").\n");
                        }

                        float[] cube = createCube(blockPositionX, blockPositionY, blockPositionZ);
                        vertexPositionData.put(cube);

                        float[] cubeVertexTexture = createCubeVertexTexture(block);
                        vertexTextureData.put(cubeVertexTexture);
                    }
                }
            }

        }

        vertexTextureData.flip();
        vertexPositionData.flip();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, chunk.getVboVertexHandle());
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexPositionData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, chunk.getVboTextureHandle());
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexTextureData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private float[] createCube(float x, float y, float z) {
        int offset = RenderConstants.BLOCK_LENGTH / 2;
        return new float[]{
                // BOTTOM QUAD(DOWN=+Y)
                x + offset, y + offset, z,
                x - offset, y + offset, z,
                x - offset, y + offset, z - RenderConstants.BLOCK_LENGTH,
                x + offset, y + offset, z - RenderConstants.BLOCK_LENGTH,
                // TOP!
                x + offset, y - offset, z - RenderConstants.BLOCK_LENGTH,
                x - offset, y - offset, z - RenderConstants.BLOCK_LENGTH,
                x - offset, y - offset, z,
                x + offset, y - offset, z,
                // FRONT QUAD
                x + offset, y + offset, z - RenderConstants.BLOCK_LENGTH,
                x - offset, y + offset, z - RenderConstants.BLOCK_LENGTH,
                x - offset, y - offset, z - RenderConstants.BLOCK_LENGTH,
                x + offset, y - offset, z - RenderConstants.BLOCK_LENGTH,
                // BACK QUAD
                x + offset, y - offset, z,
                x - offset, y - offset, z,
                x - offset, y + offset, z,
                x + offset, y + offset, z,
                // LEFT QUAD
                x - offset, y + offset, z - RenderConstants.BLOCK_LENGTH,
                x - offset, y + offset, z,
                x - offset, y - offset, z,
                x - offset, y - offset, z - RenderConstants.BLOCK_LENGTH,
                // RIGHT QUAD
                x + offset, y + offset, z,
                x + offset, y + offset, z - RenderConstants.BLOCK_LENGTH,
                x + offset, y - offset, z - RenderConstants.BLOCK_LENGTH,
                x + offset, y - offset, z};
    }

    private float[] createCubeVertexTexture(Block block) {
        float[] topSurface = generateSurfaceTextureCoordinates(1, block.getBlockType().getBlockID());
        float[] frontSurface = generateSurfaceTextureCoordinates(2, block.getBlockType().getBlockID());
        float[] leftSurface = generateSurfaceTextureCoordinates(3, block.getBlockType().getBlockID());
        float[] backSurface = generateSurfaceTextureCoordinates(4, block.getBlockType().getBlockID());
        float[] rightSurface = generateSurfaceTextureCoordinates(5, block.getBlockType().getBlockID());
        float[] bottomSurface = generateSurfaceTextureCoordinates(6, block.getBlockType().getBlockID());

        float[] cubeTextures = concatAll(backSurface, frontSurface, bottomSurface, topSurface, leftSurface, rightSurface);
        return cubeTextures;
    }

    private float[] generateSurfaceTextureCoordinates(float tileColumn, float tileLine) {
        float tileDim = 1.0f / 8.0f;

        float upperX = (tileColumn - 1) * tileDim;
        float leftY = (tileLine - 1) * tileDim;
        float lowerX = tileDim * tileColumn;
        float rightY = tileLine * tileDim;

        float[] surfaceTextureCoordinates = new float[]{
                upperX, leftY,      //x=0,y=0
                upperX, rightY,     //x=1,y=0
                lowerX, rightY,     //x=1,y=1
                lowerX, leftY};     //x=0,y=1

        return surfaceTextureCoordinates;
    }

    private float[] concatAll(float[] first, float[]... rest) {
        int totalLength = first.length;
        for (float[] array : rest) {
            totalLength += array.length;
        }
        float[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (float[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

}
