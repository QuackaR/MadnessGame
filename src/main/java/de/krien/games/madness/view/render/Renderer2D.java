package de.krien.games.madness.view.render;

import de.krien.games.madness.view.hud.stats.FpsUtil;
import de.krien.games.madness.view.voxel.World;
import de.krien.games.madness.view.voxel.util.texture.TextureManager;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import java.awt.Font;

public class Renderer2D {

    private FpsUtil fpsUtil;

    private Font font;
    private TrueTypeFont typeFont;

    public Renderer2D() {
        fpsUtil = new FpsUtil();
        font = new Font("Times New Roman", Font.BOLD, 16);
        typeFont = new TrueTypeFont(font, false);
    }

    public void draw(World world) {
        begin2d();
        drawCrosshair();
        drawFps();
        end2d();
    }

    private void drawFps() {
        fpsUtil.updateFps();
        typeFont.drawString(5, 5, "FPS: " + fpsUtil.getFramesPerSecond(), Color.black);
    }

    private void drawCrosshair() {
        int midX = Display.getDisplayMode().getWidth() / 2;
        int midY = Display.getDisplayMode().getHeight() / 2;

        int crosshairSize = 25;

        GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(midX,midY - crosshairSize);
        GL11.glVertex2f(midX+2,midY - crosshairSize);
        GL11.glVertex2f(midX+2,midY+crosshairSize);
        GL11.glVertex2f(midX,midY+crosshairSize);
        GL11.glEnd();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(midX - crosshairSize, midY);
        GL11.glVertex2f(midX - crosshairSize, midY +2);
        GL11.glVertex2f(midX + crosshairSize, midY+2);
        GL11.glVertex2f(midX + crosshairSize, midY);
        GL11.glEnd();
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
        GL11.glOrtho(0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight(),0, -1, 1);
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
