package backend.image;

import backend.utils.ColorUtils;
import backend.DenormalizedColor;
import backend.utils.Utils;
import javafx.scene.image.Image;

public class AnormalizedImageImpl implements AnormalizedImage {
	Image image;

	public AnormalizedImageImpl(Image image) {
		this.image = image;
	}

	@Override
	public DenormalizedColor getColorAt(Integer x, Integer y) {
		return ColorUtils.denormalizeColor(image.getPixelReader().getColor(x,y));
	}

	@Override
	public Integer getHeight() {
		return Utils.toInteger(image.getHeight());
	}

	@Override
	public Integer getWidth() {
		return Utils.toInteger(image.getWidth());
	}
}
