package transformations.normal.image;

import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.ColorUtils;
import backend.utils.Utils;
import javafx.scene.image.Image;
import transformations.denormalized.ImageOperator;

public class HighlightRedImageTransformation implements FullTransformation {
    private ImageOperator imageOperator;

    public HighlightRedImageTransformation(Image image) {
        this.imageOperator = new ImageOperator(Utils.getAnormalized(image),ColorUtils::highlightRed);
    }
   public HighlightRedImageTransformation(AnormalizedImage image) {
        this.imageOperator = new ImageOperator(image,ColorUtils::highlightRed);
    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        return imageOperator.transformDenormalized(denormalizedImage);
    }

    @Override
    public String getDescription() {
        return "Highlights red";
    }
}
