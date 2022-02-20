public abstract class Shape {
    protected Colour colour;
    protected double ka;        // Ambient coefficient
    protected double kd;        // Diffuse coefficient
    protected double ks;        // Specular coefficient
    protected double n;         // Roughness coefficient
    protected double r;         // Reflectivity

    Shape(Colour colour, double ka, double kd, double ks, double n, double r) {
        this.colour = colour;
        this.ka = ka;
        this.kd = kd;
        this.ks = ks;
        this.n = n;
        this.r = r;
    }

    public Colour getColour() {
        return colour.copy();
    }

    public double getKa() {
        return ka;
    }

    public double getKd() {
        return kd;
    }

    public double getKs() {
        return ks;
    }

    public double getN() {
        return n;
    }

    public double getR() {
        return r;
    }

    public abstract Vector3D getNormalAt(Vector3D point);                   // Gets the normal vector at the given point
                                                                            // on the shape's surface

    public abstract Hit getFirstHit(Ray ray) throws DoesNotHitException;    // Gets the position of where the ray first
                                                                            // hits the object and the normal vector
                                                                            // at that point
}
