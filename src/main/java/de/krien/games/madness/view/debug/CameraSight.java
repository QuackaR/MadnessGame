package de.krien.games.madness.view.debug;

import de.krien.games.madness.view.ray.Ray;
import de.krien.games.madness.view.voxel.World;
import org.lwjgl.util.vector.Vector3f;

public enum CameraSight {

    INSTANCE;

    private static final float CAMERA_SIGHT_DISTANCE = 25.0f;
    private static final float CAMERA_SIGHT_POINT_SIZE = 1.0f;

    private boolean shouldDrawCameraSight;

    private Vector3f cameraPositionAtToggleTime;
    private Vector3f cameraRotationAtToggleTime;

    private Vector3f cameraSightPoint;

    public void toggleActivity() {
        if (shouldDrawCameraSight == false) {
            updateCameraSightPoint();
            shouldDrawCameraSight = true;
        } else {
            shouldDrawCameraSight = false;
        }
    }

    private void updateCameraSightPoint() {
        cameraPositionAtToggleTime = new Vector3f(
                -1 * World.getInstance().getCamera().getPositionX(),
                -1 * World.getInstance().getCamera().getPositionY(),
                -1 * World.getInstance().getCamera().getPositionZ());
        cameraRotationAtToggleTime = new Vector3f(
                World.getInstance().getCamera().getRotationX(),
                World.getInstance().getCamera().getRotationY(),
                World.getInstance().getCamera().getRotationZ());

        Ray ray = new Ray(cameraPositionAtToggleTime, cameraRotationAtToggleTime);
        cameraSightPoint = ray.getPoint(CAMERA_SIGHT_DISTANCE);
    }

    public void drawCameraSight() {
        ImmediateDrawUtil.drawBlock(cameraPositionAtToggleTime, CAMERA_SIGHT_POINT_SIZE);
        ImmediateDrawUtil.drawBlock(cameraSightPoint, CAMERA_SIGHT_POINT_SIZE);
        ImmediateDrawUtil.drawLine(cameraPositionAtToggleTime, cameraSightPoint);
    }

    public boolean shouldDrawCameraSight() {
        return shouldDrawCameraSight;
    }
}
