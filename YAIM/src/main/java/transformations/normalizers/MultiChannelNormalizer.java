package transformations.normalizers;

import backend.utils.ColorUtils;
import backend.DenormalizedColor;
import backend.utils.Utils;
import backend.image.AnormalizedImage;
import backend.transformators.Normalizer;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class MultiChannelNormalizer implements Normalizer {
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
				Double red = ColorUtils.normalize(c.getRed(),minRed,maxRed);
				Double green = ColorUtils.normalize(c.getGreen(),minGreen,maxGreen);
				Double blue = ColorUtils.normalize(c.getBlue(),minBlue,maxBlue);
				writableImage.getPixelWriter().setColor(i,j,new Color(red,green,blue,c.getAlpha()));
			}
		}

		return writableImage;
	}
}
