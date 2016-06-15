package de.krien.games.madness.view.render;

import com.momchil_atanasov.data.front.parser.*;
import de.krien.games.madness.view.mesh.Mesh;
import de.krien.games.madness.view.voxel.Chunk;
import de.krien.games.madness.view.voxel.World;
import de.krien.games.madness.view.voxel.util.texture.TextureManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class Renderer3D {

    Mesh mesh;

    public Renderer3D() {
        mesh = new Mesh();
        mesh.load();
    }

    public void draw(World world) {
        mesh.render();
        drawWorld(world);
        updateCamera(world);
    }

    private void drawWorld(World world) {
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

        loadWorldTexture();
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

    private void loadWorldTexture() {
        Color.white.bind(); //GL11.glColor4f(1f, 1f, 1f, 1f);
        TextureManager.INSTANCE.bindActiveTexture();
    }


}
