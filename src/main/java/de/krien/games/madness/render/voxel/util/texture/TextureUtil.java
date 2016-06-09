package de.krien.games.madness.render.voxel.util.texture;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public enum TextureUtil {

    INSTANCE("res/VoxelTiles.png");

    private String path;
    private org.newdawn.slick.opengl.Texture texture;

    private TextureUtil(String path) {
        this.path = path;
    }

    public void bind() {
        if(texture == null) {
            try {
                texture = TextureLoader.getTexture("PNG", new FileInputStream(new File(path)));
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
            } catch (IOException e) {
                e.printStackTrace();
            }
            texture.bind();
        }
    }

    public void unbind() {
        if(texture != null) {
            texture.release();
        }
    }


}
