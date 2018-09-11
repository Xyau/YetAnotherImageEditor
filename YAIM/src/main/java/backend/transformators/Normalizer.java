package backend.transformators;

import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import javafx.scene.image.WritableImage;

public interface Normalizer {
	WritableImage normalize(DenormalizedImage anormalizedImage);
}
