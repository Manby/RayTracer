public class Light {
    private Vector3D position;
    private double intensity;
    private Colour colour;

    Light(double x, double y, double z, double intensity, Colour colour) {
        this.position = new Vector3D(x, y, z);
        this.intensity = intensity;
        this.colour = colour;
    }

    public Vector3D getPosition() {
        return position;
    }

    public Colour getColour() {
        return colour;
    }

    public Colour getIlluminationAt(double distance) {
        return colour.scale(intensity / (4 * Math.PI * distance * distance));   // Inverse square law
    }
}
