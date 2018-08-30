package transformations;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Created by zion on 2018-08-29.
 */
public class ExponentialDistributionNoiseTransformation implements Transformation{
    double lambda;

    public ExponentialDistributionNoiseTransformation() {
        this.lambda = 0.05;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        PixelReader pixelReader = writableImage.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        Random r = new Random();
        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                Color c = pixelReader.getColor(i, j);
                double red = c.getRed();
                double green = c.getGreen();
                double blue = c.getBlue();
                double opacity = c.getOpacity();
                double x = 0.0;

                while (x == 0.0) {
                    x = r.nextDouble();

                }
                double exponential = -1 * (Math.log(x) / this.lambda);

                red = red * exponential;
                green = green * exponential;
                blue = blue * exponential;
                opacity = opacity * exponential;

                writableImage.getPixelWriter().setColor(i, j, new Color(red, green, blue, opacity));
            }
        }
        return writableImage;
    }

    @Override
    public String getDescription () {
        return "Exponential Distribution Noise";
    }
}
