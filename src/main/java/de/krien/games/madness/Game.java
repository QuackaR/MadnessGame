package de.krien.games.madness;

import de.krien.games.madness.game.GameState;
import de.krien.games.madness.input.CameraController;
import de.krien.games.madness.render.Renderer;
import de.krien.games.madness.render.voxel.Chunk;
import de.krien.games.madness.render.voxel.World;
import de.krien.games.madness.render.voxel.util.ChunkGenerator;
import org.lwjgl.opengl.Display;

public class Game {

    private GameState gameState;
    private World world;

    private Renderer renderer;
    private CameraController cameraController;

    public Game() {
        gameState = GameState.State_Menu;
        world = World.getInstance();

        renderer = new Renderer();
        cameraController = new CameraController();
    }

    public void start() {
        Chunk chunk = new ChunkGenerator().generateBaseChunk();
        chunk.render();
        world.setChunk(chunk);

        while (!Display.isCloseRequested()) {
            update();
            draw();

        }
        Display.destroy();
    }

    private void update() {
        cameraController.processInput();
    }

    private void draw() {
        renderer.draw(world);
    }
}
