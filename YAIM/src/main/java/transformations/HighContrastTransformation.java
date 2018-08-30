package transformations;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Random;


/**
 * Created by zion on 2018-08-25.
 */
public class HighContrastTransformation implements Transformation {

    @Override
    public WritableImage transform(WritableImage writableImage) {
        PixelReader pixelReader = writableImage.getPixelReader();
        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                Color c = pixelReader.getColor(i, j);
                double red = c.getRed();
                double green = c.getGreen();
                double blue = c.getBlue();
                double opacity = c.getOpacity();

                red = Math.max(0.0, Math.min(1.0, (2 * red - 1) * (2 * red - 1)));
                green = Math.max(0.0, Math.min(1.0, (2 * green - 1) * (2 * green - 1)));
                blue = Math.max(0.0, Math.min(1.0, (2 * blue - 1) * (2 * blue - 1)));

                writableImage.getPixelWriter().setColor(i, j, new Color(red, green, blue, opacity));
            }
        }
        return writableImage;
    }

    @Override
    public String getDescription () {
        return "Additive Gaussian Noise";
    }
}