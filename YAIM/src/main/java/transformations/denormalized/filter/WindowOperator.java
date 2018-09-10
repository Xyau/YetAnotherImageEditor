package transformations.denormalized.filter;

import backend.ColorPixel;
import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.combiners.Combiner;
import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.transformators.DenormalizedOperation;
import backend.transformators.Normalizer;
import backend.utils.ImageUtils;
import backend.utils.Utils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class WindowOperator implements DenormalizedOperation {
    private Double[][] filter;
    private Combiner combiner;
    private Integer filterSize;

    public WindowOperator(Double[][] filter, Combiner combiner){
        this.filterSize = (filter.length - 1) / 2;
        this.filter = filter;
        this.combiner = combiner;
    }

    @Override
    public DenormalizedImage transformDenormalized(AnormalizedImage anormalizedImage) {
        Integer width = anormalizedImage.getWidth();
        Integer height = anormalizedImage.getHeight();
        DenormalizedImage denormalizedImage = new DenormalizedImage(width,height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                List<DenormalizedColorPixel> neighbors = getNeighborPixels(anormalizedImage,i,j,filterSize);
                DenormalizedColor processedColor = combiner.combine(neighbors,filter);
                denormalizedImage.setColor(i,j,processedColor);
            }
        }
        return denormalizedImage;
    }

    List<DenormalizedColorPixel> getNeighborPixels(AnormalizedImage image, Integer x, Integer y, Integer filterSize){
        List<DenormalizedColorPixel> neighbors = new ArrayList<>();
        Integer xIndex = 0;
        for (int i = x-filterSize; i < x+filterSize+1; i++) {
            Integer yIndex = 0;
            for (int j = y-filterSize; j < y+filterSize+1; j++) {
                if(ImageUtils.isPixelInImage(image,i,j)){
                    neighbors.add(new DenormalizedColorPixel(xIndex,yIndex,image.getColorAt(i,j)));
                } else {
                    neighbors.add(new DenormalizedColorPixel(xIndex,yIndex,new DenormalizedColor(0.0,0.0,0.0,0.0)));
                }
                yIndex++;
            }
            xIndex++;
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WindowOperator that = (WindowOperator) o;
        return Arrays.equals(filter, that.filter) &&
                Objects.equals(combiner, that.combiner);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(combiner);
        result = 31 * result + Arrays.hashCode(filter);
        return result;
    }
}
