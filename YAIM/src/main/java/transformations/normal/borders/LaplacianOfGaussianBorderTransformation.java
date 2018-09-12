package transformations.normal.borders;

import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import javafx.scene.image.WritableImage;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanTransformation;
import transformations.normal.filters.LaplacianFilterTransformation;
import transformations.normalizers.MultiChannelNormalizer;

import java.util.Optional;

public class LaplacianOfGaussianBorderTransformation implements FullTransformation {

    @Override
    public WritableImage transform(WritableImage writableImage) {
        return FullTransformation.transform(writableImage,this,new MultiChannelNormalizer());
     }

    @Override
    public String getDescription() {
        return "Laplacian of Gaussian Border";
    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        return Optional.of(denormalizedImage)
                .map(new WindowMeanTransformation(FiltersRepository.LOG)::transformDenormalized)
                .map(new ZeroFindingTransformation()::transformDenormalized)
                .get();
    }
}
