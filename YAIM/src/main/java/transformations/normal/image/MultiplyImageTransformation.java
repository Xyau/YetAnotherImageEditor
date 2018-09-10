package transformations.normal.image;

import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.ColorUtils;
import backend.utils.Utils;
import javafx.scene.image.Image;
import transformations.denormalized.ImageOperator;

public class MultiplyImageTransformation implements FullTransformation {
    private ImageOperator imageOperator;

    public MultiplyImageTransformation(Image image) {
        this.imageOperator = new ImageOperator(Utils.getAnormalized(image),ColorUtils::multiplyColors);
    }

    @Override
    public DenormalizedImage transformDenormalized(AnormalizedImage anormalizedImage) {
        return imageOperator.transformDenormalized(anormalizedImage);
    }

    @Override
    public String getDescription() {
        return "Sums 2 images";
    }
}
