package transformations.colors;

import javafx.scene.paint.Color;
import transformations.common.PixelByPixelTransformation;

import java.util.function.Function;

public class DarkenTransformation extends PixelByPixelTransformation {
    private static Function<Color,Color> DARKEN = Color::darker;
    public DarkenTransformation() {
        super(DARKEN);
    }

    @Override
    public String getDescription() {
        return "Darkens the image";
    }
}
