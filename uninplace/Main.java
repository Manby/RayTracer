import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Camera c = Loader.LoadCamera("playground.json");
            Scene s = Loader.LoadScene("playground.json");
            Renderer r = new Renderer(c, s);

            int a = 10;
            BufferedImage i = r.render(3840/a, 2160/a, 4, true);

            // Write to png file
            File save = new File("output.png");
            ImageIO.write(i, "png", save);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}