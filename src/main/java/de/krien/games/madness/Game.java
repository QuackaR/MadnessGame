package de.krien.games.madness;

import de.krien.games.madness.game.GameState;
import de.krien.games.madness.input.CameraController;
import de.krien.games.madness.input.MeshController;
import de.krien.games.madness.view.mesh.Mesh;
import de.krien.games.madness.view.render.Renderer;
import de.krien.games.madness.view.voxel.World;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class Game {

    private static Game instance;

    public static Game getInstance() {
        if(instance==null) {
            instance = new Game();
        }
    return instance;
    }

    private GameState gameState;

    private Renderer renderer;
    private CameraController cameraController;
    private MeshController meshController;

    private Game() {
        gameState = GameState.State_Menu;
        renderer = new Renderer();
        cameraController = new CameraController();

        Mesh mesh = new Mesh("res/untitled.obj", "res/untitled.png");
        mesh.load();
        mesh.setPosition(new Vector3f(0.0f, 0.0f, 150.0f));
        mesh.setRotation(new Vector3f(90.0f, 0.0f, 0.0f));
        mesh.setScale(0.1f);
        renderer.getGameobjectList3D().add(mesh);

        meshController = new MeshController(mesh);

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
        meshController.processInput();
    }

    private void draw() {
        renderer.draw();
    }

    public Renderer getRenderer() {
        return renderer;
    }
}
