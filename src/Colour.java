public class Colour extends Vector3D {
    static double GAMMA = 2.2;
    static double A = 2;           // Contrast coefficient
    static double B = 1.3;         // Brightness coefficient

    Colour() {
        super();
    }

    // Converts a string in the form "#ABCDEF"
    Colour(String hex) {
        double r = Integer.parseInt(hex.substring(1,3), 16) / 255.0;
        double g = Integer.parseInt(hex.substring(3,5), 16) / 255.0;
        double b = Integer.parseInt(hex.substring(5,7), 16) / 255.0;

        x = r;
        y = g;
        z = b;
    }

    Colour(double x, double y, double z) {
        super(x, y, z);
    }

    Colour(Vector3D v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public Colour(double u) {
        super(u);
    }

    public Colour copy() {
        return new Colour(x, y, z);
    }

    // Tone map and gamma encode the colour
    public int mapAndEncode() {
        power(B);
        this.scale(copy().add(new Colour(Math.pow(0.5/A, B))).invert());

        power(1/GAMMA);
        int rP = (int) (Math.max(0, Math.min(getX(), 1)) * 255);
        int gP = (int) (Math.max(0, Math.min(getY(), 1)) * 255);
        int bP = (int) (Math.max(0, Math.min(getZ(), 1)) * 255);

        return (rP << 16) | (gP << 8) | bP;
    }

    public Colour add(Colour c) {
        super.add(c);

        return this;
    }

    public Colour scale(double s) {
        super.scale(s);

        return this;
    }

    public Colour scale(Colour c) {
        super.scale(c);

        return this;
    }

    public Colour power(double p) {
        super.power(p);

        return this;
    }

    public Colour invert() {
        super.invert();

        return this;
    }
}
