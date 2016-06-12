package de.krien.games.madness.render.voxel.util.block;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.voxel.Block;
import de.krien.games.madness.render.voxel.BlockType;

import java.nio.FloatBuffer;

public class BlockCalculator {

    private float blockPositionX;
    private float blockPositionY;
    private float blockPositionZ;
    private int offset;

    private FloatBuffer vertexPositionData;
    private FloatBuffer vertexTextureData;

    public BlockCalculator(float blockPositionX, float blockPositionY, float blockPositionZ, int offset, FloatBuffer vertexPositionData, FloatBuffer vertexTextureData) {
        this.blockPositionX = blockPositionX;
        this.blockPositionY = blockPositionY;
        this.blockPositionZ = blockPositionZ;
        this.offset = offset;
        this.vertexPositionData = vertexPositionData;
        this.vertexTextureData = vertexTextureData;
    }

    public void renderBlock() {
        Block[][][] blocks = new Block[3][3][3];
        int x = 1;
        int y = 1;
        int z = 1;
        blocks[1][1][1] = new Block(BlockType.WOOD);
        renderTopSurface(blocks, x, y, z);
        renderBottomSurface(blocks, x, y, z);
        renderFrontSurface(blocks, x, y, z);
        renderBackSurface(blocks, x, y, z);
        renderLeftSurface(blocks, x, y, z);
        renderRightSurface(blocks, x, y, z);
    }

    public void renderBlock(Block[][][] blocks, int x, int y, int z) {
        renderTopSurface(blocks, x, y, z);
        renderBottomSurface(blocks, x, y, z);
        renderFrontSurface(blocks, x, y, z);
        renderBackSurface(blocks, x, y, z);
        renderLeftSurface(blocks, x, y, z);
        renderRightSurface(blocks, x, y, z);
    }

    private void renderTopSurface(Block[][][] blocks, int x, int y, int z) {
        if (BlockEnvironmentUtil.isBlockAboveActive(blocks, x, y, z)) {
            vertexPositionData.put(new float[]{
                    blockPositionX + offset, blockPositionY - offset, blockPositionZ,
                    blockPositionX - offset, blockPositionY - offset, blockPositionZ,
                    blockPositionX - offset, blockPositionY + offset, blockPositionZ,
                    blockPositionX + offset, blockPositionY + offset, blockPositionZ});
            if(blocks[x][y][z].isSelected()) {
                vertexTextureData.put(generateSurfaceTextureCoordinates(1, BlockType.SAND.getBlockID()));
            } else {
                vertexTextureData.put(generateSurfaceTextureCoordinates(1, blocks[x][y][z].getBlockType().getBlockID()));
            }

        }
    }

    private void renderBottomSurface(Block[][][] blocks, int x, int y, int z) {
        if (BlockEnvironmentUtil.isBlockBeneathActive(blocks, x, y, z)) {
            vertexPositionData.put(new float[]{
                    blockPositionX + offset, blockPositionY + offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX - offset, blockPositionY + offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX - offset, blockPositionY - offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX + offset, blockPositionY - offset, blockPositionZ - RenderConstants.BLOCK_LENGTH});
            vertexTextureData.put(generateSurfaceTextureCoordinates(6, blocks[x][y][z].getBlockType().getBlockID()));
        }
    }

    private void renderFrontSurface(Block[][][] blocks, int x, int y, int z) {
        if (BlockEnvironmentUtil.isBlockBeforeActive(blocks, x, y, z)) {
            vertexPositionData.put(new float[]{
                    blockPositionX + offset, blockPositionY - offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX - offset, blockPositionY - offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX - offset, blockPositionY - offset, blockPositionZ,
                    blockPositionX + offset, blockPositionY - offset, blockPositionZ});
            vertexTextureData.put(generateSurfaceTextureCoordinates(2, blocks[x][y][z].getBlockType().getBlockID()));
        }
    }

    private void renderBackSurface(Block[][][] blocks, int x, int y, int z) {
        if (BlockEnvironmentUtil.isBlockBehindActive(blocks, x, y, z)) {
            vertexPositionData.put(new float[]{
                    blockPositionX + offset, blockPositionY + offset, blockPositionZ,
                    blockPositionX - offset, blockPositionY + offset, blockPositionZ,
                    blockPositionX - offset, blockPositionY + offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX + offset, blockPositionY + offset, blockPositionZ - RenderConstants.BLOCK_LENGTH});
            vertexTextureData.put(generateSurfaceTextureCoordinates(4, blocks[x][y][z].getBlockType().getBlockID()));
        }
    }

    private void renderLeftSurface(Block[][][] blocks, int x, int y, int z) {
        if (BlockEnvironmentUtil.isBlockToTheLeftActive(blocks, x, y, z)) {
            vertexPositionData.put(new float[]{
                    blockPositionX - offset, blockPositionY + offset, blockPositionZ - RenderConstants.BLOCK_LENGTH,
                    blockPositionX - offset, blockPositionY + offset, blockPositionZ,
                    blockPositionX - offset, blockPositionY - offset, blockPositionZ,
                    blockPositionX - offset, blockPositionY - offset, blockPositionZ - RenderConstants.BLOCK_LENGTH});
            vertexTextureData.put(generateSurfaceTextureCoordinates(3, blocks[x][y][z].getBlockType().getBlockID()));
        }
    }

    private void renderRightSurface(Block[][][] blocks, int x, int y, int z) {
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
