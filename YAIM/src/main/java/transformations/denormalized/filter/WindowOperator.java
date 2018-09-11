package transformations.denormalized.filter;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.combiners.Combiner;
import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.transformators.DenormalizedTransformation;
import backend.utils.ImageUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class WindowOperator implements DenormalizedTransformation {
    private Double[][] filter;
    private Combiner combiner;

    private Integer filterHeight;
    private Integer filterWidth;

    public WindowOperator(Double[][] filter, Combiner combiner){
        this.filterHeight = filter.length;
        this.filterWidth = filter.length == 0? 0 : filter[0].length;
        this.filter = filter;
        this.combiner = combiner;
    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage anormalizedImage) {
        Integer width = anormalizedImage.getWidth();
        Integer height = anormalizedImage.getHeight();
        DenormalizedImage denormalizedImage = new DenormalizedImage(width,height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                List<DenormalizedColorPixel> neighbors = getNeighborPixels(anormalizedImage,i,j,filterHeight,filterWidth);
                DenormalizedColor processedColor = combiner.combine(neighbors,filter);
                denormalizedImage.setColor(i,j,processedColor);
            }
        }
        return denormalizedImage;
    }

    public List<DenormalizedColorPixel> getNeighborPixels(AnormalizedImage image, Integer x, Integer y, Integer filterHeight, Integer filterWidth){
        List<DenormalizedColorPixel> neighbors = new ArrayList<>();
        Integer xIndex = 0;
        Integer xOffsetStart = -(filterWidth/2);
        Integer xOffsetEnd = filterWidth + xOffsetStart;

        Integer yOffsetStart = -(filterHeight/2);
        Integer yOffsetEnd = filterHeight + yOffsetStart;
        for (int i = x+xOffsetStart; i < x+xOffsetEnd; i++) {
            Integer yIndex = 0;
            for (int j = y+yOffsetStart; j < y+yOffsetEnd; j++) {
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
