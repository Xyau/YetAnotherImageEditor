package transformations.normalizers;

import backend.utils.ColorUtils;
import backend.utils.Utils;
import backend.image.AnormalizedImage;
import backend.transformators.Normalizer;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GlobalNormalizer implements Normalizer {
	@Override
	public WritableImage normalize(AnormalizedImage anormalizedImage) {
		WritableImage writableImage = new WritableImage(anormalizedImage.getWidth(),anormalizedImage.getHeight());
		Double max = -Double.MAX_VALUE;
		Double min = Double.MAX_VALUE;

		for (int i = 0; i < anormalizedImage.getWidth(); i++) {
			for (int j = 0; j < anormalizedImage.getHeight(); j++) {
				min = Utils.getMin(min,anormalizedImage.getColorAt(i,j));
				max = Utils.getMax(max,anormalizedImage.getColorAt(i,j));
			}
		}

		for (int i = 0; i < anormalizedImage.getWidth(); i++) {
			for (int j = 0; j < anormalizedImage.getHeight(); j++) {
				Color c = ColorUtils.normalize(anormalizedImage.getColorAt(i,j),min,max);
				writableImage.getPixelWriter().setColor(i,j,c);
			}
		}

		return writableImage;
	}
}
