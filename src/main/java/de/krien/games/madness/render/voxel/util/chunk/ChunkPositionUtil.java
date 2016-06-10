package de.krien.games.madness.render.voxel.util.chunk;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.voxel.Chunk;
import de.krien.games.madness.render.voxel.World;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class ChunkPositionUtil {

    public static Chunk getChunkOfPoint(Vector3f position) {
        Chunk[][] chunks = World.getInstance().getChunks();
        for(int x = 0; x < chunks.length; x++) {
            for(int y = 0; y < chunks[x].length; y++) {
                Chunk chunk = chunks[x][y];
                if (positionIsInChunk(position, chunk)) {
                    return chunk;
                }
            }
        }
        return null;
    }

    private static boolean positionIsInChunk(Vector3f position, Chunk chunk) {
        Vector3f minCorner = getMinCorner(position, chunk);
        Vector3f maxCorner = getMaxCorner(position, chunk);
        if(position.getX() >= minCorner.getX() && position.getX() <= maxCorner.getX()
                && position.getY() >= minCorner.getY() && position.getY() <= maxCorner.getY()
                &&position.getZ() >= minCorner.getZ() && position.getZ() <= maxCorner.getZ()) {
            return true;
        }
        return false;
    }

    private static Vector3f getMinCorner(Vector3f position, Chunk chunk) {
        float chunkOffsetX = RenderConstants.BLOCK_LENGTH * RenderConstants.CHUNK_SIZE * chunk.getPosition().getX();
        float chunkOffsetY = RenderConstants.BLOCK_LENGTH * RenderConstants.CHUNK_SIZE * chunk.getPosition().getY();
        float blockPositionX = (float) 0 * RenderConstants.BLOCK_LENGTH + chunkOffsetX;
        float blockPositionY = (float) 0 * RenderConstants.BLOCK_LENGTH + chunkOffsetY;
        float blockPositionZ = (float) 0 * RenderConstants.BLOCK_LENGTH;
        int offset = RenderConstants.BLOCK_LENGTH / 2;

        return new Vector3f(blockPositionX - offset, blockPositionY - offset, blockPositionZ - RenderConstants.BLOCK_LENGTH);
    }


    private static Vector3f getMaxCorner(Vector3f position, Chunk chunk) {
        float chunkOffsetX = RenderConstants.BLOCK_LENGTH * RenderConstants.CHUNK_SIZE * chunk.getPosition().getX();
        float chunkOffsetY = RenderConstants.BLOCK_LENGTH * RenderConstants.CHUNK_SIZE * chunk.getPosition().getY();
        float blockPositionX = (float) (RenderConstants.CHUNK_SIZE - 1) * RenderConstants.BLOCK_LENGTH + chunkOffsetX;
        float blockPositionY = (float) (RenderConstants.CHUNK_SIZE - 1) * RenderConstants.BLOCK_LENGTH + chunkOffsetY;
        float blockPositionZ = (float) (RenderConstants.CHUNK_SIZE - 1) * RenderConstants.BLOCK_LENGTH;
        int offset = RenderConstants.BLOCK_LENGTH / 2;

        return new Vector3f(blockPositionX + offset, blockPositionY + offset, blockPositionZ);
    }

    public static Vector2f getChunkOffset(Chunk chunk) {
        float chunkOffsetX = RenderConstants.BLOCK_LENGTH * RenderConstants.CHUNK_SIZE * chunk.getPosition().getX();
        float chunkOffsetY = RenderConstants.BLOCK_LENGTH * RenderConstants.CHUNK_SIZE * chunk.getPosition().getY();
        return new Vector2f(chunkOffsetX, chunkOffsetY);
    }
}
