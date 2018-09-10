package transformations.normal;

import backend.transformators.Transformation;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Created by zion on 2018-08-29.
 */
public class RayleighDistributionNoiseTransformation implements Transformation {
    double phi;
    double noiseLevel;
    long seed;

    public RayleighDistributionNoiseTransformation(double phi, double noiseLevel, String seed) {
        this.phi = phi;
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
                double rayleigh = this.phi * (Math.sqrt(-2 * Math.log(x)));
                rayleigh = rayleigh > 1 ? 1 : rayleigh;
                rayleigh = rayleigh < 0 ? 0 : rayleigh;
                // (F(x) - F(0)) / (F(1) - F(0))
//                double rayleigh = (1 - Math.exp(-(x*x)/(2.0*phi*phi))) / (1 - Math.exp(-1.0/(2.0*phi*phi)));

                if (r.nextDouble() < noiseLevel) {
                    red = red * rayleigh;
                    green = green * rayleigh;
                    blue = blue * rayleigh;
                }

                writableImage.getPixelWriter().setColor(i, j, new Color(red, green, blue, opacity));
            }
        }
        return writableImage;
    }

    @Override
    public String getDescription () {
        return "Rayleigh Distribution Noise";
    }

}