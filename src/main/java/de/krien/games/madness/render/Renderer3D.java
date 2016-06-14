package de.krien.games.madness.render;

import de.krien.games.madness.render.voxel.Chunk;
import de.krien.games.madness.render.voxel.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class Renderer3D {

    public void draw(World world) {
        drawWorld(world);
        updateCamera(world);
    }

    private void drawWorld(World world) {
        Chunk[][] chunks = world.getChunks();
        for (int x = 0; x < chunks.length; x++) {
            for (int y = 0; y < chunks[0].length; y++) {
                Chunk chunk = chunks[x][y];
                GL11.glPushMatrix();
                GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, chunk.getVboVertexHandle());
                GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);
                GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, chunk.getVboTextureHandle());
                GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0L);
                GL11.glDrawArrays(GL11.GL_QUADS, 0, RenderConstants.CHUNK_BLOCK_COUNT * 24);
                GL11.glPopMatrix();
            }
        }
    }

    private void updateCamera(World world) {
        GL11.glLoadIdentity();
        GL11.glRotatef(world.getCamera().getRotationX(), 1.0f, 0.0f, 0.0f); //Yaw
        GL11.glRotatef(world.getCamera().getRotationY(), 0.0f, 1.0f, 0.0f); // Pitch
        GL11.glRotatef(world.getCamera().getRotationZ(), 0.0f, 0.0f, 1.0f); //Roll
        GL11.glTranslatef(world.getCamera().getPositionX(), world.getCamera().getPositionY(), world.getCamera().getPositionZ());
    }

}
