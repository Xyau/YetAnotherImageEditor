package transformations;

import backend.ColorPixel;
import backend.Utils;
import javafx.scene.paint.Color;

import java.util.Comparator;
import java.util.List;

public class MedianFilterTransformation extends FilterTransformation {
    public MedianFilterTransformation(Integer filterSize) {
        super(filterSize);
    }

    //Public for testing
    @Override
    public Color processNeighbors(List<ColorPixel> neighbors) {
        neighbors.sort(Comparator.comparingDouble(c -> c.getColor().getBrightness()));
        return neighbors.get(Utils.toInteger(neighbors.size()/2)).getColor();
    }
}
