package transformations.normal.borders;

import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import javafx.scene.image.WritableImage;
import transformations.normal.filters.LaplacianFilterTransformation;
import transformations.normalizers.MultiChannelNormalizer;

import java.util.Optional;

public class LaplacianBorderTransformation implements FullTransformation {

    @Override
    public WritableImage transform(WritableImage writableImage) {
        return FullTransformation.transform(writableImage,this,new MultiChannelNormalizer());
     }

    @Override
    public String getDescription() {
        return "Full Laplacian border Transformation";
    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        return Optional.of(denormalizedImage)
                .map(new LaplacianFilterTransformation()::transformDenormalized)
                .map(new ZeroFindingTransformation()::transformDenormalized)
                .get();
    }
}
