import java.lang.Math;

public class Vector3D {
    protected double x;
    protected double y;
    protected double z;

    Vector3D() {
        x = 0;
        y = 0;
        z = 0;
    }

    Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(double u) {
        this.x = u;
        this.y = u;
        this.z = u;
    }

    public Vector3D copy() {
        return new Vector3D(x, y, z);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Vector3D add(Vector3D v) {
        x += v.x;
        y += v.y;
        z += v.z;

        return this;
    }

    public Vector3D subtract(Vector3D v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;

        return this;
    }

    // Scalar product
    public double dot(Vector3D v) {
        return x*v.x + y*v.y + z*v.z;
    }

    public Vector3D scale(double s) {
        x *= s;
        y *= s;
        z *= s;

        return this;
    }

    // Multiplies each component by the corresponding component in the given vector
    public Vector3D scale(Vector3D v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;

        return this;
    }

    public double getMagnitude() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public Vector3D normalise() {
        scale(1/getMagnitude());

        return this;
    }

    // Vector product
    public Vector3D cross(Vector3D v) {
        double xCopy = x;
        double yCopy = y;

        x = y*v.z - z*v.y;
        y = z*v.x - xCopy*v.z;
        z = xCopy*v.y - yCopy*v.x;

        return this;
    }

    // Rotates the vector around another vector which acts as an axis
    // ! Needs testing
    public Vector3D rotateAround(Vector3D axis, double angle) {
        angle = Math.toRadians(angle);
        Vector3D thisCopy = copy();
        scale(Math.cos(angle)).add(axis.cross(thisCopy).scale(Math.sin(angle))).add(axis.scale(dot(axis)*(1-Math.cos(angle))));

        return this;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    // Reflects vector in another
    public Vector3D reflectIn(Vector3D v) {
        v = v.normalise();
        return subtract(v.scale(2 * dot(v)));
    }

    // Raises each component to the given power
    public Vector3D power(double p) {
        x = Math.pow(x, p);
        y = Math.pow(y, p);
        z = Math.pow(z, p);

        return this;
    }

    // Inverts each term
    public Vector3D invert() {
        x = 1/x;
        y = 1/y;
        z = 1/z;

        return this;
    }
}
