package backend.combiners;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.utils.ColorUtils;
import backend.utils.Utils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MedianCombiner implements Combiner {

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getClass());
	}

	@Override
	public DenormalizedColor combine(List<DenormalizedColorPixel> colorPixels, Double[][] filter) {
		colorPixels.sort(Comparator.comparingDouble(cp -> ColorUtils.getBrightness(cp.getColor())));
		return colorPixels.get(Utils.toInteger(colorPixels.size()/2)).getColor();
	}
}
