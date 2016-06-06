package de.krien.games.madness.render.voxel;

public enum BlockType {

    BlockType_Default(0, null),
    BlockType_Grass(1, new float[]{0, 1, 0}),
    BlockType_Dirt(2, new float[]{1, 0.5f, 0}),
    BlockType_Water(3, new float[]{0, 0f, 1f}),
    BlockType_Stone(4, new float[]{1, 1, 1}),
    BlockType_Wood(5, new float[]{1, 1, 1}),
    BlockType_Sand(6, new float[]{1, 1, 1}),
    BlockType_NumTypes(7, new float[]{1, 1, 1});

    private int blockID;
    private float[] color;

    BlockType(int blockID, float[] color) {
        this.blockID = blockID;
        this.color = color;
    }

    public int getBlockID() {
        return blockID;
    }

    public float[] getColor() {
        return color;
    }
}
