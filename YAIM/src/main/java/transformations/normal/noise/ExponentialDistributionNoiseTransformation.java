package transformations.normal.noise;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import backend.transformators.Transformation;

import java.util.Objects;
import java.util.Random;

/**
 * Created by zion on 2018-08-29.
 */
public class ExponentialDistributionNoiseTransformation implements Transformation {
    double lambda;
    double noiseLevel;
    long seed;

    public ExponentialDistributionNoiseTransformation(double lambda, double noiseLevel, String seed) {
        this.lambda = lambda;
        this.noiseLevel = noiseLevel;
        this.seed = seed.trim().hashCode();
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        PixelReader pixelReader = writableImage.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        Random r = new Random(seed);
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
//                double exponential = Math.log(x) / -this.lambda;
                double exponential = -Math.log(1 - (1 - Math.exp(-lambda)) * x) / lambda;

                if (r.nextDouble() < this.noiseLevel) {
                    red = red * exponential;
                    green = green * exponential;
                    blue = blue * exponential;
                }

                writableImage.getPixelWriter().setColor(i, j, new Color(red, green, blue, opacity));
            }
        }
        return writableImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExponentialDistributionNoiseTransformation that = (ExponentialDistributionNoiseTransformation) o;
        return Double.compare(that.lambda, lambda) == 0 &&
                Double.compare(that.noiseLevel, noiseLevel) == 0 &&
                seed == that.seed;
    }

    @Override
    public int hashCode() {

        return Objects.hash(lambda, noiseLevel, seed);
    }

    @Override
    public String getDescription () {
        return "Exponential Distribution Noise";
    }
}
