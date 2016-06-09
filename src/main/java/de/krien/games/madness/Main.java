package de.krien.games.madness;

import de.krien.games.madness.render.Renderer;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Main {

    public static void main(String[] args) {
        Game game = Game.getInstance();
        game.start();
    }
}
