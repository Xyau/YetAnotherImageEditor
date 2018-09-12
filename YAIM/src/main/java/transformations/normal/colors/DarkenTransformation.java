package transformations.normal.colors;

import backend.DenormalizedColor;
import backend.utils.ColorUtils;
import javafx.scene.paint.Color;
import transformations.normal.common.PixelByPixelTransformation;

import java.util.function.Function;

public class DarkenTransformation extends PixelByPixelTransformation {
    private static Function<DenormalizedColor, DenormalizedColor> DARKEN = ColorUtils::getDarker;
    public DarkenTransformation() {
        super(DARKEN);
    }

    @Override
    public String getDescription() {
        return "Darkens the image";
    }
}
