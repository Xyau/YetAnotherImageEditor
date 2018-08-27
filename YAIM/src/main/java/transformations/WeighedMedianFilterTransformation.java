package transformations;

import backend.ColorPixel;
import backend.ImageUtils;
import backend.Utils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WeighedMedianFilterTransformation extends FilterTransformation{
    public Integer[][] WEIGHTED_3x3 = new Integer[][]{  {1,2,1},
                                                        {2,4,2},
                                                        {1,2,1},};

    public WeighedMedianFilterTransformation() {
        super(1);
    }

    @Override
    List<ColorPixel> getNeighborPixels(Image image, Integer x, Integer y, Integer filterSize){
        List<ColorPixel> neighbors = new ArrayList<>();
        PixelReader pixelReader = image.getPixelReader();
        Integer xIndex = 0;
        for (int i = x-filterSize; i < x+filterSize+1; i++) {
            Integer yIndex = 0;
            for (int j = y-filterSize; j < y+filterSize+1; j++) {
                for (int k = 0; k < WEIGHTED_3x3[xIndex][yIndex]; k++) {
                    if(ImageUtils.isPixelInImage(image,i,j)){
                        neighbors.add(new ColorPixel(i,j,pixelReader.getColor(i,j)));
                    } else {
                        neighbors.add(new ColorPixel(i,j,Color.BLACK));
                    }
                }
                yIndex++;
            }
            xIndex++;
        }
        return neighbors;
    }

    //Public for testing
    @Override
    public Color processNeighbors(List<ColorPixel> neighbors) {
        neighbors.sort(Comparator.comparingDouble(c -> c.getColor().getBrightness()));
        return neighbors.get(Utils.toInteger(neighbors.size()/2)).getColor();
    }

    @Override
    public String getDescription() {
        return null;
    }
}