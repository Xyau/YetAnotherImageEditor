package transformations.normal.image;

import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.ColorUtils;
import backend.utils.Utils;
import javafx.scene.image.Image;
import transformations.denormalized.ImageOperator;

public class MinusImageTransformation implements FullTransformation {
    private ImageOperator imageOperator;

    public MinusImageTransformation(Image image) {
        this.imageOperator = new ImageOperator(Utils.getAnormalized(image),ColorUtils::substractColors);
    }

    @Override
    public DenormalizedImage transformDenormalized(AnormalizedImage anormalizedImage) {
        return imageOperator.transformDenormalized(anormalizedImage);
    }

    @Override
    public String getDescription() {
        return "Substract Images";
    }
}
