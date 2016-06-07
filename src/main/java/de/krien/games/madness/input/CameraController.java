package de.krien.games.madness.input;

import de.krien.games.madness.render.voxel.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class CameraController {

    private final float ZOOM_SENSITY = 1.5f;
    private final float MOVEMENT_SENSITY = 1.0f;
    private final float ROLLING_SENSITY = 0.2f;
    private final float ROTATION_SENSITY = 1.0f;

    private World world = World.getInstance();

    public void processInput() {
        int mouseWheelDelta = Mouse.getDWheel();
        int mousePositionXDelta = Mouse.getDX();
        int mousePositionYDelta = Mouse.getDY();

        // Movement with WASD
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            world.getCamera().increasePositionX(MOVEMENT_SENSITY);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            world.getCamera().decreasePositionX(MOVEMENT_SENSITY);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            world.getCamera().increasePositionY(MOVEMENT_SENSITY);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            world.getCamera().decreasePositionY(MOVEMENT_SENSITY);
        }

        // Zooming with Mouse Wheel
        if (mouseWheelDelta > 0) {
            world.getCamera().increasePositionZ(ZOOM_SENSITY);
        }
        if (mouseWheelDelta < 0) {
            world.getCamera().decreasePositionZ(ZOOM_SENSITY);
        }

        // Rolling with E and Q
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            world.getCamera().increaseRotationZ(ROLLING_SENSITY);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            world.getCamera().decreaseRotationZ(ROLLING_SENSITY);
        }

        // Rotation with Right-Click + Mouse Movement
        if (Mouse.isButtonDown(1) && mousePositionXDelta != 0) {
            world.getCamera().increaseRotationX(mousePositionXDelta * ROTATION_SENSITY);
        }
        if (Mouse.isButtonDown(1) && mousePositionYDelta != 0) {
            world.getCamera().increaseRotationY(mousePositionYDelta * ROTATION_SENSITY * -1);
        }

    }

}
