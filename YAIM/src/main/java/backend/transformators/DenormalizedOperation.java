package backend.transformators;

import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.utils.Utils;
import javafx.scene.image.WritableImage;

public interface DenormalizedOperation {
	DenormalizedImage transformDenormalized(AnormalizedImage anormalizedImage);
}
