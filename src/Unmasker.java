import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;

public class Unmasker {

    public static void main(String[] args) {

        unmask(args[0], args[1]);

    }

    private static boolean equalPixels(BufferedImage image1, BufferedImage image2, int x, int y) {
        return image1.getWidth() > x + 1 && image1.getHeight() > y + 1
                && image2.getWidth() > x + 1 && image2.getHeight() > y + 1 && 0 <= x && 0 <= y
                && image1.getRGB(x, y) == image2.getRGB(x, y)
                && image1.getRGB(x + 1, y) == image2.getRGB(x + 1, y)
                && image1.getRGB(x, y + 1) == image2.getRGB(x, y + 1)
                && image1.getRGB(x + 1, y + 1) == image2.getRGB(x + 1, y + 1);
    }

    private static void unmask(String path1, String path2) {
        BufferedImage layer1 = null, layer2 = null;
        String formatName = null;

        File file1 = new File(path1);
        File file2 = new File(path2);

        try (ImageInputStream input1 = ImageIO.createImageInputStream(file1);
             ImageInputStream input2 = ImageIO.createImageInputStream(file2)) {

            layer1 = ImageIO.read(file1);
            layer2 = ImageIO.read(file2);

            Iterator<ImageReader> iterator1 = ImageIO.getImageReaders(input1);
            Iterator<ImageReader> iterator2 = ImageIO.getImageReaders(input2);

            if (!iterator1.hasNext() || !iterator2.hasNext()) {
                System.err.println("No readers found.");
                System.exit(1);
            }

            ImageReader reader1 = iterator1.next();
            ImageReader reader2 = iterator2.next();

            formatName = reader1.getFormatName();

            if (!formatName.equals(reader2.getFormatName())) {
                System.err.println("Wrong format.");
                System.exit(2);
            }

        } catch (IOException e) {
            System.err.println("File read error.");
            e.printStackTrace();
            System.exit(1);
        }

        int width = layer1.getWidth(), height = layer1.getHeight();
        if (width != layer2.getWidth() || height != layer2.getHeight()) {
            System.err.println("Wrong size.");
            System.exit(2);
        }

        String fileName = Paths.get(path1).getFileName().toString();
        int d = fileName.lastIndexOf(".");
        if (0 < d && d < fileName.length() - 1) {
            fileName = fileName.substring(0, d);
        }

        BufferedImage outputImage = new BufferedImage(width / 2, height / 2, layer1.getType());

        for (int y = 0; y < height; y += 2) {
            for (int x = 0; x < width; x += 2) {
                int rgb = equalPixels(layer1, layer2, x, y) ? Color.WHITE.getRGB() : Color.BLACK.getRGB();
                outputImage.setRGB(x / 2, y / 2, rgb);
            }
        }

        try {
            ImageIO.write(outputImage, formatName, new File(path1.replace(fileName, fileName + "_unmasked")));
        } catch (IOException e) {
            System.err.println("File write error.");
            e.printStackTrace();
            System.exit(1);
        }

    }

}
