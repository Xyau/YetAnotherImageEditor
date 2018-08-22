package transformations;

import backend.ImageUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class LoadImageTransformation implements Transformation {
    WritableImage imageToLoad;

    public LoadImageTransformation(WritableImage writableImage) {
        this.imageToLoad = writableImage;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        for (int i = 0; i < imageToLoad.getWidth(); i++) {
            for (int j = 0; j < imageToLoad.getHeight(); j++) {
                writableImage.getPixelWriter().setColor(i,j,imageToLoad.getPixelReader().getColor(i,j));
            }
        }
        return writableImage;
    }

    @Override
    public String getDescription() {
        return "Load the image to the canvas";
    }
}
