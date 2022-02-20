public class Plane extends Shape {
    Vector3D normal;
    double d;

    Plane(double nx, double ny, double nz, double d, Colour colour, double ka, double kd, double ks, double n, double r) {
        super(colour, ka, kd, ks, n, r);
        this.normal = new Vector3D(nx, ny, nz);
        this.d = d;
    }

    @Override
    public Vector3D getNormalAt(Vector3D v) {
        return normal.copy().normalise();
    }

    @Override
    public Hit getFirstHit(Ray ray) throws DoesNotHitException {
        Vector3D O = ray.getOrigin();
        Vector3D D = ray.getDirection();

        double dot = normal.dot(D);
        if (dot == 0) {
            throw new DoesNotHitException();
        }

        double s = - (d + normal.dot(O)) / dot;
        if (s <= 0) {
            throw new DoesNotHitException();
        }

        Vector3D poi = O.copy().add(D.copy().normalise().scale(s));

        return new Hit(ray, this, poi, normal.copy());
    }
}
