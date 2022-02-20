import java.awt.image.BufferedImage;

public class Renderer {
    private Camera camera;
    private Scene scene;

    Renderer(Camera camera, Scene scene) {
        this.camera = camera;
        this.scene = scene;
    }

    // Calculates the colour from a shape after a ray has hit it
    private Colour illuminate(Hit hit) {
        Colour colour = new Colour();

        Colour shapeColour = hit.getShape().getColour();
        double ka = hit.getShape().getKa();
        double kd = hit.getShape().getKd();
        double ks = hit.getShape().getKs();
        double n = hit.getShape().getN();

        // Ambient component
        colour.add(scene.getAmbientLight().scale(ka).scale(shapeColour));

        Vector3D N = hit.getNormal();                                                           // Normal vector
        Vector3D V = hit.getIncident().getOrigin().subtract(hit.getPosition()).normalise();     // Vector from hit point to ray origin

        double distanceToLight;
        double dotDiffuse;
        double dotSpecular;
        for (Light l : scene.getLights()) {
            distanceToLight = l.getPosition().subtract(hit.getPosition()).getMagnitude();
            Vector3D L = l.getPosition().subtract(hit.getPosition()).normalise();               // Vector from hit point to light

            Ray shadowRay = new Ray(hit.getPosition().add(N.copy().scale(1E-10)), L);
            try {
                Hit shadowHit = scene.getFirstHit(shadowRay);
                double distanceToHit = shadowHit.getPosition().subtract(shadowRay.getOrigin()).getMagnitude();

                if (distanceToHit <= distanceToLight) {     // If the path from the hit point to the light is obstructed
                    continue;                               // Do not calculate diffuse and specular components
                }

            } catch (DoesNotHitException ignored) {}

            Vector3D R = L.reflectIn(N).scale(-1);                                              // Reflection of light in normal
            Colour illumination = l.getIlluminationAt(distanceToLight);

            // Diffuse component
            dotDiffuse = L.dot(N);
            if (dotDiffuse > 0) {
                colour.add(illumination.copy().scale(kd * dotDiffuse).scale(shapeColour));
            }

            // Specular component
            dotSpecular = R.dot(V);
            if (dotSpecular > 0) {
                colour.add(illumination.copy().scale(ks * Math.pow(dotSpecular, n)));
            }
        }

        return colour;
    }

    private Colour trace(Ray ray, int numBounces) {     // Traces a ray and calculates its colour
        try {
            Hit hit = scene.getFirstHit(ray);
            Colour colour = illuminate(hit);            // Calculate illumination from this hit
            double reflectivity = hit.getShape().getR();

            if (numBounces > 0 && reflectivity > 0) {
                colour.add(trace(hit.getReflectionRay(), numBounces-1).scale(reflectivity));   // Create a new reflection ray and recurse
            }

            return colour;

        } catch (DoesNotHitException e) {   // Light goes off into the void
            return new Colour();
        }
    }

    public BufferedImage render(int width, int height, int numBounces, boolean showProgress) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        double xFraction;
        double yFraction;
        Ray ray;
        for (int y = 0; y < height; y++) {
            yFraction = y/((double) (height-1));    // The fraction down the image plane the current pixel is

            if (showProgress) {
                System.out.println(Math.round(100*yFraction) + "% rendered...");
            }

            for (int x = 0; x < width; x++) {
                xFraction = x/((double) (width-1));     // The fraction across the image plane the current pixel is

                ray = camera.getRay(xFraction, yFraction);  // Get the ray that goes through the current pixel
                Colour colour = trace(ray, numBounces);     // Trace the ray and get its colour
                image.setRGB(x, y, colour.mapAndEncode());  // Set the pixel in the buffered image
            }
        }
        return image;
    }

}
