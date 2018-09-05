package transformations.image;

import backend.ColorUtils;
import backend.ImageUtils;
import backend.Pixel;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import transformations.Transformation;

public class AddImageTransformation implements Transformation {
    private Image image;

    public AddImageTransformation(Image image) {
        this.image = image;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        return ImageUtils.transformImagesNormalized(writableImage,image, ColorUtils::addColors);
    }

    @Override
    public String getDescription() {
        return "Sums 2 images";
    }
}
