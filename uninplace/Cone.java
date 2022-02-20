public class Cone extends Shape {
    Vector3D vertex;
    Vector3D axis;
    double angle;

    Cone(double x, double y, double z, double ax, double ay, double az, double angle, Colour colour, double ka, double kd, double ks, double n, double r) {
        super(colour, ka, kd, ks, n, r);
        vertex = new Vector3D(x, y, z);
        axis = new Vector3D(ax, ay, az);
        this.angle = angle;
    }

    private boolean isInValidHalf(Vector3D point) {
        return (axis.dot(point.subtract(vertex)) >= 0);
    }

    @Override
    public Vector3D getNormalAt(Vector3D point) {
        Vector3D T = point.subtract(vertex);
        double coef = T.dot(T) / axis.dot(T);

        return point.subtract(vertex.add(axis.scale(coef))).normalise();
    }

    @Override
    public Hit getFirstHit(Ray ray) throws DoesNotHitException {
        Vector3D O = ray.getOrigin();
        Vector3D D = ray.getDirection();

        double cst = Math.pow(Math.cos(angle), 2);

        double AA = axis.dot(axis);
        double DD = D.dot(D);
        double VV = vertex.dot(vertex);
        double OO = O.dot(O);
        double AD = axis.dot(D);
        double OD = O.dot(D);
        double AV = axis.dot(vertex);
        double OV = O.dot(vertex);
        double DV = D.dot(vertex);
        double OA = O.dot(axis);

        double a = AA * cst * DD - Math.pow(AD, 2);
        double b = 2 * (AA * cst * (OD - DV) - (OA * AD) + (AD * AV));
        double c = AA * cst * (OO + VV - 2 * OV) - Math.pow(OA, 2) - Math.pow(AV, 2) + 2 * OA * AV;

        double disc = b*b - 4 * a * c;

        if (disc < 0) {
            throw new DoesNotHitException();
        }

        double s1 = (-b + Math.sqrt(disc)) / (2*a);
        double s2 = (-b - Math.sqrt(disc)) / (2*a);

        Vector3D firstPOI = O.add(D.normalise().scale(s2));
        if (s2 > 0 && isInValidHalf(firstPOI)) {
            return new Hit(ray, this, firstPOI, getNormalAt(firstPOI));
        }

        Vector3D secondPOI = O.add(D.normalise().scale(s1));
        if (s1 > 0 && isInValidHalf(secondPOI)) {
            return new Hit(ray, this, secondPOI, getNormalAt(secondPOI));
        }

        throw new DoesNotHitException();
    }
}
