package de.krien.games.madness.render.debug;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.ray.Ray;
import de.krien.games.madness.render.voxel.Block;
import de.krien.games.madness.render.voxel.BlockType;
import de.krien.games.madness.render.voxel.Chunk;
import de.krien.games.madness.render.voxel.World;
import de.krien.games.madness.render.voxel.util.block.BlockPositionUtil;
import de.krien.games.madness.render.voxel.util.chunk.ChunkPositionUtil;
import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public enum RayPick {

    INSTANCE;

    private Vector3f rayPickedBlockPosition;
    private Block rayPickedBlock;
    private Chunk rayPickedChunk;

    public void updateSelection() {
        if(rayPickedBlock != null) {
            rayPickedBlock.setSelected(false);
            System.out.println("1");
        }
        updateActiveBlock();
        System.out.println("2");
        if (rayPickedBlock != null && rayPickedBlockPosition != null) {
            rayPickedBlock.setSelected(true);
            rayPickedBlock.setBlockType(BlockType.WOOD);
            rayPickedChunk.render();
            System.out.println("3");
        }
    }

    private void updateActiveBlock() {
        Vector3f cameraPositionAtToggleTime = new Vector3f(
                -1 * World.getInstance().getCamera().getPositionX(),
                -1 * World.getInstance().getCamera().getPositionY(),
                -1 * World.getInstance().getCamera().getPositionZ());
        Vector3f cameraRotationAtToggleTime = new Vector3f(
                World.getInstance().getCamera().getRotationX(),
                World.getInstance().getCamera().getRotationY(),
                World.getInstance().getCamera().getRotationZ());
        calculateFirstBlockInSight(cameraPositionAtToggleTime, cameraRotationAtToggleTime);
    }

    private void calculateFirstBlockInSight(Vector3f cameraPositionAtToggleTime, Vector3f cameraRotationAtToggleTime) {
        Ray ray = new Ray(cameraPositionAtToggleTime, cameraRotationAtToggleTime);
        for (float i = 0.1f; i < RenderConstants.VIEW_DISTANCE; i += RenderConstants.BLOCK_LENGTH) {
            Vector3f sightPoint = ray.getPoint(i);
            Chunk chunk = ChunkPositionUtil.getChunkOfPoint(sightPoint);
            if (chunk != null) {
                rayPickedChunk = chunk;
                rayPickedBlockPosition = BlockPositionUtil.getBlockPositionOfPoint(chunk, sightPoint);
                rayPickedBlock = BlockPositionUtil.getBlockOfPoint(chunk, sightPoint);

                if (rayPickedBlockPosition != null) {
                    return;
                } else {
                    continue;
                }
            }
        }
    }
}
