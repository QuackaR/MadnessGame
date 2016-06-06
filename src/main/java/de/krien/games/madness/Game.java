package de.krien.games.madness;

import de.krien.games.madness.game.GameState;
import de.krien.games.madness.render.Renderer;
import de.krien.games.madness.render.voxel.Chunk;
import de.krien.games.madness.render.voxel.util.ChunkGenerator;
import org.lwjgl.opengl.Display;

public class Game {

    private GameState gameState;
    private Renderer renderer;

    Chunk worldChunk;

    public Game() {
        gameState = GameState.State_Menu;
        renderer = new Renderer();
    }

    public void start() {
        worldChunk = new ChunkGenerator().generateProChunk();
        worldChunk.render();

        while (!Display.isCloseRequested()) {
            update();
            draw();

        }
        Display.destroy();
    }

    private void update() {

    }

    private void draw() {
        renderer.draw(worldChunk);
    }
}
