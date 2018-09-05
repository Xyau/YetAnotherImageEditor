package transformations.image;

import backend.ColorUtils;
import backend.ImageUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import transformations.Transformation;

public class AverageImageTransformation implements Transformation {
    private Image image;

    public AverageImageTransformation(Image image) {
        this.image = image;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        return ImageUtils.transformImagesNormalized(writableImage,image, ColorUtils::averagingColors);
    }

    @Override
    public String getDescription() {
        return "Sums 2 images";
    }
}
