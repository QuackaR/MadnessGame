package de.krien.games.madness;

import de.krien.games.madness.game.GameState;
import de.krien.games.madness.render.Renderer;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Game {

    public static GameState currentState = GameState.State_Menu;

    public void start() {
        try {
            Renderer renderer = new Renderer();
            renderer.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
