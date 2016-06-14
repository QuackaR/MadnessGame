package de.krien.games.madness.render;

import de.krien.games.madness.render.hud.stats.FpsHudRenderer;
import de.krien.games.madness.render.voxel.World;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Renderer2D {

    private FpsHudRenderer fpsHudRenderer;

    public Renderer2D() {
        fpsHudRenderer = new FpsHudRenderer();
    }

    public void draw(World world) {
        fpsHudRenderer.drawFps();
        begin2d();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(100,100);
        GL11.glVertex2f(100+200,100);
        GL11.glVertex2f(100+200,100+200);
        GL11.glVertex2f(100,100+200);
        GL11.glEnd();
        end2d();
    }

    private void begin2d() {
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
        GL11.glOrtho(0, Display.getDisplayMode().getWidth(), 0, Display.getDisplayMode().getHeight(), -1, 1);
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
