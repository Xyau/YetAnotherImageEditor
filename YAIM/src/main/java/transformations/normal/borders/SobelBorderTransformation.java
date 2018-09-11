package transformations.normal.borders;

import backend.image.DenormalizedImage;
import backend.transformators.DenormalizedTransformation;
import backend.transformators.Normalizer;
import backend.utils.ImageUtils;
import javafx.scene.image.WritableImage;
import backend.transformators.Transformation;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanOperator;
import transformations.normal.image.DistanceImageTransformation;
import transformations.normalizers.GlobalNormalizer;
import transformations.normalizers.MultiChannelNormalizer;

public class SobelBorderTransformation implements Transformation {

    @Override
    public WritableImage transform(WritableImage writableImage) {
        DenormalizedTransformation verticalOperation = new WindowMeanOperator(FiltersRepository.SOBEL_VERTICAL);
        DenormalizedImage verticalImage = verticalOperation.transformDenormalized(new DenormalizedImage(writableImage));
        DenormalizedTransformation horizontalOperation = new WindowMeanOperator(FiltersRepository.SOBEL_HORIZONTAL);
        DenormalizedImage horizontalImage = horizontalOperation.transformDenormalized(new DenormalizedImage(writableImage));

        DenormalizedTransformation distanceImageTransformation = new DistanceImageTransformation(verticalImage);
        DenormalizedImage resultImage = distanceImageTransformation.transformDenormalized(horizontalImage);

        Normalizer normalizer = new MultiChannelNormalizer();
        return ImageUtils.transferImageTo(writableImage,normalizer.normalize(resultImage));
    }

    @Override
    public String getDescription() {
        return "Sobel border Transformation";
    }
}