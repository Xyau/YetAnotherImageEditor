package transformations.normal;

import backend.transformators.Transformation;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


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
