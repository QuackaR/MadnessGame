package de.krien.games.madness.util;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.*;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class ObjFileParser {

    private List<Vector3f> vertexList;
    private List<Vector2f> textureList;
    private List<Vector3f> normalList;
    private List<Vector3i> facesVertexList;
    private List<Vector3i> facesTextureList;
    private List<Vector3i> facesNormalList;

    private FloatBuffer vertexBuffer;
    private FloatBuffer textureBuffer;
    private FloatBuffer normalBuffer;

    private String materialLibraryName;

    public ObjFileParser() {
        vertexList = new ArrayList<>();
        textureList = new ArrayList<>();
        normalList = new ArrayList<>();
        facesVertexList = new ArrayList<>();
        facesTextureList = new ArrayList<>();
        facesNormalList = new ArrayList<>();
    }

    public void readObj(String path) throws Exception {
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        scan(bufferedReader);
        generateVertexBuffer();
        generateTextureBuffer();
        generateNormalBuffer();
    }

    private void scan(BufferedReader bufferedReader) throws IOException {
        String line;
        while ((line = readLine(bufferedReader)) != null) {
            if (!line.isEmpty()) {
                String commandType = getCommandType(line);
                switch (commandType) {
                    case "v":
                        processVertex(line);
                        break;
                    case "vt":
                        processTexCoord(line);
                        break;
                    case "vn":
                        processNormal(line);
                        break;
                    case "f":
                        processFace(line);
                        break;
                    case "mtllib":
                        processMaterialLibrary(line);
                        break;
                }
            }
        }

    }

    private String readLine(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        if (line != null && line.endsWith("\\")) {
            StringBuilder stringBuilder = new StringBuilder();
            while (line != null && line.endsWith("\\")) {
                String lineContent = line.substring(0, line.length() - 1);
                stringBuilder.append(lineContent);
                line = bufferedReader.readLine();
            }
            if (line != null) {
                stringBuilder.append(line);
            }
            line = stringBuilder.toString().trim();
        }
        return line;
    }

    private String getCommandType(String line) {
        if (line.startsWith("#")) {
            return "#";
        }
        return line.split("[\\s]+")[0];
    }

    private void processVertex(String line) {
        String[] lineArray = line.split("[\\s]+");
        if (lineArray.length - 1 == 3) {
            float x = Float.valueOf(lineArray[1]);
            float y = Float.valueOf(lineArray[2]);
            float z = Float.valueOf(lineArray[3]);
            Vector3f vertex = new Vector3f(x, y, z);
            vertexList.add(vertex);
        }
    }

    private void processNormal(String line) {
        String[] lineArray = line.split("[\\s]+");
        if (lineArray.length - 1 == 3) {
            float x = Float.valueOf(lineArray[1]);
            float y = Float.valueOf(lineArray[2]);
            float z = Float.valueOf(lineArray[3]);
            Vector3f normal = new Vector3f(x, y, z);
            normalList.add(normal);
        }
    }

    private void processTexCoord(String line) {
        String[] lineArray = line.split("[\\s]+");
        if (lineArray.length - 1 == 2) {
            float x = Float.valueOf(lineArray[1]);
            float y = Float.valueOf(lineArray[2]);
            Vector2f texture = new Vector2f(x, y);
            textureList.add(texture);
        }
    }

    private void processFace(String line) {
        String[] lineArray = line.split("[\\s]+");
        String[] pos1 = lineArray[1].split("/");
        String[] pos2 = lineArray[2].split("/");
        String[] pos3 = lineArray[3].split("/");
        if(pos1.length == 3 && pos2.length == 3 && pos3.length == 3) {
            Vector3i vertex = new Vector3i(Integer.valueOf(pos1[0]), Integer.valueOf(pos2[0]), Integer.valueOf(pos3[0]));
            Vector3i texture = new Vector3i(Integer.valueOf(pos1[1]), Integer.valueOf(pos2[1]), Integer.valueOf(pos3[1]));
            Vector3i normal = new Vector3i(Integer.valueOf(pos1[2]), Integer.valueOf(pos2[2]), Integer.valueOf(pos3[2]));
            facesVertexList.add(vertex);
            facesTextureList.add(texture);
            facesNormalList.add(normal);
        }
    }

    private void processMaterialLibrary(String line) {
        String[] lineArray = line.split("[\\s]+");
        materialLibraryName = lineArray[1].trim();
    }

    private void generateVertexBuffer() {
        vertexBuffer = BufferUtils.createFloatBuffer(facesVertexList.size() * 3 * 3);
        for(Vector3i index : facesVertexList) {
            Vector3f vertex1 = vertexList.get(index.getX()-1);
            Vector3f vertex2 = vertexList.get(index.getY()-1);
            Vector3f vertex3 = vertexList.get(index.getZ()-1);
            vertexBuffer.put(vertex1.getX());
            vertexBuffer.put(vertex1.getY());
            vertexBuffer.put(vertex1.getZ());
            vertexBuffer.put(vertex2.getX());
            vertexBuffer.put(vertex2.getY());
            vertexBuffer.put(vertex2.getZ());
            vertexBuffer.put(vertex3.getX());
            vertexBuffer.put(vertex3.getY());
            vertexBuffer.put(vertex3.getZ());
        }
        vertexBuffer.flip();
    }

    private void generateTextureBuffer() {
        textureBuffer = BufferUtils.createFloatBuffer(facesTextureList.size() * 3 * 2);
        for(Vector3i index : facesTextureList) {
            Vector2f vertex1 = textureList.get(index.getX()-1);
            Vector2f vertex2 = textureList.get(index.getY()-1);
            Vector2f vertex3 = textureList.get(index.getZ()-1);
            textureBuffer.put(vertex1.getX());
            textureBuffer.put(vertex1.getY());
            textureBuffer.put(vertex2.getX());
            textureBuffer.put(vertex2.getY());
            textureBuffer.put(vertex3.getX());
            textureBuffer.put(vertex3.getY());
        }
        textureBuffer.flip();
    }

    public void generateNormalBuffer() {
        normalBuffer = BufferUtils.createFloatBuffer(facesNormalList.size() * 3 * 3);
        for(Vector3i index : facesNormalList) {
            Vector3f normal1 = normalList.get(index.getX()-1);
            Vector3f normal2 = normalList.get(index.getY()-1);
            Vector3f normal3 = normalList.get(index.getZ()-1);
            normalBuffer.put(normal1.getX());
            normalBuffer.put(normal1.getY());
            normalBuffer.put(normal1.getZ());
            normalBuffer.put(normal2.getX());
            normalBuffer.put(normal2.getY());
            normalBuffer.put(normal2.getZ());
            normalBuffer.put(normal3.getX());
            normalBuffer.put(normal3.getY());
            normalBuffer.put(normal3.getZ());
        }
        normalBuffer.flip();
    }

    public String getMaterialLibraryName() {
        return materialLibraryName;
    }

    public FloatBuffer getVertexBuffer() {
        return vertexBuffer;
    }

    public FloatBuffer getTextureBuffer() {
        return textureBuffer;
    }

    public FloatBuffer getNormalBuffer() {
        return normalBuffer;
    }
}
