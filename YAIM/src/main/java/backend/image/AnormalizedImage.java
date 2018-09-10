package backend.image;

import backend.DenormalizedColor;

/**
 * This image can be normal or not, it does not matter
 */
public interface AnormalizedImage {
	DenormalizedColor getColorAt(Integer x, Integer y);

	Integer getHeight();

	Integer getWidth();

}
