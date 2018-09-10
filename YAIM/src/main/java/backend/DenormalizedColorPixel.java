package backend;

import javafx.scene.paint.Color;

public class DenormalizedColorPixel {
    private Pixel pixel;
    private DenormalizedColor color;

    public DenormalizedColorPixel(Integer x, Integer y, DenormalizedColor color) {
        this.color = color;
        pixel = new Pixel(x,y);
    }

    @Override
    public String toString() {
        return "CP{" +
                "x=" + pixel.getX() +
                ", y=" + pixel.getY() +
                ", r=" + color.getRed() +
                ", g=" + color.getGreen() +
                ", b=" + color.getBlue() +
                ", a=" + color.getAlpha() +
                '}';
    }

    public Pixel getPixel() {
        return pixel;
    }

    public DenormalizedColor getColor() {
        return color;
    }
}
