package de.krien.games.madness.view.voxel.util.texture;

import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public enum Texture {

    BORDER("res/VoxelTilesBorder.png"),
    BORDERLESS("res/VoxelTiles.png");

    private String path;

    Texture(String path) {
        this.path = path;
    }

    public org.newdawn.slick.opengl.Texture load() {
        try {
            return TextureLoader.getTexture("PNG", new FileInputStream(new File(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
