package de.krien.games.madness;

import de.krien.games.madness.game.GameState;
import de.krien.games.madness.input.CameraController;
import de.krien.games.madness.render.Renderer;
import de.krien.games.madness.render.voxel.World;
import org.lwjgl.opengl.Display;

public class Game {

    private static Game instance;

    public static Game getInstance() {
        if(instance==null) {
            instance = new Game();
        }
    return instance;
    }

    private GameState gameState;
    private World world;

    private Renderer renderer;
    private CameraController cameraController;

    private Game() {
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

    public Renderer getRenderer() {
        return renderer;
    }
}
