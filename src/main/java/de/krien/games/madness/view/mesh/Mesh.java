package de.krien.games.madness.view.mesh;

import com.momchil_atanasov.data.front.parser.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * Created by ig0re on 15.06.2016.
 */
public class Mesh {

    int verticesID;
    int normalsID;
    int textureID;

    ArrayList<Float> verticeList;
    ArrayList<Float> normalList;
    ArrayList<Float> textureList;

    Texture texture;


    public Mesh() {
        verticeList = new ArrayList<Float>();
        normalList = new ArrayList<Float>();
        textureList = new ArrayList<Float>();
    }

    public void render() {
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

        Color.white.bind(); //GL11.glColor4f(1f, 1f, 1f, 1f);
        texture.bind();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesID);
        GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalsID);
        GL11.glNormalPointer(GL11.GL_FLOAT, 0, 0L);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureID);
        GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0L);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, verticeList.size());
    }

    public void load() {
        OBJModel model = readObj();

        texture = de.krien.games.madness.view.voxel.util.texture.Texture.GUN.load();

        clearData();
        convertData(model);

        FloatBuffer vertices = BufferUtils.createFloatBuffer(verticeList.size());
        FloatBuffer normals = BufferUtils.createFloatBuffer(normalList.size());
        FloatBuffer textures = BufferUtils.createFloatBuffer(textureList.size());

        convertBuffers(vertices, normals, textures);
        generateBufferIDs();
        bindDataToBuffers(vertices, normals, textures);
    }

    private void bindDataToBuffers(FloatBuffer vertices, FloatBuffer normals, FloatBuffer textures) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalsID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normals, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textures, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void generateBufferIDs() {
        verticesID = GL15.glGenBuffers();
        normalsID = GL15.glGenBuffers();
        textureID = GL15.glGenBuffers();
    }

    private void convertBuffers(FloatBuffer vertices, FloatBuffer normals, FloatBuffer textures) {
        for(Float f : verticeList) {
            vertices.put(f);
        }
        vertices.flip();

        for(Float f : normalList) {
            normals.put(f);
        }
        normals.flip();

        for(Float f : textureList) {
            textures.put(f);
        }
        textures.flip();
    }

    private void convertData(OBJModel model) {
        for (OBJObject object : model.getObjects()) {
            for (OBJMesh mesh : object.getMeshes()) {
                for (OBJFace face : mesh.getFaces()) {
                    for (OBJDataReference reference : face.getReferences()) {
                        OBJVertex vertex = model.getVertex(reference);
                        verticeList.add(vertex.x);
                        verticeList.add(vertex.y);
                        verticeList.add(vertex.z);
                        OBJNormal normal = model.getNormal(reference);
                        normalList.add(normal.x);
                        normalList.add(normal.y);
                        normalList.add(normal.z);
                        OBJTexCoord texture = model.getTexCoord(reference);
                        textureList.add(texture.u);
                        textureList.add(texture.v);
                    }
                }
            }
        }
    }

    private void clearData() {
        verticeList.clear();
        normalList.clear();
        textureList.clear();
    }

    private OBJModel readObj() {
        OBJModel model = null;
        try {
            InputStream in = new FileInputStream("res/cube.obj");
            IOBJParser parser = new OBJParser();
            model = parser.parse(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }
}
