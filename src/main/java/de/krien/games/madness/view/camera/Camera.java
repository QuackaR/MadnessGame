package de.krien.games.madness.view.camera;

import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private Vector3f position;
    private Vector3f rotation;

    public Camera() {
        position = new Vector3f();
        rotation = new Vector3f();
    }

    public void increasePositionX(float amount) {
        position.setX(position.getX() + amount);
    }

    public void increasePositionY(float amount) {
        position.setY(position.getY() + amount);
    }

    public void increasePositionZ(float amount) {
        position.setZ(position.getZ() + amount);
    }

    public void decreasePositionX(float amount) {
        position.setX(position.getX() - amount);
    }

    public void decreasePositionY(float amount) {
        position.setY(position.getY() - amount);
    }

    public void decreasePositionZ(float amount) {
        position.setZ(position.getZ() - amount);
    }

    public void increaseRotationX(float amount) {
        rotation.setX(rotation.getX() + amount);
    }

    public void increaseRotationY(float amount) {
        rotation.setY(rotation.getY() + amount);
    }

    public void increaseRotationZ(float amount) {
        rotation.setZ(rotation.getZ() + amount);
    }

    public void decreaseRotationX(float amount) {
        rotation.setX(rotation.getX() - amount);
    }

    public void decreaseRotationY(float amount) {
        rotation.setY(rotation.getY() - amount);
    }

    public void decreaseRotationZ(float amount) {
        rotation.setZ(rotation.getZ() - amount);
    }

    public float getPositionX() {
        return position.getX();
    }

    public void setPositionX(float positionX) {
        position.setX(positionX);
    }

    public float getPositionY() {
        return position.getY();
    }

    public void setPositionY(float positionY) {
        position.setY(positionY);
    }

    public float getPositionZ() {
        return position.getZ();
    }

    public void setPositionZ(float positionZ) {
        position.setZ(positionZ);
    }

    public float getRotationX() {
        return rotation.getX();
    }

    public void setRotationX(float rotationX) {
        rotation.setX(rotationX);
    }

    public float getRotationY() {
        return rotation.getY();
    }

    public void setRotationY(float rotationY) {
        rotation.setY(rotationY);
    }

    public float getRotationZ() {
        return rotation.getZ();
    }

    public void setRotationZ(float rotationZ) {
        rotation.setZ(rotationZ);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }
}
