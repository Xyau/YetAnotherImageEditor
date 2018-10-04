package backend;

import javafx.scene.paint.Color;

import java.util.Objects;

public class DenormalizedColorPixel {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DenormalizedColorPixel that = (DenormalizedColorPixel) o;
        return Objects.equals(pixel, that.pixel) &&
                Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pixel, color);
    }

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
