package transformations.denormalized.filter;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.Filter;
import backend.combiners.Combiner;
import backend.combiners.multicombiner.MultiCombiner;
import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.transformators.DenormalizedTransformation;
import backend.utils.ImageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static transformations.denormalized.filter.WindowOperator.getNeighborPixels;

public class MultiWindowOperator implements DenormalizedTransformation{
    private Double[][] filter;
    private MultiCombiner combiner;
    public DenormalizedImage extraImage;

    private Integer filterHeight;
    private Integer filterWidth;

    public MultiWindowOperator(Double[][] filter, MultiCombiner combiner, AnormalizedImage extraImage){
        this.filterHeight = filter.length;
        this.filterWidth = filter.length == 0? 0 : filter[0].length;
        this.filter = filter;
        this.combiner = combiner;
        this.extraImage = extraImage == null ? null : new DenormalizedImage(extraImage);
    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage anormalizedImage) {
        Integer width = anormalizedImage.getWidth();
        Integer height = anormalizedImage.getHeight();
        DenormalizedImage denormalizedImage = new DenormalizedImage(width,height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                List<List<DenormalizedColorPixel>> multiNeighbors = new ArrayList<>();
                multiNeighbors.add(getNeighborPixels(anormalizedImage,i,j,filterHeight,filterWidth));
                multiNeighbors.add(getNeighborPixels(extraImage,i,j,filterHeight,filterWidth));

                DenormalizedColor processedColor = combiner.combineMulti(multiNeighbors, new Filter(filter));
                denormalizedImage.setColor(i,j,processedColor);
            }
        }
        return denormalizedImage;
    }



    @Override
    public int hashCode() {
        int result = Objects.hash(combiner,extraImage);
        result = 31 * result + Arrays.hashCode(filter);
        return result;
    }
}
