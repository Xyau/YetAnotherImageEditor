package transformations.normal.borders;

import backend.combiners.ZeroesFinderCombiner;
import backend.image.DenormalizedImage;
import backend.transformators.DenormalizedTransformation;
import backend.transformators.FullTransformation;
import javafx.scene.image.WritableImage;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowOperator;
import transformations.normal.image.DistanceImageTransformation;
import transformations.normalizers.MultiChannelNormalizer;

public class ZeroFindingTransformation implements FullTransformation {
    public String getDescription() {
        return "Zero finding";
    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        DenormalizedTransformation horizontalZeroes = new WindowOperator(FiltersRepository.HORIZONTAL_ZEROES,new ZeroesFinderCombiner());
        DenormalizedTransformation verticalZeroes = new WindowOperator(FiltersRepository.VERTICAL_ZEROES,new ZeroesFinderCombiner());

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
