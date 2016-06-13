package de.krien.games.madness.render.voxel.util.texture;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public enum TextureUtil {

    BORDER("res/VoxelTilesBorder.png"),
    BORDERLESS("res/VoxelTiles.png");

    private String path;
    private org.newdawn.slick.opengl.Texture texture;

    private TextureUtil(String path) {
        this.path = path;
    }

    public void bind() {
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream(new File(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        texture.bind();
    }
}
