package backend.transformators;

import backend.utils.ImageUtils;
import backend.utils.Utils;
import javafx.scene.image.WritableImage;
import transformations.normalizers.MultiChannelRangeNormalizer;

import java.util.Optional;

public interface FullTransformation extends Transformation, DenormalizedOperation {
	@Override
	public default WritableImage transform(WritableImage writableImage){
		return Optional.of(writableImage)
				.map(Utils::getAnormalized)
				.map(this::transformDenormalized)
				.map(new MultiChannelRangeNormalizer()::normalize)
				.map(resultImage -> ImageUtils.transferImageTo(writableImage,resultImage))
				.get();
	}
}
