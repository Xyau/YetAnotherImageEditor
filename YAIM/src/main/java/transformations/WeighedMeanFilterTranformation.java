package transformations;

import backend.ColorPixel;
import backend.DenormalizedColor;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public class WeighedMeanFilterTranformation extends FilterTransformation {
    public static Integer[][] HIGHPASS = new Integer[][]{{-1,-1,-1},
            {-1, 8,-1},
            {-1,-1,-1}};
    public static Integer[][] LOWPASS = new Integer[][]{{1,1,1},
            {1,1,1},
            {1,1,1}};

    Integer[][] filter;
    public WeighedMeanFilterTranformation(Integer[][] filter) {
        super((filter.length - 1) / 2,true);
        this.filter = filter;
    }

    @Override
    Color processNeighbors(List<ColorPixel> neighbors) {
        throw new IllegalStateException("Not normalized");
    }

    @Override
    DenormalizedColor processNeighborsDenormalized(List<ColorPixel> neighbors) {
        double red = 0;
        double blue = 0;
        double green = 0;
        Double alpha = null;

        Integer totalWeight=0;
        for (ColorPixel cp: neighbors) {
            Integer weight = filter[cp.getPixel().getX()][cp.getPixel().getY()];
            totalWeight += weight;

            Color color = cp.getColor();
            red += color.getRed()*weight;
            blue += color.getBlue()*weight;
            green += color.getGreen()*weight;
            alpha = alpha==null?color.getOpacity():alpha;
        }
        if(totalWeight == 0){
            totalWeight = 1;
        }
        return new DenormalizedColor(red/(neighbors.size()*totalWeight),
                green/(neighbors.size()*totalWeight),
                blue/(neighbors.size()*totalWeight),
                alpha);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeighedMeanFilterTranformation that = (WeighedMeanFilterTranformation) o;
        return Arrays.equals(filter, that.filter);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(filter);
    }
}
