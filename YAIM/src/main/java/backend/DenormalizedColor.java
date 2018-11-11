package backend;

import backend.utils.ColorUtils;
import javafx.scene.paint.Color;

import java.util.Objects;

public class DenormalizedColor {
    public static DenormalizedColor BACKGROUND = new DenormalizedColor(3.0,3.0,3.0,1.0);
    public static DenormalizedColor EXTERNAL = new DenormalizedColor(1.0,1.0,1.0,1.0);
    public static DenormalizedColor INTERNAL = new DenormalizedColor(-1.0,-1.0,-1.0,1.0);
    public static DenormalizedColor OBJECT = new DenormalizedColor(-3.0,-3.0,-3.0,1.0);
    public static DenormalizedColor WHITE = new DenormalizedColor(1.0,1.0,1.0,1.0);
    public static DenormalizedColor BLACK = new DenormalizedColor(0.0,0.0,0.0,1.0);
    public static final DenormalizedColor RED = new DenormalizedColor(1.0,0.0,0.0,1.0);
    private Double red;
    private Double green;
    private Double blue;
    private Double alpha;

    public DenormalizedColor(Color color) {
        red = color.getRed();
        green = color.getGreen();
        blue = color.getBlue();
        alpha = color.getOpacity();
    }

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
    public String toString() {
        return "DenormalizedColor{" +
                "r=" + red +
                ", g=" + green +
                ", b=" + blue +
                ", a=" + alpha +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DenormalizedColor that = (DenormalizedColor) o;
        return Objects.equals(red, that.red) &&
                Objects.equals(green, that.green) &&
                Objects.equals(blue, that.blue) &&
                Objects.equals(alpha, that.alpha);
    }

    @Override
    public int hashCode() {

        return Objects.hash(red, green, blue, alpha);
    }
}
