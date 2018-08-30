package transformations;

import backend.ColorUtils;
import backend.ImageUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class MultiplyImageTransformation implements Transformation {
    private Image image;

    public MultiplyImageTransformation(Image image) {
        this.image = image;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        return ImageUtils.transformImagesNormalizedMultiChannel(writableImage,image, ColorUtils::multiplyColors);
    }

    @Override
    public String getDescription() {
        return "Sums 2 images";
    }
}
