package de.krien.games.madness.render.voxel;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.camera.Camera;
import de.krien.games.madness.render.voxel.util.ChunkGenerator;

public class World {

    private static World instance;

    private Chunk[][] chunks;
    private Camera camera;

    private World() {
        camera = new Camera();
        chunks = new Chunk[RenderConstants.WORLD_SIZE_X][RenderConstants.WORLD_SIZE_Y];
        for(int x = 0; x < chunks.length; x++) {
            for(int y = 0; y < (chunks[x].length / 2); y++) {
                Chunk chunk = new ChunkGenerator().generateBaseChunk();
                chunk.render(x, y);
                chunks[x][y] = chunk;
            }
            for(int y = (chunks[x].length / 2); y < chunks[x].length; y++) {
                Chunk chunk = new ChunkGenerator().generateRandomChunk();
                chunk.render(x, y);
                chunks[x][y] = chunk;
            }
        }

    }

    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

    public Chunk[][] getChunks() {
        return chunks;
    }

    public Chunk getChunk(int x, int y) {
        return chunks[x][y];
    }

    public void setChunk(int x, int y, Chunk chunk) {
        this.chunks[x][y] = chunk;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
