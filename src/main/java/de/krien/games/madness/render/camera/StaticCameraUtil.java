package de.krien.games.madness.render.camera;

/**
 * Created by akrien on 08.06.2016.
 */
public class StaticCameraUtil {

    //MOVEMENT UP:              world.getCamera().decreasePositionZ(MOVEMENT_SENSITY);
    //DOWN:                     world.getCamera().increasePositionZ(MOVEMENT_SENSITY);
    //FORWARD:                  world.getCamera().decreasePositionY(MOVEMENT_SENSITY);
    //BACKWARD:                 world.getCamera().increasePositionY(MOVEMENT_SENSITY);
    //RIGHT:                    world.getCamera().decreasePositionX(MOVEMENT_SENSITY);
    //LEFT:                     world.getCamera().increasePositionX(MOVEMENT_SENSITY);

    //ROTATION RIGHT:           world.getCamera().increaseRotationZ(MOVEMENT_SENSITY);
    //ROTATION LEFT:            world.getCamera().decreaseRotationZ(MOVEMENT_SENSITY);
    //ROTATION DOWN:            world.getCamera().increaseRotationY(MOVEMENT_SENSITY);
    //ROTATION UP:              world.getCamera().decreaseRotationY(MOVEMENT_SENSITY);
    //ROTATION TURN RIGHT:      world.getCamera().increaseRotationX(MOVEMENT_SENSITY);
    //ROTATION TURN LEFT:       world.getCamera().decreaseRotationX(MOVEMENT_SENSITY);

    private Camera camera;

    public StaticCameraUtil(Camera camera) {
        this.camera = camera;
    }

    public void moveUp(float amount) {
        camera.decreasePositionZ(amount);
    }

    public void moveDown(float amount) {
        camera.increasePositionZ(amount);
    }

    public void moveRight(float amount) {
        camera.decreasePositionX(amount);
    }

    public void moveLeft(float amount) {
        camera.increasePositionX(amount);
    }

    public void moveForward(float amount) {
        camera.decreasePositionY(amount);
    }

    public void moveBackward(float amount) {
        camera.increasePositionY(amount);
    }

    public void turnUp(float amount) {
        camera.decreaseRotationY(amount);
    }

    public void turnDown(float amount) {
        camera.increaseRotationY(amount);
    }

    public void turnLeft(float amount) {
        camera.decreaseRotationZ(amount);
    }

    public void turnRight(float amount) {
        camera.increaseRotationZ(amount);
    }

}
