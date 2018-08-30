package transformations;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.util.Random;


/**
 * Created by zion on 2018-08-25.
 */
public class AdditiveGaussianNoiseTransformation implements Transformation {
    double mean;
    double sigma;
    Long seed;

    public AdditiveGaussianNoiseTransformation(Long seed, double mean, double sigma) {
        this.mean = mean;
        this.sigma = sigma;
        this.seed = seed;
    }

    public AdditiveGaussianNoiseTransformation(String seed, Double saltRatio, Double pepperRatio) {
        this(new Long(seed.hashCode()),saltRatio,pepperRatio);
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
                double x1 = 0.0;
                double x2 = 0.0;


                while ((x1 == 0.0) || (x2 == 0.0)) {
                    x1 = r.nextDouble();
                    x2 = r.nextDouble();
                }
                double deviation = Math.sqrt(-2*Math.log(x1)) * Math.cos(2*Math.PI*x2);
                double noise = this.mean + (deviation * this.sigma);


                red = red + noise;
                green = green + noise;
                blue = blue + noise;
                opacity = opacity + noise;

                if (red > 1.0) {
                    red = 1.0;
                }
                else if (red < 0.0) {
                    red = 0.0;
                }
                if (green > 1.0) {
                    green = 1.0;
                }
                else if (green < 0.0) {
                    green = 0.0;
                }
                if (blue > 1.0) {
                    blue = 1.0;
                }
                else if (blue < 0.0) {
                    blue = 0.0;
                }
                if (opacity > 1.0) {
                    opacity = 1.0;
                }
                else if (opacity < 0.0) {
                    opacity = 0.0;
                }
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