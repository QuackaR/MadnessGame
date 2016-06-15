package de.krien.games.madness.view.voxel.util.block;

import de.krien.games.madness.util.Vector3i;
import de.krien.games.madness.view.render.RenderConstants;
import de.krien.games.madness.view.voxel.Block;

public class BlockEnvironmentUtil {
    public static boolean shouldRenderBlock(Block[][][] blocks, Vector3i index) {
        Block block = blocks[index.getX()][index.getY()][index.getZ()];
        return block != null && block.isActive() && blockIsNotSurrounded(blocks, index);
    }

    public static boolean isBlockAboveActive(Block[][][] blocks, Vector3i index) {
        return index.getZ() == (RenderConstants.CHUNK_SIZE - 1) || blocks[index.getX()][index.getY()][index.getZ() + 1] == null || !blocks[index.getX()][index.getY()][index.getZ() + 1].isActive();
    }

    public static boolean isBlockBeneathActive(Block[][][] blocks, Vector3i index) {
        return index.getZ() == 0 || blocks[index.getX()][index.getY()][index.getZ() - 1] == null || !blocks[index.getX()][index.getY()][index.getZ() - 1].isActive();
    }

    public static boolean isBlockBeforeActive(Block[][][] blocks, Vector3i index) {
        return index.getY() == 0 || blocks[index.getX()][index.getY() - 1][index.getZ()] == null || !blocks[index.getX()][index.getY() - 1][index.getZ()].isActive();
    }

    public static boolean isBlockBehindActive(Block[][][] blocks, Vector3i index) {
        return index.getY() == (RenderConstants.CHUNK_SIZE - 1) || blocks[index.getX()][index.getY() + 1][index.getZ()] == null || !blocks[index.getX()][index.getY() + 1][index.getZ()].isActive();
    }

    public static boolean isBlockToTheLeftActive(Block[][][] blocks, Vector3i index) {
        return index.getX() == 0 || blocks[index.getX() - 1][index.getY()][index.getZ()] == null || !blocks[index.getX() - 1][index.getY()][index.getZ()].isActive();
    }

    public static boolean isBlockToTheRightActive(Block[][][] blocks, Vector3i index) {
        return index.getX() == (RenderConstants.CHUNK_SIZE - 1) || blocks[index.getX() + 1][index.getY()][index.getZ()] == null || !blocks[index.getX() + 1][index.getY()][index.getZ()].isActive();
    }

    private static boolean blockIsNotSurrounded(Block[][][] blocks, Vector3i index) {
        boolean allSurroundingBlocksActive = isBlockAboveActive(blocks, index)
                && isBlockBeneathActive(blocks, index)
                && isBlockBeforeActive(blocks, index)
                && isBlockBehindActive(blocks, index)
                && isBlockToTheLeftActive(blocks, index)
                && isBlockToTheRightActive(blocks, index);
        if (allSurroundingBlocksActive) {
            return false;
        }
        return true;
    }

}
