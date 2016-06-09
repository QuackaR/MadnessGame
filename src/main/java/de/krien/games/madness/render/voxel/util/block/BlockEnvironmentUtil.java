package de.krien.games.madness.render.voxel.util.block;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.voxel.Block;

public class BlockEnvironmentUtil {

    public static boolean shouldRenderBlock(Block[][][] blocks, int x, int y, int z) {
        Block block = blocks[x][y][z];
        return block != null && block.isActive() && blockIsNotSurrounded(blocks, x, y, z);
    }

    public static boolean isBlockAboveActive(Block[][][] blocks, int x, int y, int z) {
        return z == (RenderConstants.CHUNK_SIZE - 1) || blocks[x][y][z + 1] == null || !blocks[x][y][z + 1].isActive();
    }

    public static boolean isBlockBeneathActive(Block[][][] blocks, int x, int y, int z) {
        return z == 0 || blocks[x][y][z - 1] == null || !blocks[x][y][z - 1].isActive();
    }

    public static boolean isBlockBeforeActive(Block[][][] blocks, int x, int y, int z) {
        return y == 0 || blocks[x][y - 1][z] == null || !blocks[x][y - 1][z].isActive();
    }

    public static boolean isBlockBehindActive(Block[][][] blocks, int x, int y, int z) {
        return y == (RenderConstants.CHUNK_SIZE - 1) || blocks[x][y + 1][z] == null || !blocks[x][y + 1][z].isActive();
    }

    public static boolean isBlockToTheLeftActive(Block[][][] blocks, int x, int y, int z) {
        return x == 0 || blocks[x - 1][y][z] == null || !blocks[x - 1][y][z].isActive();
    }

    public static boolean isBlockToTheRightActive(Block[][][] blocks, int x, int y, int z) {
        return x == (RenderConstants.CHUNK_SIZE - 1) || blocks[x + 1][y][z] == null || !blocks[x + 1][y][z].isActive();
    }

    private static boolean blockIsNotSurrounded(Block[][][] blocks, int x, int y, int z) {
        boolean allSurroundingBlocksActive = isBlockAboveActive(blocks, x, y, z)
                && isBlockBeneathActive(blocks, x, y, z)
                && isBlockBeforeActive(blocks, x, y, z)
                && isBlockBehindActive(blocks, x, y, z)
                && isBlockToTheLeftActive(blocks, x, y, z)
                && isBlockToTheRightActive(blocks, x, y, z);
        if (allSurroundingBlocksActive) {
            return false;
        }
        return true;
    }

}
