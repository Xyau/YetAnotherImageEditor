package transformations.normal;

import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.HaarCascadeUtils;

public class ViolaJonesFaceDetectionTransformation implements FullTransformation {
    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        HaarCascadeUtils.isThereAFaceInImage(denormalizedImage);
        return denormalizedImage;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
