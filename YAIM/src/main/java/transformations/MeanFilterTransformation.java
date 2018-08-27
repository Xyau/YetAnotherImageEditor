package transformations;

import backend.ColorPixel;
import javafx.scene.paint.Color;

import java.util.List;

public class MeanFilterTransformation extends FilterTransformation {
    public MeanFilterTransformation(Integer filterSize) {
        super(filterSize);
    }

    @Override
    Color processNeighbors(List<ColorPixel> neighbors) {
        double red = 0;
        double blue = 0;
        double green = 0;
        double alpha = 0;

        for (ColorPixel cp: neighbors) {
            Color color = cp.getColor();
            red += color.getRed();
            blue += color.getBlue();
            green += color.getGreen();
            alpha += color.getOpacity();
        }

        return new Color(red/neighbors.size(),green/neighbors.size(),blue/neighbors.size(),alpha/neighbors.size());
    }
}
