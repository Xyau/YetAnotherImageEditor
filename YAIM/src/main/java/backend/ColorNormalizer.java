package backend;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ColorNormalizer {

    public static List<Color> normalize(List<DenormalizedColor> colors){
        List<Color> normalizedColors = new ArrayList<>();

        Double minRed=Double.MAX_VALUE;
        Double maxRed=Double.MIN_VALUE;
        Double minGreen=Double.MAX_VALUE;
        Double maxGreen=Double.MIN_VALUE;
        Double minBlue=Double.MAX_VALUE;
        Double maxBlue=Double.MIN_VALUE;
        Double minAlpha=Double.MAX_VALUE;
        Double maxAlpha=Double.MIN_VALUE;
        for(DenormalizedColor denormalizedColor: colors){
            Double red = denormalizedColor.getRed();
        }
        return normalizedColors;
    }
}
