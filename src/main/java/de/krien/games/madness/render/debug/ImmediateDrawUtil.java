package de.krien.games.madness.render.debug;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class ImmediateDrawUtil {

    public static void drawBlock(Vector3f position, float size) {
        GL11.glBegin(GL11.GL_QUADS);
        //TOP
        GL11.glVertex3f(position.x + size, position.y - size, position.z);
        GL11.glVertex3f(position.x - size, position.y - size, position.z);
        GL11.glVertex3f(position.x - size, position.y + size, position.z);
        GL11.glVertex3f(position.x + size, position.y + size, position.z);
        //BOT
        GL11.glVertex3f(position.x + size, position.y - size, position.z - 2 * size);
        GL11.glVertex3f(position.x - size, position.y - size, position.z - 2 * size);
        GL11.glVertex3f(position.x - size, position.y + size, position.z - 2 * size);
        GL11.glVertex3f(position.x + size, position.y + size, position.z - 2 * size);
        //FRONT
        GL11.glVertex3f(position.x + size, position.y - size, position.z - 2 * size);
        GL11.glVertex3f(position.x - size, position.y - size, position.z - 2 * size);
        GL11.glVertex3f(position.x - size, position.y - size, position.z);
        GL11.glVertex3f(position.x + size, position.y - size, position.z);
        //BACK
        GL11.glVertex3f(position.x + size, position.y + size, position.z);
        GL11.glVertex3f(position.x - size, position.y + size, position.z);
        GL11.glVertex3f(position.x - size, position.y + size, position.z - 2 * size);
        GL11.glVertex3f(position.x + size, position.y + size, position.z - 2 * size);
        //LEFT
        GL11.glVertex3f(position.x - size, position.y + size, position.z - 2 * size);
        GL11.glVertex3f(position.x - size, position.y + size, position.z);
        GL11.glVertex3f(position.x - size, position.y - size, position.z);
        GL11.glVertex3f(position.x - size, position.y - size, position.z - 2 * size);
        //RIGHT
        GL11.glVertex3f(position.x + size, position.y + size, position.z);
        GL11.glVertex3f(position.x + size, position.y + size, position.z - 2 * size);
        GL11.glVertex3f(position.x + size, position.y - size, position.z - 2 * size);
        GL11.glVertex3f(position.x + size, position.y - size, position.z);
        GL11.glEnd();
    }

    public static void drawLine(Vector3f origin, Vector3f destination) {
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3f(origin.x, origin.y, origin.z);
        GL11.glVertex3f(destination.x, destination.y, destination.z);
        GL11.glEnd();
    }

}
