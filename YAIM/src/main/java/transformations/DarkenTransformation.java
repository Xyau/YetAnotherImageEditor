package transformations;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class DarkenTransformation implements Transformation {
    @Override
    public WritableImage transform(WritableImage writableImage) {
        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                Color c = writableImage.getPixelReader().getColor(i,j).darker();
                writableImage.getPixelWriter().setColor(i,j,c);
            }
        }
        return writableImage;
    }

    @Override
    public String getDescription() {
        return "Darkens the image";
    }
}
