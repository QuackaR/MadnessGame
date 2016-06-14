package de.krien.games.madness.view.voxel.util.texture;

/**
 * Created by akrien on 14.06.2016.
 */
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

}
