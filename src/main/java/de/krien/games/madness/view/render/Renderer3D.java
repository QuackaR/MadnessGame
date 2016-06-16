package de.krien.games.madness.view.render;

import de.krien.games.madness.view.mesh.GameObject3D;
import de.krien.games.madness.view.mesh.Mesh;
import de.krien.games.madness.view.voxel.Chunk;
import de.krien.games.madness.view.voxel.World;
import de.krien.games.madness.view.voxel.util.texture.TextureManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;

import java.util.List;

public class Renderer3D {

    private List<GameObject3D> gameobjectList;

    public Renderer3D(List<GameObject3D> gameobjectList) {
        this.gameobjectList = gameobjectList;
    }

    public void draw() {
        for (GameObject3D gameObject : gameobjectList) {
            gameObject.draw();
        }
    }
}
