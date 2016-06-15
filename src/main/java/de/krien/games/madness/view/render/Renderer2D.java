package de.krien.games.madness.view.render;

import de.krien.games.madness.view.hud.crosshair.Crosshair;
import de.krien.games.madness.view.hud.stats.Stats;
import de.krien.games.madness.view.voxel.World;
import de.krien.games.madness.view.voxel.util.texture.TextureManager;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Renderer2D {

    private Stats stats;

    private Crosshair crosshair;

    public Renderer2D() {
        stats = new Stats();
        crosshair = new Crosshair(20, 5);
    }

    public void draw(World world) {
        begin2d();
        crosshair.draw();
        stats.draw();
        end2d();
    }

    private void begin2d() {
        TextureManager.INSTANCE.unbindActiveTexture();
        // Tiefentest deaktivieren
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        // Blending aktivieren
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        // Projektionsmatrix bearbeiten
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        // Projektionsmatrix auf Matrix-Stack sichern
        GL11.glPushMatrix();
        // Projektionsmatrix zurücksetzen
        GL11.glLoadIdentity();
        // Orthographische Perspektive setzen
        GL11.glOrtho(0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight(), 0, -1, 1);
        // "Objekttransformationsmatrix" bearbeiten
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        // "Objekttransformationsmatrix" auf Matrix-Stack sichern
        GL11.glPushMatrix();
        // "Objekttransformationsmatrix" zurücksetzen
        GL11.glLoadIdentity();
    }

    private void end2d() {
        // Tiefentest aktivieren
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        // Blending deaktivieren
        GL11.glDisable(GL11.GL_BLEND);
        // Projektionsmatrix bearbeiten
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        // Alte in begin2D auf den Matrix-Stack gelegte Matrix wiederherstellen
        GL11.glPopMatrix();
        // "Objekttransformationsmatrix" bearbeiten
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        // Alte in begin2D auf den Matrix-Stack gelegte Matrix wiederherstellen
        GL11.glPopMatrix();
    }


}
