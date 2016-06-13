package de.krien.games.madness.input;

import de.krien.games.madness.render.camera.DynamicCameraUtil;
import de.krien.games.madness.render.debug.CameraSight;
import de.krien.games.madness.render.ray.BlockSelector;
import de.krien.games.madness.render.ray.RayPick;
import de.krien.games.madness.render.voxel.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class CameraController {

    private final float ZOOM_SENSITY = 1.5f;
    private final float MOVEMENT_SENSITY = 1.0f;
    private final float ROLLING_SENSITY = 0.2f;
    private final float ROTATION_SENSITY = 1.0f;

    private World world;
    private DynamicCameraUtil cameraUtil;
    private BlockSelector blockSelector;

    public CameraController() {
        world = World.getInstance();
        cameraUtil = new DynamicCameraUtil(world.getCamera());
        blockSelector = new BlockSelector();
    }

    public void processInput() {
        // Movement with WASD
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            cameraUtil.moveLeft(MOVEMENT_SENSITY);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            cameraUtil.moveRight(MOVEMENT_SENSITY);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            cameraUtil.moveBackward(MOVEMENT_SENSITY);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            cameraUtil.moveForward(MOVEMENT_SENSITY);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            System.out.println("RotationX: " + world.getCamera().getRotationX());
            System.out.println("RotationY: " + world.getCamera().getRotationY());
            System.out.println("RotationZ: " + world.getCamera().getRotationZ());

            System.out.println("PositionX: " + world.getCamera().getPositionX());
            System.out.println("PositionY: " + world.getCamera().getPositionY());
            System.out.println("PositionZ: " + world.getCamera().getPositionZ());

            System.out.println("Rad of RotationX: " + Math.toRadians(world.getCamera().getRotationX()));
            System.out.println("Rad of RotationY: " + Math.toRadians(world.getCamera().getRotationY()));
            System.out.println("Rad of RotationZ: " + Math.toRadians(world.getCamera().getRotationZ()));

            System.out.println("Sin of RotationX: " + Math.sin(Math.toRadians(world.getCamera().getRotationX())));
            System.out.println("Cos of RotationX: " + Math.cos(Math.toRadians(world.getCamera().getRotationX())));
            System.out.println("Sin of RotationY: " + Math.sin(Math.toRadians(world.getCamera().getRotationY())));
            System.out.println("Cos of RotationY: " + Math.cos(Math.toRadians(world.getCamera().getRotationY())));
            System.out.println("Sin of RotationZ: " + Math.sin(Math.toRadians(world.getCamera().getRotationZ())));
            System.out.println("Cos of RotationZ: " + Math.cos(Math.toRadians(world.getCamera().getRotationZ())));
            System.out.println("###############################");
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_F)) {
            CameraSight.INSTANCE.toggleActivity();
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_G)) {
            blockSelector.selectBlock();
        }

        // Zooming with Mouse Wheel
        int mouseWheelDelta = Mouse.getDWheel();

        if (mouseWheelDelta < 0) {
            world.getCamera().increasePositionZ(ZOOM_SENSITY);
        }
        if (mouseWheelDelta > 0) {
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
        int mousePositionXDelta = Mouse.getDX();
        int mousePositionYDelta = -1 * Mouse.getDY();

        if (Mouse.isButtonDown(1) && mousePositionYDelta != 0) {
            world.getCamera().increaseRotationX(mousePositionYDelta * ROTATION_SENSITY);
        }
        if (Mouse.isButtonDown(1) && mousePositionXDelta != 0) {
            world.getCamera().increaseRotationZ(mousePositionXDelta * ROTATION_SENSITY);
        }
    }

}
