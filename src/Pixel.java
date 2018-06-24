import java.awt.Color;
import java.util.Random;


public enum Pixel {

    // colors: upper left, upper right, bottom left, bottom roght
    A1(new Color[]{Color.WHITE, Color.BLACK, Color.WHITE, Color.BLACK}),
    A2(new Color[]{Color.BLACK, Color.WHITE, Color.BLACK, Color.WHITE}),
    B1(new Color[]{Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE}),
    B2(new Color[]{Color.WHITE, Color.WHITE, Color.BLACK, Color.BLACK}),
    C1(new Color[]{Color.WHITE, Color.BLACK, Color.BLACK, Color.WHITE}),
    C2(new Color[]{Color.BLACK, Color.WHITE, Color.WHITE, Color.BLACK});


    private final Color[] colors;

    Pixel(Color[] colors) {
        this.colors = colors;
    }

    public Color[] getColors() {
        return this.colors;
    }

//    private static boolean equalPixel(Pixel pixel, BufferedImage image, int x, int y) {
//        return image.getWidth() > x + 1 && image.getHeight() > y + 1
//                && image.getRGB(x, y) == pixel.colors[0].getRGB()
//                && image.getRGB(x + 1, y) == pixel.colors[1].getRGB()
//                && image.getRGB(x, y + 1) == pixel.colors[2].getRGB()
//                && image.getRGB(x + 1, y + 1) == pixel.colors[3].getRGB();
//    }

//    public static Pixel createPixel(BufferedImage image, int x, int y) {
//        for (Pixel pixel : Pixel.values()) {
//            if (equalPixel(pixel, image, x, y)) {
//                return pixel;
//            }
//        }
//
//        return null;
//    }

    public static Pixel invert(Pixel pixel) {
        switch (pixel) {
            case A1:
                return Pixel.A2;
            case A2:
                return Pixel.A1;
            case B1:
                return Pixel.B2;
            case B2:
                return Pixel.B1;
            case C1:
                return Pixel.C2;
            case C2:
                return Pixel.C1;
            default:
                return null;
        }
    }

    public static Pixel randomPixel(Random random) {
        return Pixel.values()[random.nextInt(6)];
    }
}
