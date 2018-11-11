package transformations.denormalized;

import backend.DenormalizedColor;
import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.transformators.DenormalizedTransformation;
import backend.utils.ImageUtils;
import backend.utils.TriFunction;

import java.util.function.BiFunction;

public class TriImageOperator implements DenormalizedTransformation {
	private TriFunction<DenormalizedColor, DenormalizedColor> colorCombinator;
	private AnormalizedImage extraImage;
	private AnormalizedImage extraImage2;

	public TriImageOperator(AnormalizedImage extraImage, AnormalizedImage extraImage2, TriFunction<DenormalizedColor, DenormalizedColor> colorCombinator) {
		this.colorCombinator = colorCombinator;
		this.extraImage = extraImage;
		this.extraImage2 = extraImage2;
	}

	@Override
	public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
		return ImageUtils.transformImages(denormalizedImage, extraImage, extraImage2, colorCombinator);
	}
}
