package backend.combiners;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.utils.ColorUtils;
import backend.utils.Utils;

import java.util.List;
import java.util.Objects;

public class ZeroesFinderCombiner implements Combiner{

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
		Double red = 0.0;
		Double blue = 0.0;
		Double green = 0.0;
		Double alpha = null;

		Double totalWeight=0.0;

		DenormalizedColor prevColor = null;
		for (DenormalizedColorPixel cp: colorPixels) {
			Double weight = filter[cp.getPixel().getY()][cp.getPixel().getX()];
			totalWeight += weight;

			DenormalizedColor color = cp.getColor();
			if(prevColor == null){
				prevColor = color;
			} else {
				red = color.getRed() * prevColor.getRed() <= 0?(Math.abs(color.getRed()-prevColor.getRed())):0.0;
				blue = color.getBlue() * prevColor.getBlue() <= 0?(Math.abs(color.getBlue()-prevColor.getBlue())):0.0;
				green = color.getGreen() * prevColor.getGreen() <= 0?(Math.abs(color.getGreen()-prevColor.getGreen())):0.0;
			}
			alpha = alpha==null?color.getAlpha():alpha;
		}
		if(totalWeight.equals(0.0)){
			totalWeight = 1.0;
		}
		return new DenormalizedColor(red/(totalWeight),
				green/(totalWeight),
				blue/(totalWeight),
				alpha);
	}
}
