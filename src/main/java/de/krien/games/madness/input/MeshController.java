package de.krien.games.madness.input;

import de.krien.games.madness.view.camera.Camera;
import de.krien.games.madness.view.camera.DynamicCameraCalculator;
import de.krien.games.madness.view.mesh.Mesh;
import de.krien.games.madness.view.ray.BlockSelector;
import de.krien.games.madness.view.voxel.util.texture.Texture;
import de.krien.games.madness.view.voxel.util.texture.TextureManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class MeshController {

    private final float ZOOM_SENSITY = 1.5f;
    private final float MOVEMENT_SENSITY = 1.0f;
    private final float ROLLING_SENSITY = 0.2f;
    private final float ROTATION_SENSITY = 1.0f;

    private Mesh mesh;

    public MeshController(Mesh mesh) {
        this.mesh = mesh;
    }

    public void processInput() {
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            Vector3f meshPosition = mesh.getPosition();
            meshPosition.setX(meshPosition.getX() - MOVEMENT_SENSITY);
            mesh.setPosition(meshPosition);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            Vector3f meshPosition = mesh.getPosition();
            meshPosition.setY(meshPosition.getY() + MOVEMENT_SENSITY);
            mesh.setPosition(meshPosition);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            Vector3f meshPosition = mesh.getPosition();
            meshPosition.setY(meshPosition.getY() - MOVEMENT_SENSITY);
            mesh.setPosition(meshPosition);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            Vector3f meshPosition = mesh.getPosition();
            meshPosition.setX(meshPosition.getX() + MOVEMENT_SENSITY);
            mesh.setPosition(meshPosition);
        }
    }

}
