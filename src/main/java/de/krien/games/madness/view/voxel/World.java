package de.krien.games.madness.view.voxel;

import de.krien.games.madness.view.camera.Camera;
import de.krien.games.madness.view.mesh.GameObject3D;
import de.krien.games.madness.view.render.RenderConstants;
import de.krien.games.madness.view.voxel.util.chunk.ChunkGenerator;
import de.krien.games.madness.view.voxel.util.texture.TextureManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public class World implements GameObject3D{

    private static World instance;
    private Chunk[][] chunks;

    private World() {
        chunks = new Chunk[RenderConstants.WORLD_SIZE_X][RenderConstants.WORLD_SIZE_Y];
        for(int x = 0; x < chunks.length; x++) {
            for(int y = 0; y < chunks[x].length; y++) {
                Chunk chunk = new ChunkGenerator().generateLandscapeChunk(new Vector2f(x, y));
                chunk.render();
                chunks[x][y] = chunk;
            }
        }
    }

    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

    public void draw() {
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

        loadWorldTexture();
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

    private void loadWorldTexture() {
        Color.white.bind(); //GL11.glColor4f(1f, 1f, 1f, 1f);
        TextureManager.INSTANCE.bindActiveTexture();
    }

    public Chunk[][] getChunks() {
        return chunks;
    }

    public Chunk getChunk(int x, int y) {
        return chunks[x][y];
    }

    public void setChunk(int x, int y, Chunk chunk) {
        this.chunks[x][y] = chunk;
    }
}
