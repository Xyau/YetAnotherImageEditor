package transformations.normalizers;

import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.transformators.Normalizer;
import backend.utils.ColorUtils;
import backend.utils.Utils;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GlobalRangeNormalizer implements Normalizer {
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
				if(min < 0 || max > 1){
					Double red = ColorUtils.normalize(c.getRed(),min,max);
					Double green = ColorUtils.normalize(c.getGreen(),min,max);
					Double blue = ColorUtils.normalize(c.getBlue(),min,max);
					c = new Color(red,green,blue,c.getOpacity());
				}
				writableImage.getPixelWriter().setColor(i,j,c);
			}
		}

		return writableImage;
	}
}
