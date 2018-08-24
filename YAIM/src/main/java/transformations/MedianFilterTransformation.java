package transformations;

import backend.ColorPixel;
import backend.ImageUtils;
import backend.Pixel;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class MedianFilterTransformation implements Transformation {


    @Override
    public WritableImage transform(WritableImage writableImage) {
        WritableImage tempImage = ImageUtils.copyImage(writableImage);
        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                List<ColorPixel> neighbors = getNeighborPixels(writableImage,i,j,3);
                tempImage.getPixelWriter().setColor(i,j,processNeighbors(neighbors));
            }
        }

        ImageUtils.transferImageTo(writableImage,tempImage);
        return writableImage;
    }

    private Color processNeighbors(List<ColorPixel> neighbors) {
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

    private List<ColorPixel> getNeighborPixels(Image image, Integer x, Integer y, Integer filterSize){
        List<ColorPixel> neighbors = new ArrayList<>();
        PixelReader pixelReader = image.getPixelReader();
        for (int i = x-filterSize; i < x+filterSize+1; i++) {
            for (int j = y-filterSize; j < y+filterSize+1; j++) {
                if(ImageUtils.isPixelInImage(image,i,j)){
                    neighbors.add(new ColorPixel(i,j,pixelReader.getColor(i,j)));
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
