package de.krien.games.madness.render.voxel.util;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.voxel.Block;
import de.krien.games.madness.render.voxel.BlockType;
import de.krien.games.madness.render.voxel.Chunk;
import org.lwjgl.opengl.GL15;

import java.util.Random;

public class ChunkGenerator {

    private Random random= new Random();

    public Chunk generateBaseChunk() {
        Chunk chunk = new Chunk();
        for (int x = 0; x < RenderConstants.CHUNK_SIZE; x++) {
            for (int y = 0; y < RenderConstants.CHUNK_SIZE; y++) {
                Block grassBlock = new Block(BlockType.GRASS);
                chunk.getBlocks()[x][y][10] = grassBlock;
                for (int z = 5; z < 10; z++) {
                    Block dirtBlock = new Block(BlockType.DIRT);
                    chunk.getBlocks()[x][y][z] = dirtBlock;
                }
                for (int z = 0; z < 5; z++) {
                    Block dirtBlock = new Block(BlockType.STONE);
                    chunk.getBlocks()[x][y][z] = dirtBlock;
                }
            }
        }
        chunk.setVboTextureHandle(GL15.glGenBuffers());
        chunk.setVboVertexHandle(GL15.glGenBuffers());

        return chunk;
    }

    public Chunk generateRandomChunk() {
        Chunk chunk = new Chunk();

        for (int x = 0; x < RenderConstants.CHUNK_SIZE; x++) {
            for (int y = 0; y < RenderConstants.CHUNK_SIZE; y++) {
                for (int z = 0; z < RenderConstants.CHUNK_SIZE; z++) {
                    if (random.nextFloat() <= 0.5f) {
                        Block block = new Block(BlockType.DEFAULT);
                        block.setActive(false);
                        chunk.getBlocks()[x][y][z] = block;
                    } else if (random.nextFloat() > 0.9f) {
                        chunk.getBlocks()[x][y][z] = new Block(BlockType.GRASS);
                    } else if (random.nextFloat() > 0.8f) {
                        chunk.getBlocks()[x][y][z] = new Block(BlockType.DIRT);
                    } else if (random.nextFloat() > 0.7f) {
                        chunk.getBlocks()[x][y][z] = new Block(BlockType.WATER);
                    } else if (random.nextFloat() > 0.6f) {
                        chunk.getBlocks()[x][y][z] = new Block(BlockType.SAND);
                    } else if (random.nextFloat() > 0.5f) {
                        chunk.getBlocks()[x][y][z] = new Block(BlockType.STONE);
                    }
                }
            }
        }
        chunk.setVboTextureHandle(GL15.glGenBuffers());
        chunk.setVboVertexHandle(GL15.glGenBuffers());

        return chunk;
    }

    public Chunk generateProChunk() {
        Chunk chunk = new Chunk();

        for(int z = 0; z < 5; z++) {
            int y = 32;
            for(int x = 28; x>=0; x--) {
                chunk.getBlocks()[x][y][z] = new Block(BlockType.DIRT);
                chunk.getBlocks()[x][y-1][z] = new Block(BlockType.WATER);
                chunk.getBlocks()[x][y-2][z] = new Block(BlockType.GRASS);
                chunk.getBlocks()[x][y-3][z] = new Block(BlockType.STONE);
                y--;
            }
            for(int x = 56; x>=27; x--) {
                chunk.getBlocks()[x][y][z] = new Block(BlockType.DIRT);
                chunk.getBlocks()[x][y-1][z] = new Block(BlockType.WATER);
                chunk.getBlocks()[x][y-2][z] = new Block(BlockType.GRASS);
                chunk.getBlocks()[x][y-3][z] = new Block(BlockType.STONE);
                y++;
            }

            for(int yLine = 32; yLine < 63; yLine++) {
                chunk.getBlocks()[3][yLine][z] = new Block(BlockType.DIRT);
                chunk.getBlocks()[2][yLine][z] = new Block(BlockType.WATER);
                chunk.getBlocks()[1][yLine][z] = new Block(BlockType.GRASS);
                chunk.getBlocks()[0][yLine][z] = new Block(BlockType.STONE);
            }
        }

//        chunk.setVboColorHandle(GL15.glGenBuffers());
//        chunk.setVboVertexHandle(GL15.glGenBuffers());

        return chunk;
    }

}
