package transformations.normal.image;

import backend.transformators.FullTransformation;
import backend.utils.ColorUtils;
import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.utils.Utils;
import javafx.scene.image.Image;
import transformations.denormalized.ImageOperator;

public class AddImageTransformation implements FullTransformation {
	private ImageOperator imageOperator;

	public AddImageTransformation(DenormalizedImage extraImage) {
		this.imageOperator = new ImageOperator(extraImage,ColorUtils::addColors);
	}
	public AddImageTransformation(Image extraImage) {
		this.imageOperator = new ImageOperator(Utils.getAnormalized(extraImage),ColorUtils::addColors);
	}

	@Override
	public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
		return imageOperator.transformDenormalized(denormalizedImage);
	}

	@Override
	public String getDescription() {
		return "Add image";
	}
}
