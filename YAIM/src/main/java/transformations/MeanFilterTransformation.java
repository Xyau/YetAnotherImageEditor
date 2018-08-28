package transformations;

import backend.ColorPixel;
import backend.DenormalizedColor;
import javafx.scene.paint.Color;

import java.util.List;

public class MeanFilterTransformation extends FilterTransformation {
    public MeanFilterTransformation(Integer filterSize) {
        super(filterSize,true);
    }

    @Override
    DenormalizedColor processNeighborsDenormalized(List<ColorPixel> neighbors) {
        double red = 0;
        double blue = 0;
        double green = 0;
        Double alpha = null;

        for (ColorPixel cp: neighbors) {
            Color color = cp.getColor();
            red += color.getRed();
            blue += color.getBlue();
            green += color.getGreen();
            alpha = alpha==null?color.getOpacity():alpha;
        }

        return new DenormalizedColor(red/neighbors.size(),green/neighbors.size(),blue/neighbors.size(),alpha);
    }
}
