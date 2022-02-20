public class Sphere extends Shape {
    private Vector3D center;
    private double radius;

    Sphere(double x, double y, double z, double radius, Colour colour, double ka, double kd, double ks, double n, double r) {
        super(colour, ka, kd, ks, n, r);
        this.center = new Vector3D(x, y, z);
        this.radius = radius;
        this.colour = colour;
        this.ka = ka;
        this.kd = kd;
        this.ks = ks;
        this.n = n;
    }

    @Override
    public Vector3D getNormalAt(Vector3D point) {
        return point.subtract(center).normalise();
    }

    @Override
    public Hit getFirstHit(Ray ray) throws DoesNotHitException {
        Vector3D D = ray.getDirection();
        Vector3D O = ray.getOrigin();

        double a = D.dot(D);
        double b = 2 * D.dot(O.subtract(center));
        double c = (O.subtract(center)).dot(O.subtract(center)) - (radius*radius);
        double disc = b*b - (4 * a * c);

        if (disc < 0) {
            throw new DoesNotHitException();
        }

        double s1 = (-b + Math.sqrt(disc)) / (2*a);
        double s2 = (-b - Math.sqrt(disc)) / (2*a);

        double sMin;
        if (s1 <= 0) {
            throw new DoesNotHitException();    // Sphere was behind ray
        }
        sMin = s1;
        if (s2 > 0) {
            sMin = s2;
        }

        Vector3D poi = O.add(D.normalise().scale(sMin));

        return new Hit(ray, this, poi, getNormalAt(poi));
    }
}
