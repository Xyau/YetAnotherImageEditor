package backend.combiners;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.Filter;
import backend.utils.ColorUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

import static backend.utils.ColorUtils.getGreyscale;
import static backend.utils.ColorUtils.substractColors;
import static backend.utils.CombinerUtils.getCenterPixel;
import static backend.utils.CombinerUtils.getPixel;

public class SusanCombiner implements Combiner {

	@Override
	public DenormalizedColor combine(List<DenormalizedColorPixel> colorPixels, Double[][] filter) {
		DenormalizedColorPixel middlePixel = getCenterPixel(colorPixels, new Filter(filter));
		DenormalizedColor midGray = getGreyscale(middlePixel.getColor());
		int counter = 0;
		long susanValue=0;

		for (DenormalizedColorPixel cp : colorPixels) {
			DenormalizedColor pixGray = getGreyscale(cp.getColor());
			DenormalizedColor diff = ColorUtils.substractColors(midGray, pixGray);
			System.out.println(diff.getRed());
			if (Math.abs(diff.getRed()) < 0.105) {
				counter += 1;
			} else {
				counter += 0;
			}

			susanValue = 1 - (counter / (2 * filter.length + 1) ^ 2);
			//System.out.println(susanValue);
		}
		if (0.45 <= susanValue && susanValue < 0.55) {
			return new DenormalizedColor(1.0, 0.0, 0.0, 0.5);
		} else if (0.70 <= susanValue && susanValue < 0.80) {
			return new DenormalizedColor(0.0, 1.0, 0.0, 0.5);
		} else {
			return new DenormalizedColor(0.0, 0.0, 1.0, 0.5);
		}
	}


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
}
