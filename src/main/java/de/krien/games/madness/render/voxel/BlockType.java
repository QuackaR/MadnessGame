package de.krien.games.madness.render.voxel;

public enum BlockType {

    BlockType_Default(0),
    BlockType_Grass(1),
    BlockType_Dirt(2),
    BlockType_Water(3),
    BlockType_Stone(4),
    BlockType_Wood(5),
    BlockType_Sand(6),
    BlockType_NumTypes(7);

    private int BlockID;

    BlockType(int i) {
        BlockID = i;
    }

    public int getID() {
        return BlockID;
    }
}
