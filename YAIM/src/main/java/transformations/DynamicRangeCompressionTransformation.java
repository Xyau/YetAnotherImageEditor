package transformations;

import backend.ColorPixel;
import backend.ImageUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class DynamicRangeCompressionTransformation implements Transformation {


    @Override
    public WritableImage transform(WritableImage writableImage) {
        WritableImage tempImage = ImageUtils.copyImage(writableImage);
        int maxRed = 0;
        int maxGreen = 0;
        int maxBlue = 0;
        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                maxRed = writableImage.getPixelReader().getColor(i,j).getRed() * 255 > maxRed ? new Double(255 * writableImage.getPixelReader().getColor(i,j).getRed()).intValue() : maxRed;
                maxGreen = writableImage.getPixelReader().getColor(i,j).getGreen() * 255 > maxGreen ? new Double(255 * writableImage.getPixelReader().getColor(i,j).getGreen()).intValue() : maxGreen;
                maxBlue = writableImage.getPixelReader().getColor(i,j).getBlue() * 255 > maxBlue ? new Double(255 * writableImage.getPixelReader().getColor(i,j).getBlue()).intValue() : maxBlue;
            }
        }

        double c_red = 255.0 / Math.log(1 + maxRed);
        double c_green = 255.0 / Math.log(1 + maxGreen);
        double c_blue = 255.0 / Math.log(1 + maxBlue);
        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                double red = writableImage.getPixelReader().getColor(i,j).getRed() * 255;
                double green = writableImage.getPixelReader().getColor(i,j).getGreen() * 255;
                double blue = writableImage.getPixelReader().getColor(i,j).getBlue() * 255;
                red = Math.max(0.0, Math.min(1.0, c_red * Math.log(1 + red) / 255.0));
                green = Math.max(0.0, Math.min(1.0, c_green * Math.log(1 + green) / 255.0));
                blue = Math.max(0.0, Math.min(1.0, c_blue * Math.log(1 + blue) / 255.0));
                writableImage.getPixelWriter().setColor(i,j, new Color(red, green, blue, 1.0));
            }
        }

        return writableImage;
    }

    public WritableImage transform(Double[][] red, Double[][] blue, Double[][] green) {
        int maxRed = 0;
        int maxGreen = 0;
        int maxBlue = 0;
        for (int i = 0; i < red[0].length; i++) {
            for (int j = 0; j < red.length; j++) {
                maxRed = red[i][j] * 255 > maxRed ? new Double(255 * red[i][j]).intValue() : maxRed;
                maxGreen = green[i][j] * 255 > maxGreen ? new Double(255 * green[i][j]).intValue() : maxGreen;
                maxBlue = blue[i][j] * 255 > maxBlue ? new Double(255 * blue[i][j]).intValue() : maxBlue;
            }
        }

        double c_red = 255.0 / Math.log(1 + maxRed);
        double c_green = 255.0 / Math.log(1 + maxGreen);
        double c_blue = 255.0 / Math.log(1 + maxBlue);
        WritableImage writableImage = new WritableImage(red[0].length, red.length);
        for (int i = 0; i < red[0].length; i++) {
            for (int j = 0; j < red.length; j++) {
                double actual_red = red[i][j] * 255;
                double actual_green = green[i][j] * 255;
                double actual_blue = blue[i][j] * 255;
                actual_red = Math.max(0.0, Math.min(1.0, c_red * Math.log(1 + actual_red) / 255.0));
                actual_green = Math.max(0.0, Math.min(1.0, c_green * Math.log(1 + actual_green) / 255.0));
                actual_blue = Math.max(0.0, Math.min(1.0, c_blue * Math.log(1 + actual_blue) / 255.0));
                writableImage.getPixelWriter().setColor(i,j, new Color(actual_red, actual_green, actual_blue, 1.0));
            }
        }
        return writableImage;
    }

    @Override
    public String getDescription() {
        return "Applies dynamic range compression";
    }
}
