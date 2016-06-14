package de.krien.games.madness.render;

import de.krien.games.madness.render.hud.stats.FpsHudRenderer;
import de.krien.games.madness.render.voxel.World;
import de.krien.games.madness.render.voxel.util.texture.Texture;
import de.krien.games.madness.render.voxel.util.texture.TextureManager;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import java.awt.Font;

public class Renderer2D {

    private FpsHudRenderer fpsHudRenderer;

    private Font font;
    private TrueTypeFont typeFont;

    public Renderer2D() {
        fpsHudRenderer = new FpsHudRenderer();
        font = new Font("Times New Roman", Font.BOLD, 24);
        typeFont = new TrueTypeFont(font, false);
    }

    public void draw(World world) {
        fpsHudRenderer.drawFps();
        begin2d();
        Color.green.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(100,100);
        GL11.glVertex2f(100+200,100);
        GL11.glVertex2f(100+200,100+200);
        GL11.glVertex2f(100,100+200);
        GL11.glEnd();
        typeFont.drawString(50, 50, "Test", Color.cyan);
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
        Color.white.bind(); //GL11.glColor4f(1f, 1f, 1f, 1f);
        TextureManager.INSTANCE.bindActiveTexture();
    }

}
