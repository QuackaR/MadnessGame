package de.krien.games.madness;

import de.krien.games.madness.game.GameState;
import de.krien.games.madness.input.CameraController;
import de.krien.games.madness.render.Renderer;
import de.krien.games.madness.render.voxel.World;
import org.lwjgl.opengl.Display;

public class Game {

    private GameState gameState;
    private World world;

    private Renderer renderer;
    private CameraController cameraController;

    public Game() {
        gameState = GameState.State_Menu;
        renderer = new Renderer();
        world = World.getInstance();
        cameraController = new CameraController();
    }

    public void start() {
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
