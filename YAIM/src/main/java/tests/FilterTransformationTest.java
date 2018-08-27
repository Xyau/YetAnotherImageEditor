package tests;

import backend.ColorPixel;
import backend.ImageUtils;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import transformations.FilterTransformation;
import transformations.MedianFilterTransformation;

import java.util.ArrayList;
import java.util.List;

public class FilterTransformationTest {

    @Test
    public void testFilter(){
        MedianFilterTransformation medianFilterTransformation = new MedianFilterTransformation(3);
        List<ColorPixel> pixelList = new ArrayList<>();
        pixelList.add(new ColorPixel(0,0,Color.BLACK));
        pixelList.add(new ColorPixel(0,1,Color.WHITE));
        pixelList.add(new ColorPixel(0,1,Color.WHITE));
        medianFilterTransformation.processNeighbors(pixelList);
    }
}
