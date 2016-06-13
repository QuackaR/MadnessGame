package de.krien.games.madness.render.voxel.util.block;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.voxel.Block;
import de.krien.games.madness.render.voxel.BlockType;
import de.krien.games.madness.util.Vector3i;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;

public class BlockCalculator {

    private Vector3f blockPosition;
    private int offset;

    private FloatBuffer vertexPositionData;
    private FloatBuffer vertexTextureData;

    public BlockCalculator(Vector3f blockPosition, int offset, FloatBuffer vertexPositionData, FloatBuffer vertexTextureData) {
        this.blockPosition = blockPosition;
        this.offset = offset;
        this.vertexPositionData = vertexPositionData;
        this.vertexTextureData = vertexTextureData;
    }

    public void renderBlock(Block[][][] blocks, Vector3i index) {
        renderTopSurface(blocks, index);
        renderBottomSurface(blocks, index);
        renderFrontSurface(blocks, index);
        renderBackSurface(blocks, index);
        renderLeftSurface(blocks, index);
        renderRightSurface(blocks, index);
    }

    private void renderTopSurface(Block[][][] blocks, Vector3i index) {
        if (BlockEnvironmentUtil.isBlockAboveActive(blocks, index)) {
            vertexPositionData.put(new float[]{
                    blockPosition.getX() + offset, blockPosition.getY() - offset, blockPosition.getZ(),
                    blockPosition.getX() - offset, blockPosition.getY() - offset, blockPosition.getZ(),
                    blockPosition.getX() - offset, blockPosition.getY() + offset, blockPosition.getZ(),
                    blockPosition.getX() + offset, blockPosition.getY() + offset, blockPosition.getZ()});
            if(blocks[index.getX()][index.getY()][index.getZ()].isSelected()) {
                vertexTextureData.put(generateSurfaceTextureCoordinates(1, BlockType.SAND.getBlockID()));
            } else {
                vertexTextureData.put(generateSurfaceTextureCoordinates(1, blocks[index.getX()][index.getY()][index.getZ()].getBlockType().getBlockID()));
            }

        }
    }

    private void renderBottomSurface(Block[][][] blocks, Vector3i index) {
        if (BlockEnvironmentUtil.isBlockBeneathActive(blocks, index)) {
            vertexPositionData.put(new float[]{
                    blockPosition.getX() + offset, blockPosition.getY() + offset, blockPosition.getZ() - RenderConstants.BLOCK_LENGTH,
                    blockPosition.getX() - offset, blockPosition.getY() + offset, blockPosition.getZ() - RenderConstants.BLOCK_LENGTH,
                    blockPosition.getX() - offset, blockPosition.getY() - offset, blockPosition.getZ() - RenderConstants.BLOCK_LENGTH,
                    blockPosition.getX() + offset, blockPosition.getY() - offset, blockPosition.getZ() - RenderConstants.BLOCK_LENGTH});
            vertexTextureData.put(generateSurfaceTextureCoordinates(6, blocks[index.getX()][index.getY()][index.getZ()].getBlockType().getBlockID()));
        }
    }

    private void renderFrontSurface(Block[][][] blocks, Vector3i index) {
        if (BlockEnvironmentUtil.isBlockBeforeActive(blocks, index)) {
            vertexPositionData.put(new float[]{
                    blockPosition.getX() + offset, blockPosition.getY() - offset, blockPosition.getZ() - RenderConstants.BLOCK_LENGTH,
                    blockPosition.getX() - offset, blockPosition.getY() - offset, blockPosition.getZ() - RenderConstants.BLOCK_LENGTH,
                    blockPosition.getX() - offset, blockPosition.getY() - offset, blockPosition.getZ(),
                    blockPosition.getX() + offset, blockPosition.getY() - offset, blockPosition.getZ()});
            vertexTextureData.put(generateSurfaceTextureCoordinates(2, blocks[index.getX()][index.getY()][index.getZ()].getBlockType().getBlockID()));
        }
    }

    private void renderBackSurface(Block[][][] blocks, Vector3i index) {
        if (BlockEnvironmentUtil.isBlockBehindActive(blocks, index)) {
            vertexPositionData.put(new float[]{
                    blockPosition.getX() + offset, blockPosition.getY() + offset, blockPosition.getZ(),
                    blockPosition.getX() - offset, blockPosition.getY() + offset, blockPosition.getZ(),
                    blockPosition.getX() - offset, blockPosition.getY() + offset, blockPosition.getZ() - RenderConstants.BLOCK_LENGTH,
                    blockPosition.getX() + offset, blockPosition.getY() + offset, blockPosition.getZ() - RenderConstants.BLOCK_LENGTH});
            vertexTextureData.put(generateSurfaceTextureCoordinates(4, blocks[index.getX()][index.getY()][index.getZ()].getBlockType().getBlockID()));
        }
    }

    private void renderLeftSurface(Block[][][] blocks, Vector3i index) {
        if (BlockEnvironmentUtil.isBlockToTheLeftActive(blocks, index)) {
            vertexPositionData.put(new float[]{
                    blockPosition.getX() - offset, blockPosition.getY() + offset, blockPosition.getZ() - RenderConstants.BLOCK_LENGTH,
                    blockPosition.getX() - offset, blockPosition.getY() + offset, blockPosition.getZ(),
                    blockPosition.getX() - offset, blockPosition.getY() - offset, blockPosition.getZ(),
                    blockPosition.getX() - offset, blockPosition.getY() - offset, blockPosition.getZ() - RenderConstants.BLOCK_LENGTH});
            vertexTextureData.put(generateSurfaceTextureCoordinates(3, blocks[index.getX()][index.getY()][index.getZ()].getBlockType().getBlockID()));
        }
    }

    private void renderRightSurface(Block[][][] blocks, Vector3i index) {
        if (BlockEnvironmentUtil.isBlockToTheRightActive(blocks, index)) {
            vertexPositionData.put(new float[]{
                    blockPosition.getX() + offset, blockPosition.getY() + offset, blockPosition.getZ(),
                    blockPosition.getX() + offset, blockPosition.getY() + offset, blockPosition.getZ() - RenderConstants.BLOCK_LENGTH,
                    blockPosition.getX() + offset, blockPosition.getY() - offset, blockPosition.getZ() - RenderConstants.BLOCK_LENGTH,
                    blockPosition.getX() + offset, blockPosition.getY() - offset, blockPosition.getZ()});
            vertexTextureData.put(generateSurfaceTextureCoordinates(5, blocks[index.getX()][index.getY()][index.getZ()].getBlockType().getBlockID()));
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
