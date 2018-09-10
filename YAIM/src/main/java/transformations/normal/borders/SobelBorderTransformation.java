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

public class SobelBorderTransformation implements Transformation {

    @Override
    public WritableImage transform(WritableImage writableImage) {
        DenormalizedOperation verticalOperation = new WindowMeanOperator(FiltersRepository.SOBEL_VERTICAL);
        DenormalizedImage verticalImage = verticalOperation.transformDenormalized(Utils.getAnormalized(writableImage));
        DenormalizedOperation horizontalOperation = new WindowMeanOperator(FiltersRepository.SOBEL_HORIZONTAL);
        DenormalizedImage horizontalImage = horizontalOperation.transformDenormalized(Utils.getAnormalized(writableImage));

        DenormalizedOperation avgImgTransformation = new DistanceImageTransformation(verticalImage);
        DenormalizedImage resultImage = avgImgTransformation.transformDenormalized(horizontalImage);

        Normalizer normalizer = new GlobalNormalizer();
        return ImageUtils.transferImageTo(writableImage,normalizer.normalize(resultImage));
    }

    @Override
    public String getDescription() {
        return "Sobel border Transformation";
    }
}
