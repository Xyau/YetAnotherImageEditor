package transformations.normalizers;

import backend.DenormalizedColor;
import backend.image.AnormalizedImage;
import backend.transformators.Normalizer;
import backend.utils.ColorUtils;
import backend.utils.Utils;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * If the channel has a min < 0 or a max > 1 then it renormalizes using that new max/min.
 * otherwise returns the color as is.
 */
public class MultiChannelRangeNormalizer implements Normalizer {
	@Override
	public WritableImage normalize(AnormalizedImage anormalizedImage) {
		WritableImage writableImage = new WritableImage(anormalizedImage.getWidth(),anormalizedImage.getHeight());
		Double maxRed = -Double.MAX_VALUE;
		Double minRed = Double.MAX_VALUE;
		Double maxBlue = -Double.MAX_VALUE;
		Double minBlue = Double.MAX_VALUE;
		Double maxGreen = -Double.MAX_VALUE;
		Double minGreen = Double.MAX_VALUE;

		for (int i = 0; i < anormalizedImage.getWidth(); i++) {
			for (int j = 0; j < anormalizedImage.getHeight(); j++) {
				DenormalizedColor color = anormalizedImage.getColorAt(i,j);
				minRed = Utils.getMinRed(minRed,color);
				maxRed = Utils.getMaxRed(maxRed,color);
				minBlue = Utils.getMinBlue(minBlue,color);
				maxBlue = Utils.getMaxBlue(maxBlue,color);
				minGreen = Utils.getMinGreen(minGreen,color);
				maxGreen = Utils.getMaxGreen(maxGreen,color);
			}
		}

		for (int i = 0; i < anormalizedImage.getWidth(); i++) {
			for (int j = 0; j < anormalizedImage.getHeight(); j++) {
				DenormalizedColor c = anormalizedImage.getColorAt(i,j);
				Double red = c.getRed();
				Double green = c.getGreen();
				Double blue = c.getBlue();
				if(! Utils.isInRange(c.getRed(),0.0,1.0)){
					red = ColorUtils.normalize(c.getRed(),minRed,maxRed);
				}
				if(! Utils.isInRange(c.getGreen(),0.0,1.0)){
					green = ColorUtils.normalize(c.getGreen(),minGreen,maxGreen);
				}
				if(! Utils.isInRange(c.getBlue(),0.0,1.0)){
					blue = ColorUtils.normalize(c.getBlue(),minBlue,maxBlue);
				}

				writableImage.getPixelWriter().setColor(i,j,new Color(red,green,blue,c.getAlpha()));
			}
		}

		return writableImage;
	}
}
