package de.krien.games.madness.render;


public class RenderConstants {

    public static final int BLOCK_LENGTH = 2; // Size of one block

    public static final int CHUNK_SIZE = 64; // Count of blocks per dimension
    public static final int CHUNK_BLOCK_COUNT = CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE; // Count of blocks in one Chunk

    public static final int WORLD_SIZE_X = 1; // Width of world in chunks
    public static final int WORLD_SIZE_Y = 1; // Length of world in chunks

    public static final int MAX_FPS = 100;
    public static final float VIEW_DISTANCE = 300.0f;
    // public static final int WORLD_SIZE_Z = 4; // Height of world in chunks

}
