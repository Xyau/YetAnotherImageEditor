package backend.transformators;

import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.utils.Utils;
import javafx.scene.image.WritableImage;

/**
 * This is a transformation that returns a {@link DenormalizedImage}.
 */
public interface DenormalizedTransformation {
	DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage);
}
