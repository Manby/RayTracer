public class Hit {
    static double NUDGE = 1E-10;       // Distance by which the reflected ray is translated away from the
                                // point of intersection (to avoid the ray from intersecting
                                // again the same shape again)

    private Ray incident;       // The ray that caused the hit
    private Shape shape;        // The shape that was hit
    private Vector3D position;  // The coordinates of the point where the hit occurred
    private Vector3D normal;    // The normal vector at this point

    Hit(Ray incident, Shape shape, Vector3D position, Vector3D normal) {
        this.incident = incident;
        this.shape = shape;
        this.position = position;
        this.normal = normal.normalise();
    }

    public Ray getIncident() {
        return incident;
    }

    public Shape getShape() {
        return shape;
    }

    public Vector3D getPosition() {
        return position;
    }

    public Vector3D getNormal() {
        return normal;
    }

    // Returns the reflection of the incident ray in the normal vector
    public Ray getReflectionRay () {
        return new Ray(position.add(normal.scale(NUDGE)), incident.getDirection().reflectIn(normal).scale(1));
    }
}
