public class Ray {
    private Vector3D origin;
    private Vector3D direction;

    Ray(Vector3D origin, Vector3D direction) {
        this.origin = origin;
        this.direction = direction.normalise();
    }

    public Vector3D getOrigin() {
        return origin;
    }

    public Vector3D getDirection() {
        return direction;
    }
}
