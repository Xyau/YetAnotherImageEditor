package transformations.normal.image;
import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.ColorUtils;
import backend.utils.Utils;
import javafx.scene.image.Image;
import transformations.denormalized.ImageOperator;

public class DistanceImageTransformation implements FullTransformation {
	private ImageOperator imageOperator;

	public DistanceImageTransformation(Image extraImage) {
		this.imageOperator = new ImageOperator(Utils.getAnormalized(extraImage),ColorUtils::distanceColors);
	}

	public DistanceImageTransformation(DenormalizedImage extraImage) {
		this.imageOperator = new ImageOperator(extraImage,ColorUtils::distanceColors);
	}

	@Override
	public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
		return imageOperator.transformDenormalized(denormalizedImage);
	}

	@Override
	public String getDescription() {
		return "Euclidean Distance between image";
	}
}
