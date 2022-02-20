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
        return new Vector3D(x+v.x, y+v.y, z+v.z);
    }

    public Vector3D subtract(Vector3D v) {
        return new Vector3D(x-v.x, y-v.y, z-v.z);
    }

    // Scalar product
    public double dot(Vector3D v) {
        return x*v.x + y*v.y + z*v.z;
    }

    public Vector3D scale(double s) {
        return new Vector3D(x*s, y*s, z*s);
    }

    // Multiplies each component by the corresponding component in the given vector
    public Vector3D scale(Vector3D v) {
        return new Vector3D(x*v.x, y*v.y, z*v.z);
    }

    public double getMagnitude() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public Vector3D normalise() {
        double s = getMagnitude();
        return new Vector3D(x/s, y/s, z/s);
    }

    // Vector product
    public Vector3D cross(Vector3D v) {
        return new Vector3D(y*v.z - z*v.y, z*v.x - x*v.z, x*v.y - y*v.x);
    }

    // Rotates the vector around another vector which acts as an axis
    public Vector3D rotateAround(Vector3D axis, double angle) {
        angle = Math.toRadians(angle);
        return scale(Math.cos(angle)).add(axis.cross(this).scale(Math.sin(angle))).add(axis.scale(dot(axis)*(1-Math.cos(angle))));
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
        return new Vector3D(Math.pow(x, p), Math.pow(y, p), Math.pow(z, p));
    }

    // Inverts each term
    public Vector3D inverse() {
        return new Vector3D(1/x, 1/y, 1/z);
    }
}
