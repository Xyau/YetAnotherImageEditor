package transformations.normal.common;

import backend.transformators.DenormalizedOperation;
import backend.transformators.Normalizer;
import backend.transformators.Transformation;
import backend.utils.ImageUtils;
import backend.utils.Utils;
import javafx.scene.image.WritableImage;

import java.util.Optional;
import java.util.stream.Stream;

public abstract class SimpleTransformation implements Transformation {

	public WritableImage transform(WritableImage writableImage, DenormalizedOperation operation, Normalizer normalizer){
		return Optional.of(writableImage)
				.map(Utils::getAnormalized)
				.map(operation::transformDenormalized)
				.map(normalizer::normalize)
				.map(resultImage -> ImageUtils.transferImageTo(writableImage,resultImage))
				.get();
	}
}
