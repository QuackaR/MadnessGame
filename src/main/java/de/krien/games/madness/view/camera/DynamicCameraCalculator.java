package de.krien.games.madness.view.camera;

public class DynamicCameraCalculator {

    private Camera camera;

    public DynamicCameraCalculator(Camera camera) {
        this.camera = camera;
    }

    public void moveForward(float amount) {
        float bfMovementX = amount * (float)Math.sin(Math.toRadians(camera.getRotationZ())) * (float)Math.cos(Math.toRadians(camera.getRotationX() + 90));
        float bfMovementY = amount * (float)Math.cos(Math.toRadians(camera.getRotationZ())) * (float)Math.cos(Math.toRadians(camera.getRotationX() + 90));
        float bfMovementZ = amount * (float)Math.sin(Math.toRadians(camera.getRotationX() + 90));
        camera.decreasePositionX(bfMovementX);
        camera.decreasePositionY(bfMovementY);
        camera.increasePositionZ(bfMovementZ);
    }

    public void moveBackward(float amount) {
        float bfMovementX = amount * (float)Math.sin(Math.toRadians(camera.getRotationZ())) * (float)Math.cos(Math.toRadians(camera.getRotationX() + 90));
        float bfMovementY = amount * (float)Math.cos(Math.toRadians(camera.getRotationZ())) * (float)Math.cos(Math.toRadians(camera.getRotationX() + 90));
        float bfMovementZ = amount * (float)Math.sin(Math.toRadians(camera.getRotationX() + 90));
        camera.increasePositionX(bfMovementX);
        camera.increasePositionY(bfMovementY);
        camera.decreasePositionZ(bfMovementZ);
    }

    public void moveLeft(float amount) {
        float lrMovementX = amount * (float)Math.sin(Math.toRadians(camera.getRotationZ() - 90));
        float lrMovementY = amount * (float)Math.cos(Math.toRadians(camera.getRotationZ() - 90));
        camera.decreasePositionX(lrMovementX);
        camera.decreasePositionY(lrMovementY);
    }

    public void moveRight(float amount) {
        float lrMovementX = amount * (float)Math.sin(Math.toRadians(camera.getRotationZ() - 90));
        float lrMovementY = amount * (float)Math.cos(Math.toRadians(camera.getRotationZ() - 90));
        camera.increasePositionX(lrMovementX);
        camera.increasePositionY(lrMovementY);
    }
}
