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

    float chunkOffsetX;
    float chunkOffsetY;

    public void renderMesh(Chunk chunk, int chunkX, int chunkY) {
        chunk.setVboTextureHandle(GL15.glGenBuffers());
        chunk.setVboVertexHandle(GL15.glGenBuffers());

        FloatBuffer vertexPositionData = BufferUtils.createFloatBuffer((RenderConstants.CHUNK_BLOCK_COUNT) * 6 * 12);
        FloatBuffer vertexTextureData = BufferUtils.createFloatBuffer((RenderConstants.CHUNK_BLOCK_COUNT) * 6 * 12);

        chunkOffsetX = RenderConstants.BLOCK_LENGTH * RenderConstants.CHUNK_SIZE * chunkX;
        chunkOffsetY = RenderConstants.BLOCK_LENGTH * RenderConstants.CHUNK_SIZE * chunkY;

        for (int x = 0; x < RenderConstants.CHUNK_SIZE; x++) {
            for (int y = 0; y < RenderConstants.CHUNK_SIZE; y++) {
                for (int z = 0; z < RenderConstants.CHUNK_SIZE; z++) {
                    Block block = chunk.getBlocks()[x][y][z];
                    if (block != null && block.isActive() && blockIsNotSurrounded(chunk.getBlocks(), x, y, z)) {
                        float[] cube = createCube(x, y, z, chunk.getBlocks());
                        vertexPositionData.put(cube);

                        float[] cubeVertexTexture = createCubeVertexTexture(x, y, z, chunk.getBlocks());
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

    private boolean blockIsNotSurrounded(Block[][][] blocks, int x, int y, int z) {
        boolean render = true;
        if (x > 0 && blocks[x - 1][y][z] != null && blocks[x - 1][y][z].isActive()
                && x < (RenderConstants.CHUNK_SIZE - 1) && blocks[x + 1][y][z] != null && blocks[x + 1][y][z].isActive()
                && y > 0 && blocks[x][y - 1][z] != null && blocks[x][y - 1][z].isActive()
                && y < (RenderConstants.CHUNK_SIZE - 1) && blocks[x][y + 1][z] != null && blocks[x][y + 1][z].isActive()
                && z > 0 && blocks[x][y][z - 1] != null && blocks[x][y][z - 1].isActive()
                && z < (RenderConstants.CHUNK_SIZE - 1) && blocks[x][y][z + 1] != null && blocks[x][y][z + 1].isActive()) {
            render = false;
        }
        return render;
    }

    private float[] createCube(int x, int y, int z, Block[][][] blocks) {
        float blockPositionX = (float) x * RenderConstants.BLOCK_LENGTH + chunkOffsetX;
        float blockPositionY = (float) y * RenderConstants.BLOCK_LENGTH + chunkOffsetY;
        float blockPositionZ = (float) z * RenderConstants.BLOCK_LENGTH;
        int offset = RenderConstants.BLOCK_LENGTH / 2;

        float[] bottom = new float[0];
        float[] top = new float[0];
        float[] front = new float[0];
        float[] back = new float[0];
        float[] left = new float[0];
        float[] right = new float[0];

        if (y == (RenderConstants.CHUNK_SIZE - 1) || blocks[x][y + 1][z] == null || !blocks[x][y + 1][z].isActive()) {
            bottom = new float[]{
                    // BOTTOM QUAD(DOWN=+Y) //
                    blockPositionX + offset, blockPositionY + offset, blockPositionZ,
                    blockPositionX - offset, blockPositionY + offset, blockPositionZ,
                    blockPositionX - offset, blockPositionY + offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX + offset, blockPositionY + offset, blockPositionZ - RenderConstants.BLOCK_LENGTH};
        }
        if (y == 0 || blocks[x][y - 1][z] == null || !blocks[x][y - 1][z].isActive()) {
            top = new float[]{
                    // TOP!
                    blockPositionX + offset, blockPositionY - offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX - offset, blockPositionY - offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX - offset, blockPositionY - offset, blockPositionZ,
                    blockPositionX + offset, blockPositionY - offset, blockPositionZ};
        }
        if (z == 0 || blocks[x][y][z - 1] == null || !blocks[x][y][z - 1].isActive()) {
            front = new float[]{
                    // FRONT QUAD // Bottom?
                    blockPositionX + offset, blockPositionY + offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX - offset, blockPositionY + offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX - offset, blockPositionY - offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX + offset, blockPositionY - offset, blockPositionZ - RenderConstants.BLOCK_LENGTH};
        }
        if (z == (RenderConstants.CHUNK_SIZE - 1) || blocks[x][y][z + 1] == null || !blocks[x][y][z + 1].isActive()) {
            back = new float[]{
                    // BACK QUAD // Top?
                    blockPositionX + offset, blockPositionY - offset, blockPositionZ,
                    blockPositionX - offset, blockPositionY - offset, blockPositionZ,
                    blockPositionX - offset, blockPositionY + offset, blockPositionZ,
                    blockPositionX + offset, blockPositionY + offset, blockPositionZ};
        }
        if (x == 0 || blocks[x - 1][y][z] == null || !blocks[x - 1][y][z].isActive()) {
            left = new float[]{
                    // LEFT QUAD
                    blockPositionX - offset, blockPositionY + offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX - offset, blockPositionY + offset, blockPositionZ,
                    blockPositionX - offset, blockPositionY - offset, blockPositionZ,
                    blockPositionX - offset, blockPositionY - offset, blockPositionZ - RenderConstants.BLOCK_LENGTH};
        }
        if (x == (RenderConstants.CHUNK_SIZE - 1) || blocks[x + 1][y][z] == null || !blocks[x + 1][y][z].isActive()) {
            right = new float[]{
                    // RIGHT QUAD
                    blockPositionX + offset, blockPositionY + offset, blockPositionZ,
                    blockPositionX + offset, blockPositionY + offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX + offset, blockPositionY - offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX + offset, blockPositionY - offset, blockPositionZ};
        }

        return concatAll(bottom, top, front, back, left, right);
    }

    private float[] createCubeVertexTexture(int x, int y, int z, Block[][][] blocks) {
        Block block = blocks[x][y][z];

        float[] backSurface = new float[0];
        float[] frontSurface = new float[0];
        float[] bottomSurface = new float[0];
        float[] topSurface = new float[0];
        float[] leftSurface = new float[0];
        float[] rightSurface = new float[0];

        if (y == (RenderConstants.CHUNK_SIZE - 1) || blocks[x][y + 1][z] == null || !blocks[x][y + 1][z].isActive()) {
            backSurface = generateSurfaceTextureCoordinates(4, block.getBlockType().getBlockID());
        }
        if (y == 0 || blocks[x][y - 1][z] == null || !blocks[x][y - 1][z].isActive()) {
            frontSurface = generateSurfaceTextureCoordinates(2, block.getBlockType().getBlockID());
        }
        if (z == 0 || blocks[x][y][z - 1] == null || !blocks[x][y][z - 1].isActive()) {
            bottomSurface = generateSurfaceTextureCoordinates(6, block.getBlockType().getBlockID());
        }
        if (z == (RenderConstants.CHUNK_SIZE - 1) || blocks[x][y][z + 1] == null || !blocks[x][y][z + 1].isActive()) {
            topSurface = generateSurfaceTextureCoordinates(1, block.getBlockType().getBlockID());
        }
        if (x == 0 || blocks[x - 1][y][z] == null || !blocks[x - 1][y][z].isActive()) {
            leftSurface = generateSurfaceTextureCoordinates(3, block.getBlockType().getBlockID());
        }
        if (x == (RenderConstants.CHUNK_SIZE - 1) || blocks[x + 1][y][z] == null || !blocks[x + 1][y][z].isActive()) {
            rightSurface = generateSurfaceTextureCoordinates(5, block.getBlockType().getBlockID());
        }

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
