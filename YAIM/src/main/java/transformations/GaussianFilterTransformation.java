package transformations;

import backend.ColorPixel;
import backend.ImageUtils;
import backend.Utils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GaussianFilterTransformation extends FilterTransformation {

    public GaussianFilterTransformation(Integer filterSize) {
        super(filterSize);
    }

    Color processNeighbors(List<ColorPixel> neighbors) {
        Double std = Utils.getStandardDeviation(neighbors.stream()
                .map(ColorPixel::getColor)
                .map(Color::getBrightness).collect(Collectors.toList()));

        Double totalWeight = 0.0;

        double red = 0;
        double blue = 0;
        double green = 0;
        double alpha = 0;

        for (ColorPixel cp: neighbors) {
            Double weight = Utils.getGaussianFilterWeight(std,cp.getPixel().getX(),cp.getPixel().getY());
            totalWeight += weight;

            Color color = cp.getColor();
            red += color.getRed()*weight;
            blue += color.getBlue()*weight;
            green += color.getGreen()*weight;
            alpha += color.getOpacity()*weight;
        }

        return new Color(red/(neighbors.size()*totalWeight),
                green/(neighbors.size()*totalWeight),
                blue/(neighbors.size()*totalWeight),
                alpha/(neighbors.size()*totalWeight));
    }

    @Override
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
        return null;
    }
}
