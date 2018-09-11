package transformations.normalizers;

import backend.DenormalizedColor;
import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.transformators.Normalizer;
import backend.utils.ColorUtils;
import backend.utils.ImageUtils;
import backend.utils.Utils;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class TruncatingNormalizer implements Normalizer {
	@Override
	public WritableImage normalize(DenormalizedImage denormalizedImage) {
		WritableImage writableImage = new WritableImage(denormalizedImage.getWidth(),denormalizedImage.getHeight());

		for (int i = 0; i < denormalizedImage.getWidth(); i++) {
			for (int j = 0; j < denormalizedImage.getHeight(); j++) {
				DenormalizedColor color = denormalizedImage.getColorAt(i,j);
				Double red = Utils.getInRange(color.getRed(),0.0,1.0);
				Double green = Utils.getInRange(color.getGreen(),0.0,1.0);
				Double blue = Utils.getInRange(color.getBlue(),0.0,1.0);
				writableImage.getPixelWriter().setColor(i,j,new Color(red,green,blue,color.getAlpha()));
			}
		}
		return writableImage;
	}
}
