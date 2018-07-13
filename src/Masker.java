import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Random;

public class Masker {

    public static void main(String[] args) {

        mask(args[0]);

    }

    private static void setPixel(BufferedImage image, int x, int y, Pixel pixel) {
        if (image.getWidth() <= x + 1 || image.getHeight() <= y + 1 || x < 0 || y < 0) {
            return;
        }
        image.setRGB(x, y, pixel.getColors()[0].getRGB());
        image.setRGB(x + 1, y, pixel.getColors()[1].getRGB());
        image.setRGB(x, y + 1, pixel.getColors()[2].getRGB());
        image.setRGB(x + 1, y + 1, pixel.getColors()[3].getRGB());
    }

    private static void mask(String path) {
        File file = new File(path);

        BufferedImage image = null;
        String formatName = null;

        try (ImageInputStream input = ImageIO.createImageInputStream(file)) {
            image = ImageIO.read(file);

            Iterator<ImageReader> iterator = ImageIO.getImageReaders(input);
            if (!iterator.hasNext()) {
                System.err.println("No readers found.");
                System.exit(1);
            }

            ImageReader reader = iterator.next();
            formatName = reader.getFormatName();

        } catch (IOException e) {
            System.err.println("File read error.");
            e.printStackTrace();
            System.exit(1);
        }

        Random random = new SecureRandom();
        int width = image.getWidth(), height = image.getHeight();

        String fileName = Paths.get(path).getFileName().toString();
        int d = fileName.lastIndexOf(".");
        if (0 < d && d < fileName.length() - 1) {
            fileName = fileName.substring(0, d);
        }

        BufferedImage layer1 = new BufferedImage(2 * width, 2 * height, image.getType());
        BufferedImage layer2 = new BufferedImage(2 * width, 2 * height, image.getType());

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                Pixel pixel = Pixel.randomPixel(random);
                setPixel(layer1, 2 * x, 2 * y, pixel);

                pixel = (image.getRGB(x, y) == Color.WHITE.getRGB()) ? pixel : Pixel.invert(pixel);
                setPixel(layer2, 2 * x, 2 * y, pixel);
            }
        }

        try {
            ImageIO.write(layer1, formatName, new File(path.replace(fileName, fileName + "1")));
            ImageIO.write(layer2, formatName, new File(path.replace(fileName, fileName + "2")));
        } catch (IOException e) {
            System.err.println("File write error.");
            e.printStackTrace();
            System.exit(1);
        }

    }

}
