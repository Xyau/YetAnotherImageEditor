package backend.transformators;

import backend.image.AnormalizedImage;
import javafx.scene.image.WritableImage;

public interface Normalizer {
	WritableImage normalize(AnormalizedImage anormalizedImage);
}
