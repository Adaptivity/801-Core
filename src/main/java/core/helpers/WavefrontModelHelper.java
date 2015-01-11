package core.helpers;

import core.Core;
import core.common.resources.CoreEnums;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import org.lwjgl.opengl.GL11;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Master801 on 12/7/2014 at 8:59 AM.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public final class WavefrontModelHelper {

    private static final Map<String, ModelWavefront> MODEL_WAVEFRONT_CACHE = new HashMap<String, ModelWavefront>();

    public static ModelWavefront loadWavefrontModel(ResourceLocation resourceLocation) {
        return WavefrontModelHelper.loadWavefrontModel(RandomHelper.convertResourceLocationToString(resourceLocation));
    }

    public static ModelWavefront loadWavefrontModel(String path) {
        if (path == null) {
            return null;
        }
        ModelWavefront model = WavefrontModelHelper.MODEL_WAVEFRONT_CACHE.get(path);
        if (model != null) {
            return model;
        }
        InputStream stream = ResourcePackHelper.getInputStreamFromCurrentPack(path);//Checks if the resource pack has this resource.
        if (stream == null) {
            stream = InputStreamHelper.getStreamFromResource(path, false);//Last resort
        }
        if (stream == null) {//Ensures that the resource is non-existing after the attempt to revive it.
            return null;
        }
        try {
            model = new ModelWavefront(stream).readLines();
        } finally {
            InputStreamHelper.closeInputStream(stream);
        }
        WavefrontModelHelper.MODEL_WAVEFRONT_CACHE.put(path, model);
        return model;
    }

    protected static void clearModelCache() {
        WavefrontModelHelper.MODEL_WAVEFRONT_CACHE.clear();
    }

    /**
     * Model class for Wavefront objects.
     * @author Master801
     */
    public static final class ModelWavefront {

        private final InputStream stream;
        private final List<String> groups = new ArrayList<String>();
        private final List<Face> faces = new ArrayList<Face>();
        private final List<Vertex3f> vertices3f = new ArrayList<Vertex3f>();
        private final List<Vertex4f> vertices4f = new ArrayList<Vertex4f>();

        protected ModelWavefront(InputStream stream) {
            this.stream = stream;
        }

        /**
         * Renders only this group.
         */
        public void renderGroup(String group) {
            for(String gotGroup : groups) {
                if (!(gotGroup.equals(group))) {
                    groups.remove(gotGroup);
                }
            }
            render();
        }

        /**
         * Renders everything.
         */
        public void renderEverything() {
            render();
        }

        /**
         * Renders everything except for these groups.
         */
        public void renderEverythingExcludingGroups(String[] groups) {
            for(String group : groups) {
                this.groups.remove(group);
            }
            render();
        }

        ModelWavefront readLines() {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String nextLine = null;
            while((nextLine = ReadingHelper.readLineFromBufferedReader(reader)) != null) {
                if (nextLine.startsWith("# ")) {//This checks if the line is a comment.
                    continue;//Skip the line since it is a comment.
                }
                if (nextLine.startsWith("g ")) {
                    ListHelper.addObjectToListWhileChecking(groups, nextLine.split("g ")[1]);
                } else if (nextLine.startsWith("o ")) {
                    ListHelper.addObjectToListWhileChecking(groups, nextLine.split("o ")[1]);
                    final String error = "You're using an object's name as the group! Make sure to change your modeler's settings to export as group names and not object names!";
                    LoggerHelper.addAdvancedMessageToLogger(Core.instance, CoreEnums.LoggerEnum.WARN, error);
                }
                if (nextLine.startsWith("f ")) {
                    String[] splitFaces = nextLine.split("f ");
                    float[] faces = new float[splitFaces.length];
                    for(int i = 0; i < faces.length; i++) {
                        if (StringHelper.containsCharacter(splitFaces[i], '/') || StringHelper.containsCharacter(splitFaces[i], '\\')) {
                            faces[i] = RandomHelper.convertStringToFloat(splitFaces[i]);
                        }
                    }
                    if (RandomHelper.doesArrayContainAnObject(RandomHelper.convertFloatArrayToFloatArray(faces))) {
                        ListHelper.addObjectToListWhileChecking(this.faces, new Face(faces));
                    }
                }
                if (nextLine.startsWith("v ")) {
                    String[] splitVertice = nextLine.split(" ");
                    if (splitVertice.length == 2) {//Checks if it's a triangle vertex.
                        ListHelper.addObjectToListWhileChecking(vertices3f, new Vertex3f(RandomHelper.convertFloatArrayToFloatArray(RandomHelper.convertStringArrayToFloatArray(splitVertice))));
                    } else if (splitVertice.length == 3) {//Checks if it's a quad vertex.
                        ListHelper.addObjectToListWhileChecking(vertices4f, new Vertex4f(RandomHelper.convertFloatArrayToFloatArray(RandomHelper.convertStringArrayToFloatArray(splitVertice))));
                    }
                }
            }
            ReadingHelper.closeBufferedReader(reader);
            return this;
        }

        private void render() {//Make this available only to this class.
            GLHelper.glColour4f();
            if (!vertices3f.isEmpty()) {
                GL11.glBegin(GL11.GL_TRIANGLES);
            }
            if (!vertices4f.isEmpty()) {
                GL11.glBegin(GL11.GL_QUADS);
            }
            for(Face face : faces) {
                if (!vertices3f.isEmpty()) {
                }
                if (!vertices4f.isEmpty()) {
                }
            }
            if (!vertices3f.isEmpty()) {
                GL11.glEnd();
            }
            if (!vertices4f.isEmpty()) {
                GL11.glEnd();
            }
        }
    }

    /**
     * Only used for triangle vertices.
     * @author Master801
     */
    public static final class Vertex3f {

        private float vertex0 = 0.0F, vertex1 = 0.0F, vertex3 = 0.0F;

        Vertex3f(float vertex0, float vertex1, float vertex3) {
            this.vertex0 = vertex0;
            this.vertex1 = vertex1;
            this.vertex3 = vertex3;
        }

        Vertex3f(float[] vertices) {
            this(vertices[0], vertices[1], vertices[2]);
        }

        public float getVertex0() {
            return vertex0;
        }

        public float getVertex1() {
            return vertex1;
        }

        public float getVertex2() {
            return vertex3;
        }

        public float[] getVerticesInArray() {
            return new float[] { getVertex0(), getVertex1(), getVertex2() };
        }

        public void setVertex0(float newVertex) {
            vertex0 = newVertex;
        }

        public void setVertex1(float newVertex) {
            vertex1 = newVertex;
        }

        public void setVertex2(float newVertex) {
            vertex3 = newVertex;
        }

        public void setVirtices(float newVextex0, float newVextex1, float newVextex2) {
            setVertex0(newVextex0);
            setVertex1(newVextex1);
            setVertex2(newVextex2);
        }

    }

    /**
     * Only used for quad vertices.
     * @author Master801
     */
    public static final class Vertex4f {

        private float vertex0 = 0.0F, vertex1 = 0.0F, vertex2 = 0.0F, vertex3 = 0.0F;

        Vertex4f(float vertice0, float vertex1, float vertex2, float vertex3) {
            this.vertex0 = vertice0;
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.vertex3 = vertex3;
        }

        Vertex4f(float[] vertices) {
            this(vertices[0], vertices[1], vertices[2], vertices[3]);
        }

        public float getVertice0() {
            return vertex0;
        }

        public float getVertice1() {
            return vertex1;
        }

        public float getVertice2() {
            return vertex2;
        }

        public float getVertice3() {
            return vertex3;
        }

        public float[] getVerticesInArray() {
            return new float[] { getVertice0(), getVertice1(), getVertice2(), getVertice3() };
        }

        public void setVertex0(float newVertex) {
            vertex0 = newVertex;
        }

        public void setVertex1(float newVertex) {
            vertex1 = newVertex;
        }

        public void setVertex2(float newVertex) {
            vertex2 = newVertex;
        }

        public void setVertex3(float newVertex) {
            vertex3 = newVertex;
        }

        public void setVirtices(float newVertex0, float newVertex1, float newVertex2, float newVertex3) {
            setVertex0(newVertex0);
            setVertex1(newVertex1);
            setVertex2(newVertex2);
            setVertex3(newVertex3);
        }

    }

    /**
     * @author Master801
     */
    public static final class Face {

        private float face0 = 0, face1 = 0, face2 = 0, face3 = 0;

        Face(float face0, float face1, float face2, float face3) {
            this.face0 = face0;
            this.face1 = face1;
            this.face2 = face2;
            this.face3 = face3;
        }

        Face(float[] faces) {
            this(faces[0], faces[1], faces[2], faces[3]);
        }

        public float getFace0() {
            return face0;
        }

        public float getFace1() {
            return face1;
        }

        public float getFace2() {
            return face2;
        }

        public float getFace3() {
            return face3;
        }

        public float[] getFaces() {
            return new float[] { getFace0(), getFace1(), getFace2(), getFace3() };
        }

        public void setFace0(float newFace) {
            face0 = newFace;
        }

        public void setFace1(float newFace) {
            face1 = newFace;
        }

        public void setFace2(float newFace) {
            face2 = newFace;
        }

        public void setFace3(float newFace) {
            face3 = newFace;
        }

        public void setFaces(float newFace0, float newFace1, float newFace2, float newFace3) {
            setFace0(newFace0);
            setFace1(newFace1);
            setFace2(newFace2);
            setFace3(newFace3);
        }

    }

}
