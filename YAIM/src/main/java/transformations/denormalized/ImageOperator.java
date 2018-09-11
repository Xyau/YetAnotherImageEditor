package transformations.denormalized;

import backend.DenormalizedColor;
import backend.transformators.DenormalizedTransformation;
import backend.utils.ImageUtils;
import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;

import java.util.function.BiFunction;

public class ImageOperator implements DenormalizedTransformation {
	private BiFunction<DenormalizedColor, DenormalizedColor, DenormalizedColor> colorCombinator;
	private AnormalizedImage extraImage;

	public ImageOperator(AnormalizedImage extraImage, BiFunction<DenormalizedColor, DenormalizedColor, DenormalizedColor> colorCombinator) {
		this.colorCombinator = colorCombinator;
		this.extraImage = extraImage;
	}

	@Override
	public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
		return ImageUtils.transformImages(denormalizedImage,extraImage, colorCombinator);
	}
}
