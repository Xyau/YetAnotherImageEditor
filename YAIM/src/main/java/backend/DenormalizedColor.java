package backend;

import javafx.scene.paint.Color;

import java.util.Objects;

public class DenormalizedColor {
    private Double red;
    private Double green;
    private Double blue;
    private Double alpha;


    public DenormalizedColor(Double red, Double green, Double blue, Double alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public Double getRed() {
        return red;
    }

    public Double getGreen() {
        return green;
    }

    public Double getBlue() {
        return blue;
    }

    public Double getAlpha() {
        return alpha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DenormalizedColor color = (DenormalizedColor) o;
        return Objects.equals(red, color.red) &&
                Objects.equals(green, color.green) &&
                Objects.equals(blue, color.blue) &&
                Objects.equals(alpha, color.alpha);
    }

    @Override
    public int hashCode() {

        return Objects.hash(red, green, blue, alpha);
    }

    @Override
    public String toString() {
        return "DenormalizedColor{" +
                "r=" + red +
                ", g=" + green +
                ", b=" + blue +
                ", a=" + alpha +
                '}';
    }
}
