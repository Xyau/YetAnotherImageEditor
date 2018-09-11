package transformations.normal.borders;

import backend.image.DenormalizedImage;
import backend.transformators.DenormalizedTransformation;
import backend.transformators.FullTransformation;
import backend.transformators.Normalizer;
import backend.transformators.Transformation;
import backend.utils.ImageUtils;
import javafx.scene.image.WritableImage;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanOperator;
import transformations.normal.filters.LaplacianFilterTransformation;
import transformations.normal.image.DistanceImageTransformation;
import transformations.normalizers.MultiChannelNormalizer;

import java.util.Optional;

public class LaplacianBorderTransformation implements FullTransformation {

    @Override
    public WritableImage transform(WritableImage writableImage) {
        return FullTransformation.transform(writableImage,this,new MultiChannelNormalizer());
     }

    @Override
    public String getDescription() {
        return "Sobel border Transformation";
    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        return Optional.of(denormalizedImage)
                .map(new LaplacianFilterTransformation()::transformDenormalized)
                .map(new ZeroFindingTransformation()::transformDenormalized)
                .get();
    }
}
