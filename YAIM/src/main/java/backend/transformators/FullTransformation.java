package backend.transformators;

import backend.image.DenormalizedImage;
import backend.utils.ImageUtils;
import backend.utils.Utils;
import javafx.scene.image.WritableImage;
import transformations.normalizers.MultiChannelNormalizer;
import transformations.normalizers.MultiChannelRangeNormalizer;

import java.util.Optional;

/**
 * A {@link FullTransformation} can both transform normalized or denormalized to be used as required.
 * The default for transforming normalized is provided. So implementing classes dont have to implement
 * the transform everytime. This uses a {@link MultiChannelRangeNormalizer} to normalize by default.
 */
public interface FullTransformation extends Transformation, DenormalizedTransformation {
	@Override
	default WritableImage transform(WritableImage writableImage){
		return Optional.of(writableImage)
				.map(DenormalizedImage::new)
				.map(this::transformDenormalized)
				.map(new MultiChannelNormalizer()::normalize)
				.map(resultImage -> ImageUtils.transferImageTo(writableImage,resultImage))
				.get();
	}

	static WritableImage transform(WritableImage writableImage, DenormalizedTransformation denormalizedTransformation, Normalizer normalizer){
		return Optional.of(writableImage)
				.map(DenormalizedImage::new)
				.map(denormalizedTransformation::transformDenormalized)
				.map(normalizer::normalize)
				.map(resultImage -> ImageUtils.transferImageTo(writableImage,resultImage))
				.get();
	}
}
