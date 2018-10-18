package transformations.normal.canny;

import backend.DenormalizedColor;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.transformators.Transformation;
import backend.utils.ColorUtils;
import backend.utils.ImageUtils;
import javafx.scene.image.WritableImage;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanTransformation;
import transformations.denormalized.filter.WindowOperator;
import transformations.normal.borders.SobelBorderTransformation;
import transformations.normal.filters.GaussianMeanFilterTransformation;
import transformations.normal.image.DistanceImageTransformation;
import transformations.normal.image.ModulusImageTransformation;

import java.io.FilterReader;

public class OrthogonalAngleDirectionTransformation implements FullTransformation {

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        DenormalizedImage Gx = new WindowMeanTransformation(FiltersRepository.SOBEL_HORIZONTAL).transformDenormalized(denormalizedImage);
        DenormalizedImage Gy = new WindowMeanTransformation(FiltersRepository.SOBEL_VERTICAL).transformDenormalized(denormalizedImage);

        DenormalizedImage angles = ImageUtils.transformImages(new DenormalizedImage(Gx), Gy, ((color1, color2) ->
                ColorUtils.combineColors(color1, color2, (x, y) -> x == 0 ? 90.0 : Math.toDegrees(Math.atan(y/x)))
            ));
        return angles;
    }

    @Override
    public String getDescription () {
        return "Angle Transformation";
    }

}
