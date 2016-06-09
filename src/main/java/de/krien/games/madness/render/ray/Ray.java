package de.krien.games.madness.render.ray;


import javax.vecmath.Vector3f;

public class Ray {

        public final Vector3f origin;
        public final Vector3f direction;

        public Ray(Vector3f origin, Vector3f direction) {
            this.origin = origin;
            this.direction = direction;
        }

        public Vector3f getEndPoint(Vector3f out, float distance) {
            Vector3f endPointVector = new Vector3f(direction);
            endPointVector.scale(distance);
            endPointVector.add(origin);
            return endPointVector;
        }

    public Vector3f getEndPoint(float distance) {
        float amount = 1;
        float bfMovementX = amount * (float)Math.sin(Math.toRadians(direction.z));
        float bfMovementY = amount * (float)Math.cos(Math.toRadians(direction.z));
        float bfMovementZ = amount * (float)Math.sin(Math.toRadians(direction.x + 90));

        Vector3f dest = new Vector3f(origin.x - bfMovementX, origin.y - bfMovementY, origin.z - bfMovementZ);

        dest.scale(distance);
        dest.add(origin);
        return dest;
    }
}
