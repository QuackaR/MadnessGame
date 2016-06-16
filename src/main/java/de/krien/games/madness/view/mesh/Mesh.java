package de.krien.games.madness.view.mesh;

import de.krien.games.madness.util.ObjFileParser;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.nio.FloatBuffer;

public class Mesh implements GameObject3D {

    private final float DEFAULT_SCALE = 1;

    private String objPath;
    private String texturePath;

    private int verticesID;
    private int normalsID;
    private int textureID;

    private Texture texture;
    private ObjFileParser objFileParser;

    private float scale;
    private Vector3f position;
    private Vector3f rotation;

    public Mesh(String objPath, String texturePath) {
        this.objPath = objPath;
        this.texturePath = texturePath;
        scale = DEFAULT_SCALE;
        position = new Vector3f();
        rotation = new Vector3f();
    }

    public void draw() {
        enableRenderingOptions();
        bindTexture();
        drawMesh();
    }

    private void drawMesh() {
        GL11.glPushMatrix();
        {
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(position.getX(), position.getY(), position.getZ());
            GL11.glRotatef(rotation.getX(), 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(rotation.getY(), 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(rotation.getZ(), 0.0f, 0.0f, 1.0f);

            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesID);
            GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalsID);
            GL11.glNormalPointer(GL11.GL_FLOAT, 0, 0L);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureID);
            GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0L);

            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, objFileParser.getVertexBuffer().limit());
        }
        GL11.glPopMatrix();
    }

    private void bindTexture() {
        Color.white.bind(); //GL11.glColor4f(1f, 1f, 1f, 1f);
        texture.bind();
    }

    private void enableRenderingOptions() {
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
    }

    public void load() {
        ObjFileParser objFileParser = loadMesh();
        generateBufferIDs();
        loadTexture();
        bindDataToBuffers(
                objFileParser.getVertexBuffer(),
                objFileParser.getNormalBuffer(),
                objFileParser.getTextureBuffer()
        );

    }

    private ObjFileParser loadMesh() {
        objFileParser = new ObjFileParser();
        try {
            objFileParser.readObj(objPath);
        } catch (Exception e) {
            System.out.println("Could not read .obj-File: ");
            e.printStackTrace();
        }
        return objFileParser;
    }

    private void generateBufferIDs() {
        verticesID = GL15.glGenBuffers();
        normalsID = GL15.glGenBuffers();
        textureID = GL15.glGenBuffers();
    }

    private void loadTexture() {
        texture = de.krien.games.madness.view.voxel.util.texture.Texture.load(texturePath);
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

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }
}