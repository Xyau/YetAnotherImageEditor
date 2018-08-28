package transformations;

import backend.*;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

public abstract class FilterTransformation implements Transformation {
    Integer filterSize;

    Double min= Double.MAX_VALUE;
    Double max=Double.MIN_VALUE;
    Boolean normalize;

    public FilterTransformation(Integer filterSize, Boolean normalize){
        if(filterSize < 0) throw new IllegalStateException("Illegal filter size");
        this.normalize = normalize;
        this.filterSize = filterSize;
    }


    @Override
    public WritableImage transform(WritableImage writableImage) {
        WritableImage tempImage = ImageUtils.copyImage(writableImage);
        Integer width = Utils.toInteger(writableImage.getWidth());
        Integer height = Utils.toInteger(writableImage.getHeight());
        DenormalizedColor[][] denormalizedColors = new DenormalizedColor[height][width];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                List<ColorPixel> neighbors = getNeighborPixels(writableImage,i,j,filterSize);
                if(normalize){
                    DenormalizedColor denormalizedColor = processNeighborsDenormalized(neighbors);
                    denormalizedColors[j][i] = denormalizedColor;
                    max = Utils.getMax(max,denormalizedColor);
                    min = Utils.getMin(min,denormalizedColor);
                } else {
                    Color selectedColor = processNeighbors(neighbors);
                    tempImage.getPixelWriter().setColor(i,j,selectedColor);
                }
            }
        }

        if(normalize){
            ImageUtils.transferImageTo(writableImage,denormalizedColors,min,max);
        } else {
            ImageUtils.transferImageTo(writableImage,tempImage);
        }
        return writableImage;
    }



    DenormalizedColor processNeighborsDenormalized(List<ColorPixel> neighbors){
        throw new IllegalStateException("Not normalized");
    }

    Color processNeighbors(List<ColorPixel> neighbors) {
        throw new IllegalStateException("Not normalized");
    }
    List<ColorPixel> getNeighborPixels(Image image, Integer x, Integer y, Integer filterSize){
        List<ColorPixel> neighbors = new ArrayList<>();
        PixelReader pixelReader = image.getPixelReader();
        Integer xIndex = 0;
        for (int i = x-filterSize; i < x+filterSize+1; i++) {
            Integer yIndex = 0;
            for (int j = y-filterSize; j < y+filterSize+1; j++) {
                if(ImageUtils.isPixelInImage(image,i,j)){
                    neighbors.add(new ColorPixel(xIndex,yIndex,pixelReader.getColor(i,j)));
                } else {
                    neighbors.add(new ColorPixel(xIndex,yIndex,Color.BLACK));
                }
                yIndex++;
            }
            xIndex++;
        }
        return neighbors;
    }

    @Override
    public String getDescription() {
        return "Filters the image";
    }
}
