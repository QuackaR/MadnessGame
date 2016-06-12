package de.krien.games.madness.render.voxel;

public class Block {

    private boolean isActive;
    private boolean isSelected;
    private BlockType blockType;

    public Block(BlockType blockType) {
        this.blockType = blockType;
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }
}
