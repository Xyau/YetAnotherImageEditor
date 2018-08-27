package backend;

import javafx.scene.paint.Color;

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
}
