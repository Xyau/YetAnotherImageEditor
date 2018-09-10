package transformations.normalizers;

import backend.image.AnormalizedImage;
import backend.transformators.Normalizer;
import backend.utils.ColorUtils;
import backend.utils.Utils;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GlobalRangeNormalizer implements Normalizer {
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
