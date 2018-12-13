package transformations.normal;

import backend.DenormalizedColor;
import backend.cascading_features.Rectangle;
import backend.cascading_features.ScaledRectangle;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.ColorUtils;
import backend.utils.HaarCascadeUtils;
import javafx.scene.paint.Color;
import org.w3c.dom.css.Rect;

import java.util.List;

import static backend.utils.HaarCascadeUtils.isThereAFaceInImage;

public class ViolaJonesFaceDetectionTransformation implements FullTransformation {
    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        List<ScaledRectangle> rectangles = HaarCascadeUtils.isThereAFaceInImage(denormalizedImage);
        rectangles.stream()
                .forEach(r ->
                        new DrawEmtpySquareTransformation(r.getX(),r.getY(),r.getHeight(),r.getWidth(),DenormalizedColor.RED)
                .transformDenormalized(denormalizedImage));
        System.out.println(rectangles);
        return denormalizedImage;
    }

    @Override
    public String getDescription() {
        return "Viola Jones";
    }
}
