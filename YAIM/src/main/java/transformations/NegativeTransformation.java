package transformations;

import backend.ImageUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;


public class NegativeTransformation implements Transformation {

    @Override
    public WritableImage transform(WritableImage writableImage) {
        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                Color c = writableImage.getPixelReader().getColor(i, j);
                c = new Color(1.0-c.getRed(),1.0 - c.getGreen(),1.0 - c.getBlue(),1.0);
                writableImage.getPixelWriter().setColor(i, j, c);
            }
        }
        return writableImage;
    }

    @Override
    public String getDescription() {
        return "Negative";
    }
}
