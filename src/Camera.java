public class Camera {
    private Vector3D position;
    private Vector3D facing;    // Direction in which the camera is facing
    private double d;
    private double width;       // Width of image plane
    private double height;      // Height of image plane
    private Vector3D xAxis;     // Relative x-axis of the image plane
    private Vector3D yAxis;     // Relative y-axis of the image plane

    Camera(double x, double y, double z, double nx, double ny, double nz, double d, double width, double height) {
        this.position = new Vector3D(x, y, z);
        this.facing = new Vector3D(nx, ny, nz).normalise();
        this.d = d;
        this.width = width;
        this.height = height;
        xAxis = new Vector3D(facing.getY(), facing.getX(), 0).normalise();
        yAxis = facing.copy().cross(xAxis).normalise();
    }

    public Vector3D getPosition() {
        return position.copy();
    }

    public Vector3D getFacing() {
        return facing.copy();
    }

    // Gets the ray that is shot through the pixel that is at the given fraction across the image plane
    public Ray getRay(double xFraction, double yFraction) {
        /*
        Vector3D xAxis = new Vector3D(facing.getY(), facing.getX(), 0).normalise();
        Vector3D yAxis = facing.cross(xAxis).normalise();

        double xAngle = fovx * (xFraction-0.5);
        double yAngle = fovy * (yFraction-0.5);

        return facing.rotateAround(xAxis, xAngle).rotateAround(yAxis.rotateAround(xAxis, xAngle), yAngle);
        //return facing.rotateAround(yAxis, yAngle).rotateAround(xAxis.rotateAround(yAxis, yAngle), xAngle);
        */

        Vector3D xStep = xAxis.copy().normalise().scale(width*(xFraction-0.5));
        Vector3D yStep = yAxis.copy().normalise().scale(height*(yFraction-0.5));
        return new Ray(position, facing.copy().scale(d).add(xStep).add(yStep));
    }
}
