package transformations.normal;

import backend.DenormalizedColor;
import backend.transformators.FullTransformation;
import backend.transformators.Transformation;
import backend.utils.ColorUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import transformations.normal.common.PixelByPixelTransformation;

import java.util.function.Function;


public class NegativeTransformation extends PixelByPixelTransformation {
    public NegativeTransformation() {
        super(ColorUtils::negative);
    }

    @Override
    public String getDescription() {
        return "Negative";
    }
}
