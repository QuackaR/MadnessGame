package de.krien.games.madness.render.voxel.util;

import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public enum Texture {

    GRASS("res/grass_top.jpg");

    private String path;
    private org.newdawn.slick.opengl.Texture texture;

    private Texture(String path) {
        this.path = path;
    }

    public org.newdawn.slick.opengl.Texture getTexture() {
        if(texture == null) {
            try {
                texture = TextureLoader.getTexture("JPG", new FileInputStream(new File(path)));
                texture.bind();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return texture;
    }


}
