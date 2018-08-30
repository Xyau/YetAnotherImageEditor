package transformations;

import backend.ColorUtils;
import backend.ImageUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class MinusImageTransformation implements Transformation {
    private Image image;

    public MinusImageTransformation(Image image) {
        this.image = image;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        return ImageUtils.transformImagesNormalizedMultiChannel(writableImage,image,ColorUtils::substractColors);
    }

    @Override
    public String getDescription() {
        return null;
    }
}
