package transformations;

import backend.ImageUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;


public class BinaryTransformation implements Transformation {

    double threashold;

    public BinaryTransformation(double threashold) {
        this.threashold = threashold;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                Color c = writableImage.getPixelReader().getColor(i, j);
                double red = c.getRed() < threashold ? 0.0 : 1.0;
                double green = c.getGreen() < threashold ? 0.0 : 1.0;
                double blue = c.getBlue() < threashold ? 0.0 : 1.0;
                c = new Color(red, green, blue, 1.0);
                writableImage.getPixelWriter().setColor(i, j, c);
            }
        }
        return writableImage;
    }

    @Override
    public String getDescription() {
        return "Binary";
    }
}