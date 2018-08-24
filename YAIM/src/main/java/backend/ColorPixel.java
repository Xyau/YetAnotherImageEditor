package backend;

import javafx.scene.paint.Color;

public class ColorPixel {
    private Pixel pixel;
    private Color color;

    public ColorPixel(Integer x, Integer y, Color color) {
        this.color = color;
        pixel = new Pixel(x,y);
    }

    @Override
    public String toString() {
        return "ColorPixel{" +
                "x=" + pixel.getX() +
                ", y=" + pixel.getY() +
                ", color=" + color +
                '}';
    }

    public Pixel getPixel() {
        return pixel;
    }

    public Color getColor() {
        return color;
    }
}
