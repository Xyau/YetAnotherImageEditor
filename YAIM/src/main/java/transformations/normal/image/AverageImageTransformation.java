package transformations.normal.image;

import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.ColorUtils;
import backend.utils.Utils;
import javafx.scene.image.Image;
import transformations.denormalized.ImageOperator;

public class AverageImageTransformation implements FullTransformation {
    private ImageOperator imageOperator;

    public AverageImageTransformation(Image image) {
        this.imageOperator = new ImageOperator(Utils.getAnormalized(image),ColorUtils::averagingColors);
    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        return imageOperator.transformDenormalized(denormalizedImage);
    }

    @Override
    public String getDescription() {
        return "Sums 2 images";
    }
}
