package de.krien.games.madness.render.voxel.util.block;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.voxel.Block;
import de.krien.games.madness.render.voxel.Chunk;
import de.krien.games.madness.render.voxel.util.chunk.ChunkPositionUtil;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class BlockPositionUtil {

    public static Block getBlockOfPoint(Chunk chunk, Vector3f position) {
        for (int x = 0; x < RenderConstants.CHUNK_SIZE; x++) {
            for (int y = 0; y < RenderConstants.CHUNK_SIZE; y++) {
                for (int z = 0; z < RenderConstants.CHUNK_SIZE; z++) {
                    Vector3f blockIndex = new Vector3f(x, y, z);
                    Block block = chunk.getBlocks()[x][y][z];
                    if (block != null && block.isActive() && BlockEnvironmentUtil.shouldRenderBlock(chunk.getBlocks(),x,y,z) && positionIsInBlock(chunk, block, blockIndex, position)) {
                        return block;
                    }
                }
            }
        }
        return null;
    }

    public static Vector3f getBlockPositionOfPoint(Chunk chunk, Vector3f position) {
        for (int x = 0; x < RenderConstants.CHUNK_SIZE; x++) {
            for (int y = 0; y < RenderConstants.CHUNK_SIZE; y++) {
                for (int z = 0; z < RenderConstants.CHUNK_SIZE; z++) {
                    Vector3f blockIndex = new Vector3f(x, y, z);
                    Block block = chunk.getBlocks()[x][y][z];
                    if (block != null && block.isActive() && BlockEnvironmentUtil.shouldRenderBlock(chunk.getBlocks(),x,y,z) && positionIsInBlock(chunk, block, blockIndex, position)) {
                        Vector3f blockPosition = getBlockPosition(chunk, blockIndex);
                        return blockPosition;
                    }
                }
            }
        }
        return null;
    }

    private static boolean positionIsInBlock(Chunk chunk, Block block, Vector3f blockIndex, Vector3f position) {
        Vector3f minCorner = getMinCorner(position, chunk, block, blockIndex);
        Vector3f maxCorner = getMaxCorner(position, chunk, block, blockIndex);

        int posXMinXComparansion = new Float(position.getX()).compareTo(new Float(minCorner.getX()));
        int posXMaxXComparansion = new Float(position.getX()).compareTo(new Float(maxCorner.getX()));
        int posYMinYComparansion = new Float(position.getY()).compareTo(new Float(minCorner.getY()));
        int posYMaxYComparansion = new Float(position.getY()).compareTo(new Float(maxCorner.getY()));
        int posZMinZComparansion = new Float(position.getZ()).compareTo(new Float(minCorner.getZ()));
        int posZMaxZComparansion = new Float(position.getZ()).compareTo(new Float(maxCorner.getZ()));

        boolean posXisGreaterThenMinX = posXMinXComparansion >= 0;
        boolean posXisLesserThenMaxX = posXMaxXComparansion <= 0;
        boolean posYisGreaterThenMinY = posYMinYComparansion >= 0;
        boolean posYisLesserThenMaxY = posYMaxYComparansion <= 0;
        boolean posZisGreaterThenMinZ = posZMinZComparansion >= 0;
        boolean posZisLesserThenMaxZ = posZMaxZComparansion <= 0;

        if (posXisGreaterThenMinX && posXisLesserThenMaxX
                && posYisGreaterThenMinY && posYisLesserThenMaxY
                && posZisGreaterThenMinZ && posZisLesserThenMaxZ) {

            System.out.println("Block - MinCorner: " + minCorner.getX() + ":" + minCorner.getY() + ":" + minCorner.getZ());
            System.out.println("Block - MaxCorner: " + maxCorner.getX() + ":" + maxCorner.getY() + ":" + maxCorner.getZ());
            System.out.println("Block - RayPickPosition: " + position.getX() + ":" + position.getY() + ":" + position.getZ());
            return true;
        }
        return false;
    }

    private static Vector3f getMinCorner(Vector3f position, Chunk chunk, Block block, Vector3f blockIndex) {
        Vector3f blockPosition = getBlockPosition(chunk, blockIndex);
        int blockOffset = getBlockOffset();
        return new Vector3f(blockPosition.getX() - blockOffset, blockPosition.getY() - blockOffset, blockPosition.getZ() - RenderConstants.BLOCK_LENGTH);
    }


    private static Vector3f getMaxCorner(Vector3f position, Chunk chunk, Block block, Vector3f blockIndex) {
        Vector3f blockPosition = getBlockPosition(chunk, blockIndex);
        int blockOffset = getBlockOffset();
        return new Vector3f(blockPosition.getX() + blockOffset, blockPosition.getY() + blockOffset, blockPosition.getZ());
    }

    public static Vector3f getBlockPosition(Chunk chunk, Vector3f blockIndex) {
        Vector2f chunkOffset = ChunkPositionUtil.getChunkOffset(chunk);
        float blockPositionX = (float) blockIndex.getX() * RenderConstants.BLOCK_LENGTH + chunkOffset.getX();
        float blockPositionY = (float) blockIndex.getY() * RenderConstants.BLOCK_LENGTH + chunkOffset.getY();
        float blockPositionZ = (float) blockIndex.getZ() * RenderConstants.BLOCK_LENGTH;
        return new Vector3f(blockPositionX, blockPositionY, blockPositionZ);
    }

    public static int getBlockOffset() {
        int blockOffset = RenderConstants.BLOCK_LENGTH / 2;
        return blockOffset;
    }

}
