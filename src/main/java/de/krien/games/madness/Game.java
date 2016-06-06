package de.krien.games.madness;

import de.krien.games.madness.game.GameState;
import de.krien.games.madness.render.Renderer;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Game {

    private GameState gameState;
    private Renderer renderer;

    public Game() {
        gameState = GameState.State_Menu;
        renderer = new Renderer();
    }

    public void start() {
        renderer.run();

//        while (!Display.isCloseRequested()) {
//            update();
//            draw();
//        }
    }

    private void update() {
    }

    private void draw() {
        renderer.run();
    }
}
