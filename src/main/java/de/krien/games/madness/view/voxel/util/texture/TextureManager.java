package de.krien.games.madness.view.voxel.util.texture;

import org.lwjgl.opengl.GL11;

public enum TextureManager {

    INSTANCE;

    private org.newdawn.slick.opengl.Texture activeTexture;

    public void bindActiveTexture() {
        if(activeTexture != null) {
            activeTexture.bind();
        }
    }

    public void setActiveTexture(Texture texture) {
        activeTexture = texture.load();
        bindActiveTexture();
    }

    public void unbindActiveTexture() {
        org.newdawn.slick.opengl.TextureImpl.bindNone();
    }

}
