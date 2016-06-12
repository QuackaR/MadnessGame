package de.krien.games.madness.render.ray;

import org.lwjgl.util.vector.Vector3f;

public class Ray {

    private Vector3f origin; //Position
    private Vector3f direction; //Rotation

    public Ray(Vector3f origin, Vector3f direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vector3f getPoint(float distance) {
        Vector3f point = new Vector3f();
        float deltaX = -1 * distance * (float) Math.sin(Math.toRadians(direction.z)) * (float)Math.cos(Math.toRadians(direction.x + 90));
        float deltaY = -1 * distance * (float) Math.cos(Math.toRadians(direction.z)) * (float)Math.cos(Math.toRadians(direction.x + 90));
        float deltaZ = -1 * distance * (float) Math.sin(Math.toRadians(direction.x + 90));
        point.setX(origin.x - deltaX);
        point.setY(origin.y - deltaY);
        point.setZ(origin.z + deltaZ);
        return point;
    }

    public Vector3f getOrigin() {
        return origin;
    }

    public void setOrigin(Vector3f origin) {
        this.origin = origin;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }
}
