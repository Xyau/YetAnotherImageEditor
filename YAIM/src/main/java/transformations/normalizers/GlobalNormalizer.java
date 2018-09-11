package transformations.normalizers;

import backend.image.DenormalizedImage;
import backend.utils.ColorUtils;
import backend.utils.Utils;
import backend.image.AnormalizedImage;
import backend.transformators.Normalizer;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GlobalNormalizer implements Normalizer {
	@Override
	public WritableImage normalize(DenormalizedImage denormalizedImage) {
		WritableImage writableImage = new WritableImage(denormalizedImage.getWidth(),denormalizedImage.getHeight());
		Double max = -Double.MAX_VALUE;
		Double min = Double.MAX_VALUE;

		for (int i = 0; i < denormalizedImage.getWidth(); i++) {
			for (int j = 0; j < denormalizedImage.getHeight(); j++) {
				min = Utils.getMin(min,denormalizedImage.getColorAt(i,j));
				max = Utils.getMax(max,denormalizedImage.getColorAt(i,j));
			}
		}

		for (int i = 0; i < denormalizedImage.getWidth(); i++) {
			for (int j = 0; j < denormalizedImage.getHeight(); j++) {
				Color c = ColorUtils.normalize(denormalizedImage.getColorAt(i,j),min,max);
				writableImage.getPixelWriter().setColor(i,j,c);
			}
		}

		return writableImage;
	}
}
