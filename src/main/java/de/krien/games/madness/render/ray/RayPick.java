package de.krien.games.madness.render.ray;

import de.krien.games.madness.render.RenderConstants;
import de.krien.games.madness.render.voxel.Block;
import de.krien.games.madness.render.voxel.Chunk;
import de.krien.games.madness.render.voxel.util.block.BlockPositionUtil;
import de.krien.games.madness.render.voxel.util.chunk.ChunkPositionUtil;
import de.krien.games.madness.util.Vector3i;
import org.lwjgl.util.vector.Vector3f;

public class RayPick {

    private Vector3f rayPickedBlockPosition;
    private Vector3i rayPickedBlockIndex;
    private Block rayPickedBlock;
    private Chunk rayPickedChunk;

    BlockPositionUtil blockPositionUtil;

    public RayPick() {
        blockPositionUtil = new BlockPositionUtil();
    }

    public void update(Vector3f position, Vector3f rotation) {
        Vector3f originPosition = new Vector3f(-1 * position.getX(), -1 * position.getY(), -1 * position.getZ());
        Vector3f originRotation = new Vector3f(rotation.getX(), rotation.getY(), rotation.getZ());
        calculateFirstBlockInSight(originPosition, originRotation);
    }

    private void calculateFirstBlockInSight(Vector3f position, Vector3f rotation) {
        Ray ray = new Ray(position, rotation);
        for (float i = 0.1f; i < 25.0f; i += RenderConstants.BLOCK_LENGTH) {
            Vector3f sightPoint = ray.getPoint(i);
            Chunk chunk = ChunkPositionUtil.getChunkOfPoint(sightPoint);
            if (chunk != null) {
                blockPositionUtil.calculate(chunk, sightPoint);
                if (blockPositionUtil.getCalculatedBlock() != null) {
                    rayPickedBlock = blockPositionUtil.getCalculatedBlock();
                    rayPickedChunk = blockPositionUtil.getCalculatedChunk();
                    rayPickedBlockPosition = blockPositionUtil.getCalculatedBlockPosition();
                    rayPickedBlockIndex = blockPositionUtil.getCalculatedBlockIndex();
                    return;
                } else {
                    continue;
                }
            }
        }
    }

    public Vector3f getRayPickedBlockPosition() {
        return rayPickedBlockPosition;
    }

    public Vector3i getRayPickedBlockIndex() {
        return rayPickedBlockIndex;
    }

    public Block getRayPickedBlock() {
        return rayPickedBlock;
    }

    public Chunk getRayPickedChunk() {
        return rayPickedChunk;
    }
}
