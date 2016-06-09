package de.krien.games.madness.render.voxel.util.block;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.voxel.Block;

import java.nio.FloatBuffer;

public class BlockRenderer {

    private float blockPositionX;
    private float blockPositionY;
    private float blockPositionZ;
    private int offset;

    private FloatBuffer vertexPositionData;
    private FloatBuffer vertexTextureData;

    public BlockRenderer(float blockPositionX, float blockPositionY, float blockPositionZ, int offset, FloatBuffer vertexPositionData, FloatBuffer vertexTextureData) {
        this.blockPositionX = blockPositionX;
        this.blockPositionY = blockPositionY;
        this.blockPositionZ = blockPositionZ;
        this.offset = offset;
        this.vertexPositionData = vertexPositionData;
        this.vertexTextureData = vertexTextureData;
    }

    public void renderTopSurface(Block[][][] blocks, int x, int y, int z) {
        if (BlockEnvironmentUtil.isBlockAboveActive(blocks, x, y, z)) {
            vertexPositionData.put(new float[]{
                    blockPositionX + offset, blockPositionY - offset, blockPositionZ,
                    blockPositionX - offset, blockPositionY - offset, blockPositionZ,
                    blockPositionX - offset, blockPositionY + offset, blockPositionZ,
                    blockPositionX + offset, blockPositionY + offset, blockPositionZ});
            vertexTextureData.put(generateSurfaceTextureCoordinates(1, blocks[x][y][z].getBlockType().getBlockID()));
        }
    }

    public void renderBottomSurface(Block[][][] blocks, int x, int y, int z) {
        if (BlockEnvironmentUtil.isBlockBeneathActive(blocks, x, y, z)) {
            vertexPositionData.put(new float[]{
                    blockPositionX + offset, blockPositionY + offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX - offset, blockPositionY + offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX - offset, blockPositionY - offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX + offset, blockPositionY - offset, blockPositionZ - RenderConstants.BLOCK_LENGTH});
            vertexTextureData.put(generateSurfaceTextureCoordinates(6, blocks[x][y][z].getBlockType().getBlockID()));
        }
    }

    public void renderFrontSurface(Block[][][] blocks, int x, int y, int z) {
        if (BlockEnvironmentUtil.isBlockBeforeActive(blocks, x, y, z)) {
            vertexPositionData.put(new float[]{
                    blockPositionX + offset, blockPositionY - offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX - offset, blockPositionY - offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX - offset, blockPositionY - offset, blockPositionZ,
                    blockPositionX + offset, blockPositionY - offset, blockPositionZ});
            vertexTextureData.put(generateSurfaceTextureCoordinates(2, blocks[x][y][z].getBlockType().getBlockID()));
        }
    }

    public void renderBackSurface(Block[][][] blocks, int x, int y, int z) {
        if (BlockEnvironmentUtil.isBlockBehindActive(blocks, x, y, z)) {
            vertexPositionData.put(new float[]{
                    blockPositionX + offset, blockPositionY + offset, blockPositionZ,
                    blockPositionX - offset, blockPositionY + offset, blockPositionZ,
                    blockPositionX - offset, blockPositionY + offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX + offset, blockPositionY + offset, blockPositionZ - RenderConstants.BLOCK_LENGTH});
            vertexTextureData.put(generateSurfaceTextureCoordinates(4, blocks[x][y][z].getBlockType().getBlockID()));
        }
    }

    public void renderLeftSurface(Block[][][] blocks, int x, int y, int z) {
        if (BlockEnvironmentUtil.isBlockToTheLeftActive(blocks, x, y, z)) {
            vertexPositionData.put(new float[]{
                    blockPositionX - offset, blockPositionY + offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX - offset, blockPositionY + offset, blockPositionZ,
                    blockPositionX - offset, blockPositionY - offset, blockPositionZ,
                    blockPositionX - offset, blockPositionY - offset, blockPositionZ - RenderConstants.BLOCK_LENGTH});
            vertexTextureData.put(generateSurfaceTextureCoordinates(3, blocks[x][y][z].getBlockType().getBlockID()));
        }
    }

    public void renderRightSurface(Block[][][] blocks, int x, int y, int z) {
        if (BlockEnvironmentUtil.isBlockToTheRightActive(blocks, x, y, z)) {
            vertexPositionData.put(new float[]{
                    blockPositionX + offset, blockPositionY + offset, blockPositionZ,
                    blockPositionX + offset, blockPositionY + offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX + offset, blockPositionY - offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX + offset, blockPositionY - offset, blockPositionZ});
            vertexTextureData.put(generateSurfaceTextureCoordinates(5, blocks[x][y][z].getBlockType().getBlockID()));
        }
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
}
