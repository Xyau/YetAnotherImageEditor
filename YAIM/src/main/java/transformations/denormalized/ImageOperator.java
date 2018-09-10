package transformations.denormalized;

import backend.DenormalizedColor;
import backend.utils.ImageUtils;
import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.transformators.DenormalizedOperation;

import java.util.function.BiFunction;

public class ImageOperator implements DenormalizedOperation {
	private BiFunction<DenormalizedColor, DenormalizedColor, DenormalizedColor> colorCombinator;
	private AnormalizedImage extraImage;

	public ImageOperator(AnormalizedImage extraImage, BiFunction<DenormalizedColor, DenormalizedColor, DenormalizedColor> colorCombinator) {
		this.colorCombinator = colorCombinator;
		this.extraImage = extraImage;
	}

	@Override
	public DenormalizedImage transformDenormalized(AnormalizedImage anormalizedImage) {
		return ImageUtils.transformImages(anormalizedImage,extraImage, colorCombinator);
	}
}
