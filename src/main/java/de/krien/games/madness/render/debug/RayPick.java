package de.krien.games.madness.render.debug;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.ray.Ray;
import de.krien.games.madness.render.voxel.Block;
import de.krien.games.madness.render.voxel.BlockType;
import de.krien.games.madness.render.voxel.Chunk;
import de.krien.games.madness.render.voxel.World;
import de.krien.games.madness.render.voxel.util.block.BlockPositionUtil;
import de.krien.games.madness.render.voxel.util.chunk.ChunkPositionUtil;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public enum RayPick {

    INSTANCE;

    private static final float CAMERA_SIGHT_DISTANCE = 25.0f;
    private static final float CAMERA_SIGHT_POINT_SIZE = 1.0f;

    private boolean shouldDrawLineToRayPickedBlock;

    private Vector3f cameraPositionAtToggleTime;
    private Vector3f cameraRotationAtToggleTime;

    private Vector3f rayPickedBlockPosition;
    private Block rayPickedBlock;
    private Chunk rayPickedChunk;

    public void toggleActivity() {
        if (shouldDrawLineToRayPickedBlock == false) {
            updateActiveBlock();
            if(rayPickedBlock != null && rayPickedBlockPosition != null) {
                shouldDrawLineToRayPickedBlock = true;
                rayPickedBlock.setBlockType(BlockType.WOOD);
                rayPickedChunk.render();
            }
        } else {
            shouldDrawLineToRayPickedBlock = false;
        }
    }

    private void updateActiveBlock() {
         cameraPositionAtToggleTime = new Vector3f(
                -1 * World.getInstance().getCamera().getPositionX(),
                -1 * World.getInstance().getCamera().getPositionY(),
                -1 * World.getInstance().getCamera().getPositionZ());
        cameraRotationAtToggleTime = new Vector3f(
                World.getInstance().getCamera().getRotationX(),
                World.getInstance().getCamera().getRotationY(),
                World.getInstance().getCamera().getRotationZ());

        rayPickedBlockPosition = getPositionOfFirstBlockInSight();
        rayPickedBlock = getFirstBlockInSight();
    }

    private Block getFirstBlockInSight() {
        Ray ray = new Ray(cameraPositionAtToggleTime, cameraRotationAtToggleTime);
        for(float i = 0.1f; i < RenderConstants.VIEW_DISTANCE; i+= (RenderConstants.BLOCK_LENGTH)) {
            Vector3f sightPoint = ray.getPoint(i);
            Chunk chunk = ChunkPositionUtil.getChunkOfPoint(sightPoint);
            if(chunk != null) {
                rayPickedChunk = chunk;
                return BlockPositionUtil.getBlockOfPoint(chunk, sightPoint);
            }
        }
        return null;
    }

    private Vector3f getPositionOfFirstBlockInSight() {
        Ray ray = new Ray(cameraPositionAtToggleTime, cameraRotationAtToggleTime);
        for(float i = 0.1f; i < RenderConstants.VIEW_DISTANCE; i+= RenderConstants.BLOCK_LENGTH) {
            Vector3f sightPoint = ray.getPoint(i);
            Chunk chunk = ChunkPositionUtil.getChunkOfPoint(sightPoint);
            if(chunk != null) {
                return BlockPositionUtil.getBlockPositionOfPoint(chunk, sightPoint);
            }
        }
        return null;
    }

    public void drawLineToRayPickedBlock() {
        //ImmediateDrawUtil.drawBlock(cameraPositionAtToggleTime, CAMERA_SIGHT_POINT_SIZE);
        //ImmediateDrawUtil.drawBlock(rayPickedBlockPosition, CAMERA_SIGHT_POINT_SIZE);
        ImmediateDrawUtil.drawLine(cameraPositionAtToggleTime, rayPickedBlockPosition);
    }

    public boolean shouldDrawLineToRayPickedBlock() {
        return shouldDrawLineToRayPickedBlock;
    }
}
