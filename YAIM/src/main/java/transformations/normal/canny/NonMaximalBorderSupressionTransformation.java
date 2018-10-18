package transformations.normal.canny;

import backend.DenormalizedColor;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.ColorUtils;
import backend.utils.ImageUtils;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanTransformation;
import transformations.normal.borders.SobelBorderTransformation;
import transformations.normal.image.DistanceImageTransformation;

public class NonMaximalBorderSupressionTransformation implements FullTransformation {

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        DenormalizedImage angles = new OrthogonalAngleDirectionTransformation().transformDenormalized(new DenormalizedImage(denormalizedImage));
        DenormalizedImage intensities = new SobelBorderTransformation().transformDenormalized(denormalizedImage);


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
