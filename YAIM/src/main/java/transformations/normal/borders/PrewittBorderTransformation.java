package transformations.normal.borders;

import backend.image.DenormalizedImage;
import backend.transformators.DenormalizedOperation;
import backend.transformators.Normalizer;
import backend.utils.ImageUtils;
import backend.utils.Utils;
import javafx.scene.image.WritableImage;
import backend.transformators.Transformation;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanOperator;
import transformations.normal.image.AverageImageTransformation;
import transformations.normal.image.DistanceImageTransformation;
import transformations.normalizers.GlobalNormalizer;
import transformations.normalizers.MultiChannelRangeNormalizer;

import java.util.Objects;

public class PrewittBorderTransformation implements Transformation {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(1);
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        DenormalizedOperation verticalOperation = new WindowMeanOperator(FiltersRepository.PREWITT_VERTICAL);
        DenormalizedImage verticalImage = verticalOperation.transformDenormalized(Utils.getAnormalized(writableImage));
        DenormalizedOperation horizontalOperation = new WindowMeanOperator(FiltersRepository.PREWITT_HORIZONTAL);
        DenormalizedImage horizontalImage = horizontalOperation.transformDenormalized(Utils.getAnormalized(writableImage));

        DenormalizedOperation avgImgTransformation = new DistanceImageTransformation(verticalImage);
        DenormalizedImage resultImage = avgImgTransformation.transformDenormalized(horizontalImage);

        Normalizer normalizer = new GlobalNormalizer();
        return ImageUtils.transferImageTo(writableImage,normalizer.normalize(resultImage));
    }

    @Override
    public String getDescription() {
        return "Prewitt Filter Transformation";
    }
}
