package de.krien.games.madness.input;

import de.krien.games.madness.view.camera.Camera;
import de.krien.games.madness.view.camera.DynamicCameraCalculator;
import de.krien.games.madness.view.ray.BlockSelector;
import de.krien.games.madness.view.voxel.World;
import de.krien.games.madness.view.voxel.util.texture.Texture;
import de.krien.games.madness.view.voxel.util.texture.TextureManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class CameraController {

    private final float ZOOM_SENSITY = 1.5f;
    private final float MOVEMENT_SENSITY = 1.0f;
    private final float ROLLING_SENSITY = 0.2f;
    private final float ROTATION_SENSITY = 1.0f;
    
    private DynamicCameraCalculator cameraUtil;
    private BlockSelector blockSelector;
    private Camera camera;
    
    public CameraController() {
        camera = Camera.INSTANCE;
        cameraUtil = new DynamicCameraCalculator(Camera.INSTANCE);
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
            System.out.println("RotationX: " + camera.getRotationX());
            System.out.println("RotationY: " + camera.getRotationY());
            System.out.println("RotationZ: " + camera.getRotationZ());

            System.out.println("PositionX: " + camera.getPositionX());
            System.out.println("PositionY: " + camera.getPositionY());
            System.out.println("PositionZ: " + camera.getPositionZ());

            System.out.println("Rad of RotationX: " + Math.toRadians(camera.getRotationX()));
            System.out.println("Rad of RotationY: " + Math.toRadians(camera.getRotationY()));
            System.out.println("Rad of RotationZ: " + Math.toRadians(camera.getRotationZ()));

            System.out.println("Sin of RotationX: " + Math.sin(Math.toRadians(camera.getRotationX())));
            System.out.println("Cos of RotationX: " + Math.cos(Math.toRadians(camera.getRotationX())));
            System.out.println("Sin of RotationY: " + Math.sin(Math.toRadians(camera.getRotationY())));
            System.out.println("Cos of RotationY: " + Math.cos(Math.toRadians(camera.getRotationY())));
            System.out.println("Sin of RotationZ: " + Math.sin(Math.toRadians(camera.getRotationZ())));
            System.out.println("Cos of RotationZ: " + Math.cos(Math.toRadians(camera.getRotationZ())));
            System.out.println("###############################");
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_1)) {
            TextureManager.INSTANCE.setActiveTexture(Texture.BORDERLESS);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_2)) {
            TextureManager.INSTANCE.setActiveTexture(Texture.BORDER);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_3)) {
            GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_FILL );
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_4)) {
            GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_LINE );
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_G)) {
            blockSelector.selectBlock();
        }

        // Zooming with Mouse Wheel
        int mouseWheelDelta = Mouse.getDWheel();

        if (mouseWheelDelta < 0) {
            camera.increasePositionZ(ZOOM_SENSITY);
        }
        if (mouseWheelDelta > 0) {
            camera.decreasePositionZ(ZOOM_SENSITY);
        }

        // Rolling with E and Q
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            camera.increaseRotationZ(ROLLING_SENSITY);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            camera.decreaseRotationZ(ROLLING_SENSITY);
        }

        // Rotation with Right-Click + Mouse Movement
        int mousePositionXDelta = Mouse.getDX();
        int mousePositionYDelta = -1 * Mouse.getDY();

        if (Mouse.isButtonDown(1) && mousePositionYDelta != 0) {
            camera.increaseRotationX(mousePositionYDelta * ROTATION_SENSITY);
        }
        if (Mouse.isButtonDown(1) && mousePositionXDelta != 0) {
            camera.increaseRotationZ(mousePositionXDelta * ROTATION_SENSITY);
        }
    }

}
