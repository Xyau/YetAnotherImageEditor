package transformations.normal;

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
                ColorUtils.combineColors(color1, color2, (x, y) -> x == 0 ? Math.PI/2 : Math.atan(y/x))
            ));
        DenormalizedImage sobelBorders = new DistanceImageTransformation(Gy).transformDenormalized(Gx);

        for (int i = 0; i < denormalizedImage.getWidth(); i++) {
            for (int j = 0; j < denormalizedImage.getHeight(); j++) {
                DenormalizedColor borderAngles = getClampedAngles(angles.getColorAt(i, j));
                DenormalizedColor borderIntensity = sobelBorders.getColorAt(i, j);

            }
        }
        return null;
    }

    private DenormalizedColor getClampedAngles(DenormalizedColor color) {
        return ColorUtils.transform(color, x -> {
            if (x < Math.toRadians(22.5)) return -Math.PI/2;
            if (x < Math.toRadians(67.5)) return -Math.PI/4;
            if (x < Math.toRadians(112.5)) return 0.0;
            if (x < Math.toRadians(157.5)) return Math.PI/4;
            return -Math.PI/2;
        });
    }

    @Override
    public String getDescription () {
        return "Angle Transformation";
    }

}
