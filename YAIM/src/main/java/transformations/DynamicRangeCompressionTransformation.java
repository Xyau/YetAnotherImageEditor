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
//        System.out.println("MAX: " + maxRed + " " + maxGreen + " " + maxBlue);

        double c_red = 255.0 / Math.log(1 + maxRed);
        double c_green = 255.0 / Math.log(1 + maxGreen);
        double c_blue = 255.0 / Math.log(1 + maxBlue);
//        System.out.println("C: " + c_red + " " + c_green + " " + c_blue);
        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                double red = writableImage.getPixelReader().getColor(i,j).getRed();
                double green = writableImage.getPixelReader().getColor(i,j).getGreen();
                double blue = writableImage.getPixelReader().getColor(i,j).getBlue();
                red = c_red * Math.log(1 + red) / 255.0;
                green = c_green * Math.log(1 + green) / 255.0;
                blue = c_blue * Math.log(1 + blue) / 255.0;
                System.out.println(red + " " + green + " " + blue);
                writableImage.getPixelWriter().setColor(i,j, new Color(red, green, blue, 1.0));
            }
        }

        return writableImage;
    }

    @Override
    public String getDescription() {
        return "Applies dynamic range compression";
    }
}
