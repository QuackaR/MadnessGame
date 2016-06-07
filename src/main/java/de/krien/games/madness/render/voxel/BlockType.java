package de.krien.games.madness.render.voxel;

import de.krien.games.madness.render.voxel.util.Texture;

public enum BlockType {

    BlockType_Default(0, Texture.GRASS),
    BlockType_Grass(1, Texture.GRASS),
    BlockType_Dirt(2, Texture.GRASS),
    BlockType_Water(3, Texture.GRASS),
    BlockType_Stone(4, Texture.GRASS),
    BlockType_Wood(5, Texture.GRASS),
    BlockType_Sand(6, Texture.GRASS),
    BlockType_NumTypes(7, Texture.GRASS);

    private int blockID;
    private Texture texture;

    BlockType(int blockID, Texture texture) {
        this.blockID = blockID;
        this.texture = texture;
    }

    public int getBlockID() {
        return blockID;
    }

    public org.newdawn.slick.opengl.Texture getTexture() {
        return texture.getTexture();
    }
}
