package de.krien.games.madness.render.ray;

import de.krien.games.madness.render.voxel.Block;
import de.krien.games.madness.render.voxel.Chunk;
import de.krien.games.madness.render.voxel.World;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by akrien on 13.06.2016.
 */
public class BlockSelector {

    private RayPick rayPick;
    private Vector3f position;
    private Vector3f rotation;

    public BlockSelector() {
        position = World.getInstance().getCamera().getPosition();
        rotation = World.getInstance().getCamera().getRotation();
        rayPick = new RayPick();
    }

    public void selectBlock() {
        setBlockInactive();
        rayPick.update(position, rotation);
        setBlockActive();
        rerender();
    }

    private void rerender() {
        if(rayPick.getRayPickedChunk() != null) {
            rayPick.getRayPickedChunk().render();
        }
    }

    private void setBlockActive() {
        if(rayPick.getRayPickedBlock() != null) {
            rayPick.getRayPickedBlock().setSelected(true);
        }
    }

    private void setBlockInactive() {
        if(rayPick.getRayPickedBlock() != null) {
            rayPick.getRayPickedBlock().setSelected(false);
        }
    }

    public Block getBlock() {
        return rayPick.getRayPickedBlock();
    }

    public Chunk getChunk() {
        return rayPick.getRayPickedChunk();
    }

    public Vector3f getPosition() {
        return rayPick.getRayPickedBlockPosition();
    }

    public Vector3f getIndex() {
        return rayPick.getRayPickedBlockIndex();
    }

}
