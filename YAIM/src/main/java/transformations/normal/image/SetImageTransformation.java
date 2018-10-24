package transformations.normal.image;

import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.transformators.Transformation;
import backend.utils.ImageUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class SetImageTransformation implements Transformation {
    private Image image;

    public SetImageTransformation(Image image) {
        this.image = image;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        return ImageUtils.transferImageTo(writableImage,image);
    }

    @Override
    public String getDescription() {
        return "Transfers an image to another";
    }
}
