package transformations;

import backend.ColorPixel;
import backend.ImageUtils;
import backend.Pixel;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public abstract class FilterTransformation implements Transformation {
    Integer filterSize;


    public FilterTransformation(Integer filterSize){
        if(filterSize < 0) throw new IllegalStateException("Illegal filter size");
        this.filterSize = filterSize;
    }


    @Override
    public WritableImage transform(WritableImage writableImage) {
        WritableImage tempImage = ImageUtils.copyImage(writableImage);
        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                List<ColorPixel> neighbors = getNeighborPixels(writableImage,i,j,filterSize);
                Color selectedColor = processNeighbors(neighbors);
                tempImage.getPixelWriter().setColor(i,j,selectedColor);
            }
        }

        ImageUtils.transferImageTo(writableImage,tempImage);
        return writableImage;
    }

    abstract Color processNeighbors(List<ColorPixel> neighbors);

    List<ColorPixel> getNeighborPixels(Image image, Integer x, Integer y, Integer filterSize){
        List<ColorPixel> neighbors = new ArrayList<>();
        PixelReader pixelReader = image.getPixelReader();
        for (int i = x-filterSize; i < x+filterSize+1; i++) {
            for (int j = y-filterSize; j < y+filterSize+1; j++) {
                if(ImageUtils.isPixelInImage(image,i,j)){
                    neighbors.add(new ColorPixel(i-x-filterSize,j,pixelReader.getColor(i,j)));
                } else {
                    neighbors.add(new ColorPixel(i,j,Color.BLACK));
                }
            }
        }
        return neighbors;
    }

    @Override
    public String getDescription() {
        return "Filters the image";
    }
}
