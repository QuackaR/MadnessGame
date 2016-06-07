package de.krien.games.madness.render.voxel.util;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.voxel.Block;
import de.krien.games.madness.render.voxel.BlockType;
import de.krien.games.madness.render.voxel.Chunk;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;
import java.util.Random;

public class ChunkGenerator {

    private Random random= new Random();

    public Chunk generateBaseChunk() {
        Chunk chunk = new Chunk();
        for (int x = 0; x < RenderConstants.CHUNK_SIZE; x++) {
            for (int y = 0; y < RenderConstants.CHUNK_SIZE; y++) {
                Block grassBlock = new Block(BlockType.BlockType_Grass);
                chunk.getBlocks()[x][y][RenderConstants.CHUNK_SIZE - 1] = grassBlock;
                for (int z = RenderConstants.CHUNK_SIZE - 2; z > RenderConstants.CHUNK_SIZE - 9; z--) {
                    Block dirtBlock = new Block(BlockType.BlockType_Dirt);
                    chunk.getBlocks()[x][y][z] = dirtBlock;
                }
            }
        }
        chunk.setVboColorHandle(GL15.glGenBuffers());
        chunk.setVboVertexHandle(GL15.glGenBuffers());

        return chunk;
    }

    public Chunk generateRandomChunk() {
        Chunk chunk = new Chunk();

        for (int x = 0; x < RenderConstants.CHUNK_SIZE; x++) {
            for (int y = 0; y < RenderConstants.CHUNK_SIZE; y++) {
                for (int z = 0; z < RenderConstants.CHUNK_SIZE; z++) {
                    if (random.nextFloat() <= 0.7f) {
                        Block block = new Block(BlockType.BlockType_Default);
                        block.setActive(false);
                        chunk.getBlocks()[x][y][z] = block;
                    } else if (random.nextFloat() > 0.9f) {
                        chunk.getBlocks()[x][y][z] = new Block(BlockType.BlockType_Grass);
                    } else if (random.nextFloat() > 0.8f) {
                        chunk.getBlocks()[x][y][z] = new Block(BlockType.BlockType_Dirt);
                    } else if (random.nextFloat() > 0.7f) {
                        chunk.getBlocks()[x][y][z] = new Block(BlockType.BlockType_Water);
                    }
                }
            }
        }
        chunk.setVboColorHandle(GL15.glGenBuffers());
        chunk.setVboVertexHandle(GL15.glGenBuffers());

        return chunk;
    }

    public Chunk generateProChunk() {
        Chunk chunk = new Chunk();

        for(int z = 0; z < 5; z++) {
            int y = 32;
            for(int x = 28; x>=0; x--) {
                chunk.getBlocks()[x][y][z] = new Block(BlockType.BlockType_Dirt);
                chunk.getBlocks()[x][y-1][z] = new Block(BlockType.BlockType_Water);
                chunk.getBlocks()[x][y-2][z] = new Block(BlockType.BlockType_Grass);
                chunk.getBlocks()[x][y-3][z] = new Block(BlockType.BlockType_Stone);
                y--;
            }
            for(int x = 56; x>=27; x--) {
                chunk.getBlocks()[x][y][z] = new Block(BlockType.BlockType_Dirt);
                chunk.getBlocks()[x][y-1][z] = new Block(BlockType.BlockType_Water);
                chunk.getBlocks()[x][y-2][z] = new Block(BlockType.BlockType_Grass);
                chunk.getBlocks()[x][y-3][z] = new Block(BlockType.BlockType_Stone);
                y++;
            }

            for(int yLine = 32; yLine < 63; yLine++) {
                chunk.getBlocks()[3][yLine][z] = new Block(BlockType.BlockType_Dirt);
                chunk.getBlocks()[2][yLine][z] = new Block(BlockType.BlockType_Water);
                chunk.getBlocks()[1][yLine][z] = new Block(BlockType.BlockType_Grass);
                chunk.getBlocks()[0][yLine][z] = new Block(BlockType.BlockType_Stone);
            }
        }

//        chunk.setVboColorHandle(GL15.glGenBuffers());
//        chunk.setVboVertexHandle(GL15.glGenBuffers());

        return chunk;
    }

}
