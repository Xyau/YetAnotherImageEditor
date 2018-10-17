package transformations.normal.borders;

import backend.image.DenormalizedImage;
import backend.transformators.DenormalizedTransformation;
import backend.transformators.FullTransformation;
import backend.transformators.Normalizer;
import backend.utils.ImageUtils;
import javafx.scene.image.WritableImage;
import backend.transformators.Transformation;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanTransformation;
import transformations.normal.image.DistanceImageTransformation;
import transformations.normalizers.MultiChannelNormalizer;

public class SobelBorderTransformation implements FullTransformation {

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        DenormalizedImage verticalImage = new WindowMeanTransformation(FiltersRepository.SOBEL_VERTICAL).transformDenormalized(denormalizedImage);
        DenormalizedImage horizontalImage = new WindowMeanTransformation(FiltersRepository.SOBEL_HORIZONTAL).transformDenormalized(denormalizedImage);

        return new DistanceImageTransformation(verticalImage).transformDenormalized(horizontalImage);
    }

    @Override
    public String getDescription() {
        return "Sobel border Transformation";
    }

}
