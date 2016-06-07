package de.krien.games.madness.render.voxel;

import de.krien.games.madness.render.camera.Camera;

public class World {

    private static World instance;

    private Chunk chunk;
    private Camera camera;

    private World() {
        camera = new Camera();
    }

    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public void setChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
