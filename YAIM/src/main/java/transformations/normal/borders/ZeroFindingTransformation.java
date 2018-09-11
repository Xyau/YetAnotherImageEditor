package transformations.normal.borders;

import backend.image.DenormalizedImage;
import backend.transformators.DenormalizedTransformation;
import backend.transformators.FullTransformation;
import backend.transformators.Transformation;
import javafx.scene.image.WritableImage;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanOperator;
import transformations.normal.image.AddImageTransformation;
import transformations.normal.image.DistanceImageTransformation;
import transformations.normalizers.GlobalNormalizer;
import transformations.normalizers.MultiChannelNormalizer;

public class ZeroFindingTransformation implements FullTransformation {
    public String getDescription() {
        return "Zero finding";
    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        DenormalizedTransformation horizontalZeroes = new WindowMeanOperator(FiltersRepository.HORIZONTAL_ZEROES);
        DenormalizedTransformation verticalZeroes = new WindowMeanOperator(FiltersRepository.VERTICAL_ZEROES);

        //Make a copy of the original and apply vertical borders
        DenormalizedImage vertical = verticalZeroes.transformDenormalized(new DenormalizedImage(denormalizedImage));
        DenormalizedImage horizontal = horizontalZeroes.transformDenormalized(denormalizedImage);

        //Get the modulus of the two images
        DenormalizedImage result = new DistanceImageTransformation(vertical).transformDenormalized(horizontal);
        return result;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        return FullTransformation.transform(writableImage, this,new MultiChannelNormalizer());
    }
}
