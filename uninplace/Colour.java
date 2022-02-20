public class Colour extends Vector3D {
    static double GAMMA = 2.2;
    static double A = 2;           // Contrast coefficient
    static double B = 1.3;         // Brightness coefficient

    Colour() {
        x = 0;
        y = 0;
        z = 0;
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

    Colour(Vector3D v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public Colour(double u) {
        super(u);
    }

    // Tone map and gamma encode the colour
    public int mapAndEncode() {
        Colour power = power(B);
        Colour displayReferred = power.scale(power.add(new Colour(Math.pow(0.5/A, B))).inverse());

        Colour gammaEncoded = displayReferred.power(1/GAMMA);
        int rP = (int) (Math.max(0, Math.min(gammaEncoded.getX(), 1)) * 255);
        int gP = (int) (Math.max(0, Math.min(gammaEncoded.getY(), 1)) * 255);
        int bP = (int) (Math.max(0, Math.min(gammaEncoded.getZ(), 1)) * 255);

        return (rP << 16) | (gP << 8) | bP;
    }

    public Colour add(Colour c) {
        return new Colour(super.add(c));
    }

    public Colour scale(double s) {
        return new Colour(super.scale(s));
    }

    public Colour scale(Colour c) {
        return new Colour(super.scale(c));
    }

    public Colour power(double p) {
        return new Colour(super.power(p));
    }

    public Colour inverse() {
        return new Colour(super.inverse());
    }
}
