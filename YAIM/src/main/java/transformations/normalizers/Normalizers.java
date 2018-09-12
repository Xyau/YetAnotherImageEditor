package transformations.normalizers;

import backend.image.DenormalizedImage;
import backend.transformators.Normalizer;
import javafx.scene.image.WritableImage;

public enum  Normalizers implements Normalizer {
    GLOBAL{
        @Override
        public WritableImage normalize(DenormalizedImage anormalizedImage) {
            return new GlobalNormalizer().normalize(anormalizedImage);
        }
    },
    GLOBAL_RANGE,
    ;


    @Override
    public WritableImage normalize(DenormalizedImage anormalizedImage) {
        return null;
    }
}
