import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class Loader {
    // Reads a file and returns contents in a string
    private static String ReadFile(String filename) throws IOException {
        String line;

        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }

            return sb.toString();
        }
    }

    // Creates a Scene object from data contained within a JSON file
    public static Scene LoadScene(String filename) throws IOException {
        Scene scene = new Scene();

        JSONObject jsonObj = new JSONObject(ReadFile(filename));

        JSONArray jsonScene = jsonObj.getJSONArray("scene");

        for (int i = 0; i < jsonScene.length(); i++) {
            JSONObject jsonElement = jsonScene.getJSONObject(i);

            switch (jsonElement.getString("type")) {
                case "sphere" -> {
                    double x = jsonElement.getDouble("x");
                    double y = jsonElement.getDouble("y");
                    double z = jsonElement.getDouble("z");
                    double radius = jsonElement.getDouble("radius");
                    Colour colour = new Colour(jsonElement.getString("colour"));
                    double ka = jsonElement.getDouble("ka");
                    double kd = jsonElement.getDouble("kd");
                    double ks = jsonElement.getDouble("ks");
                    double n = jsonElement.getDouble("n");
                    double r = jsonElement.getDouble("r");

                    scene.addShape(new Sphere(x, y, z, radius, colour, ka, kd, ks, n, r));
                }
                case "cone" -> {
                    double x = jsonElement.getDouble("x");
                    double y = jsonElement.getDouble("y");
                    double z = jsonElement.getDouble("z");
                    double ax = jsonElement.getDouble("ax");
                    double ay = jsonElement.getDouble("ay");
                    double az = jsonElement.getDouble("az");
                    double angle = Math.toRadians(jsonElement.getDouble("angle"));
                    Colour colour = new Colour(jsonElement.getString("colour"));
                    double ka = jsonElement.getDouble("ka");
                    double kd = jsonElement.getDouble("kd");
                    double ks = jsonElement.getDouble("ks");
                    double n = jsonElement.getDouble("n");
                    double r = jsonElement.getDouble("r");

                    scene.addShape(new Cone(x, y, z, ax, ay, az, angle, colour, ka, kd, ks, n, r));
                }
                case "plane" -> {
                    double nx = jsonElement.getDouble("nx");
                    double ny = jsonElement.getDouble("ny");
                    double nz = jsonElement.getDouble("nz");
                    double d = jsonElement.getDouble("d");
                    Colour colour = new Colour(jsonElement.getString("colour"));
                    double ka = jsonElement.getDouble("ka");
                    double kd = jsonElement.getDouble("kd");
                    double ks = jsonElement.getDouble("ks");
                    double n = jsonElement.getDouble("n");
                    double r = jsonElement.getDouble("r");

                    scene.addShape(new Plane(nx, ny, nz, d, colour, ka, kd, ks, n, r));
                }
                case "light" -> {
                    double x = jsonElement.getDouble("x");
                    double y = jsonElement.getDouble("y");
                    double z = jsonElement.getDouble("z");
                    double intensity = jsonElement.getDouble("intensity");
                    Colour colour = new Colour(jsonElement.getString("colour"));

                    scene.addLight(new Light(x, y, z, intensity, colour));
                }
                case "ambient" -> {
                    double intensity = jsonElement.getDouble("intensity");
                    Colour colour = new Colour(jsonElement.getString("colour"));

                    scene.setAmbientLight(colour.scale(intensity));
                }
            }

        }

        return scene;
    }

    // Creates a Camera object from data contained within a JSON file
    public static Camera LoadCamera(String filename) throws IOException {
        JSONObject jsonObj = new JSONObject(ReadFile(filename));

        JSONObject jsonCamera = jsonObj.getJSONObject("camera");

        double x = jsonCamera.getDouble("x");
        double y = jsonCamera.getDouble("y");
        double z = jsonCamera.getDouble("z");
        double nx = jsonCamera.getDouble("nx");
        double ny = jsonCamera.getDouble("ny");
        double nz = jsonCamera.getDouble("nz");
        double d = jsonCamera.getDouble("d");
        double width = jsonCamera.getDouble("width");
        double height = jsonCamera.getDouble("height");

        return new Camera(x, y, z, nx, ny, nz, d, width, height);
    }
}
