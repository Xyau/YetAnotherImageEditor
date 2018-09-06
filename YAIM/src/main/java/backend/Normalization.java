package backend;

import javafx.scene.paint.Color;

import java.util.function.Function;

public abstract class Normalization {
    public static Function<DenormalizedColor, Color> TRUNCATE = (denormalizedColor) -> {
        Double red = keepInRange(0.0,1.0,denormalizedColor.getRed());
        Double blue = keepInRange(0.0,1.0,denormalizedColor.getBlue());
        Double green = keepInRange(0.0,1.0,denormalizedColor.getGreen());
        Double alpha = keepInRange(0.0,1.0,denormalizedColor.getAlpha());
        return new Color(red,green,blue,alpha);
    };

    public static Double keepInRange(Double min, Double max, Double value){
        return value > max? max:(value < min?min:value);
    }
}
