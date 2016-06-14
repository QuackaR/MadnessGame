package de.krien.games.madness.view.voxel.util.chunk;

import de.krien.games.madness.view.render.RenderConstants;
import de.krien.games.madness.view.voxel.Block;
import de.krien.games.madness.view.voxel.BlockType;
import de.krien.games.madness.view.voxel.Chunk;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector2f;

import java.util.Random;

public class ChunkGenerator {

    private Random random= new Random();

    public Chunk generateBaseChunk(Vector2f position) {
        Chunk chunk = new Chunk(position);
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

    public Chunk generateLandscapeChunk(Vector2f position) {
        Chunk chunk = new Chunk(position);
        float[][] noise = generateSimplexNoise(RenderConstants.CHUNK_SIZE, RenderConstants.CHUNK_SIZE);

        for (int x = 0; x < RenderConstants.CHUNK_SIZE; x++) {
            for (int y = 0; y < RenderConstants.CHUNK_SIZE; y++) {
                int height = (int) (noise[x][y] * RenderConstants.CHUNK_SIZE / 4);
                chunk.getBlocks()[x][y][0] = new Block(BlockType.WATER);
                for (int z = 1; z < height / 2; z++) {
                        chunk.getBlocks()[x][y][z] = new Block(BlockType.STONE);
                }
                for (int z = height / 2; z < height - 1; z++) {
                    chunk.getBlocks()[x][y][z] = new Block(BlockType.DIRT);
                }
                if(height>0) {
                    chunk.getBlocks()[x][y][height-1] = new Block(BlockType.GRASS);
                }
            }
        }
        chunk.setVboTextureHandle(GL15.glGenBuffers());
        chunk.setVboVertexHandle(GL15.glGenBuffers());

        return chunk;
    }

    private static float[][] generateSimplexNoise(int width, int height){
        float[][] simplexnoise = new float[width][height];
        float frequency = 5.0f / (float) width;

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                simplexnoise[x][y] = (float) noise(x * frequency,y * frequency);
                simplexnoise[x][y] = (simplexnoise[x][y] + 1) / 2;   //generate values between 0 and 1
            }
        }

        return simplexnoise;
    }

    private static int[][] grad3 = {{1,1,0},{-1,1,0},{1,-1,0},{-1,-1,0},
            {1,0,1},{-1,0,1},{1,0,-1},{-1,0,-1},
            {0,1,1},{0,-1,1},{0,1,-1},{0,-1,-1}};

    private static int p[] = {151,160,137,91,90,15,
            131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,
            190, 6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,
            88,237,149,56,87,174,20,125,136,171,168, 68,175,74,165,71,134,139,48,27,166,
            77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,55,46,245,40,244,
            102,143,54, 65,25,63,161, 1,216,80,73,209,76,132,187,208, 89,18,169,200,196,
            135,130,116,188,159,86,164,100,109,198,173,186, 3,64,52,217,226,250,124,123,
            5,202,38,147,118,126,255,82,85,212,207,206,59,227,47,16,58,17,182,189,28,42,
            223,183,170,213,119,248,152, 2,44,154,163, 70,221,153,101,155,167, 43,172,9,
            129,22,39,253, 19,98,108,110,79,113,224,232,178,185, 112,104,218,246,97,228,
            251,34,242,193,238,210,144,12,191,179,162,241, 81,51,145,235,249,14,239,107,
            49,192,214, 31,181,199,106,157,184, 84,204,176,115,121,50,45,127, 4,150,254,
            138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180};

    public static double noise(double xin, double yin) {
        double n0, n1, n2; // Noise contributions from the three corners
        // Skew the input space to determine which simplex cell we're in
        final double F2 = 0.5*(Math.sqrt(3.0)-1.0);
        double s = (xin+yin)*F2; // Hairy factor for 2D
        int i = fastfloor(xin+s);
        int j = fastfloor(yin+s);
        final double G2 = (3.0-Math.sqrt(3.0))/6.0;
        double t = (i+j)*G2;
        double X0 = i-t; // Unskew the cell origin back to (x,y) space
        double Y0 = j-t;
        double x0 = xin-X0; // The x,y distances from the cell origin
        double y0 = yin-Y0;
        // For the 2D case, the simplex shape is an equilateral triangle.
        // Determine which simplex we are in.
        int i1, j1; // Offsets for second (middle) corner of simplex in (i,j) coords
        if(x0>y0) {i1=1; j1=0;} // lower triangle, XY order: (0,0)->(1,0)->(1,1)
        else {i1=0; j1=1;} // upper triangle, YX order: (0,0)->(0,1)->(1,1)
        // A step of (1,0) in (i,j) means a step of (1-c,-c) in (x,y), and
        // a step of (0,1) in (i,j) means a step of (-c,1-c) in (x,y), where
        // c = (3-sqrt(3))/6
        double x1 = x0 - i1 + G2; // Offsets for middle corner in (x,y) unskewed coords
        double y1 = y0 - j1 + G2;
        double x2 = x0 - 1.0 + 2.0 * G2; // Offsets for last corner in (x,y) unskewed coords
        double y2 = y0 - 1.0 + 2.0 * G2;
        // Work out the hashed gradient indices of the three simplex corners
        int ii = i & 255;
        int jj = j & 255;
        int gi0 = perm[ii+perm[jj]] % 12;
        int gi1 = perm[ii+i1+perm[jj+j1]] % 12;
        int gi2 = perm[ii+1+perm[jj+1]] % 12;
        // Calculate the contribution from the three corners
        double t0 = 0.5 - x0*x0-y0*y0;
        if(t0<0) n0 = 0.0;
        else {
            t0 *= t0;
            n0 = t0 * t0 * dot(grad3[gi0], x0, y0); // (x,y) of grad3 used for 2D gradient
        }
        double t1 = 0.5 - x1*x1-y1*y1;
        if(t1<0) n1 = 0.0;
        else {
            t1 *= t1;
            n1 = t1 * t1 * dot(grad3[gi1], x1, y1);
        }
        double t2 = 0.5 - x2*x2-y2*y2;
        if(t2<0) n2 = 0.0;
        else {
            t2 *= t2;
            n2 = t2 * t2 * dot(grad3[gi2], x2, y2);
        }
        // Add contributions from each corner to get the final noise value.
        // The result is scaled to return values in the interval [-1,1].
        return 70.0 * (n0 + n1 + n2);
    }

    private static int perm[] = new int[512];
    static { for(int i=0; i<512; i++) perm[i]=p[i & 255]; }

    private static int fastfloor(double x) {
        return x>0 ? (int)x : (int)x-1;
    }
    private static double dot(int g[], double x, double y) {
        return g[0]*x + g[1]*y; }
    private static double dot(int g[], double x, double y, double z) {
        return g[0]*x + g[1]*y + g[2]*z;
    }

    public Chunk generateSphereChunk(Vector2f position) {
        Chunk chunk = new Chunk(position);

        for (int x = 0; x < RenderConstants.CHUNK_SIZE; x++) {
            for (int y = 0; y < RenderConstants.CHUNK_SIZE; y++) {
                for (int z = 0; z < RenderConstants.CHUNK_SIZE; z++) {
                    if (Math.sqrt((float) (x-RenderConstants.CHUNK_SIZE/2)*(x-RenderConstants.CHUNK_SIZE/2) + (y-RenderConstants.CHUNK_SIZE/2)*(y-RenderConstants.CHUNK_SIZE/2) + (z-RenderConstants.CHUNK_SIZE/2)*(z-RenderConstants.CHUNK_SIZE/2)) <= RenderConstants.CHUNK_SIZE/2) {
                        if (random.nextFloat() > 0.8f) {
                            chunk.getBlocks()[x][y][z] = new Block(BlockType.GRASS);
                        } else if (random.nextFloat() > 0.6f) {
                            chunk.getBlocks()[x][y][z] = new Block(BlockType.DIRT);
                        } else if (random.nextFloat() > 0.4f) {
                            chunk.getBlocks()[x][y][z] = new Block(BlockType.WATER);
                        } else if (random.nextFloat() > 0.2f) {
                            chunk.getBlocks()[x][y][z] = new Block(BlockType.SAND);
                        } else if (random.nextFloat() > 0.0f) {
                            chunk.getBlocks()[x][y][z] = new Block(BlockType.STONE);
                        }
                    }
                }
            }
        }
        chunk.setVboTextureHandle(GL15.glGenBuffers());
        chunk.setVboVertexHandle(GL15.glGenBuffers());

        return chunk;
    }

    public Chunk generateRandomChunk(Vector2f position) {
        Chunk chunk = new Chunk(position);

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

    public Chunk generateProChunk(Vector2f position) {
        Chunk chunk = new Chunk(position);

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
