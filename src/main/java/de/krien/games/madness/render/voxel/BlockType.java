package de.krien.games.madness.render.voxel;

public enum BlockType {

    DEFAULT(0),
    GRASS(1),
    DIRT(2),
    STONE(3),
    WATER(4),
    WOOD(5),
    SAND(6);

    private int blockID;

    BlockType(int blockID) {
        this.blockID = blockID;
    }

    public int getBlockID() {
        return blockID;
    }
}
