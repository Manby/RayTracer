import java.util.ArrayList;

public class Scene {
    private ArrayList<Shape> shapes;
    private ArrayList<Light> lights;
    private Colour ambientLight;

    Scene() {
        shapes = new ArrayList<>();
        lights = new ArrayList<>();
    }

    public void addShape(Shape s) {
        shapes.add(s);
    }

    public void addLight(Light l) {
        lights.add(l);
    }

    public void setAmbientLight(Colour a) {
        ambientLight = a;
    }

    public Colour getAmbientLight() {
        return ambientLight;
    }

    // Computes which shape the ray hits first and where
    public Hit getFirstHit(Ray ray) throws DoesNotHitException {
        Hit hit;
        Hit firstHit = null;
        double distance;
        double minDistance = Double.POSITIVE_INFINITY;

        for (Shape s : shapes) {
            try {
                hit = s.getFirstHit(ray);
                distance = hit.getPosition().subtract(ray.getOrigin()).getMagnitude();
                if (distance < minDistance) {
                    firstHit = hit;
                    minDistance = distance;
                }
            } catch (DoesNotHitException e) {}
        }
        if (firstHit != null) {
            return firstHit;
        }
        throw new DoesNotHitException();    // The ray goes off into the void
    }

    public ArrayList<Light> getLights() {
        return lights;
    }

}
