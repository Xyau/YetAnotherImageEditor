package backend.combiners;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.Filter;
import backend.utils.ColorUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static backend.utils.ColorUtils.getGreyscale;
import static backend.utils.CombinerUtils.getCenterPixel;

public class SusanCombiner implements Combiner {

	@Override
	public DenormalizedColor combine(List<DenormalizedColorPixel> colorPixels, Double[][] filter){
		DenormalizedColorPixel middlePixel = getCenterPixel(colorPixels,new Filter(filter));
		DenormalizedColor midGray = getGreyscale(middlePixel.getColor());

		int counter = 0;

		List<DenormalizedColorPixel> validPixels = colorPixels.stream()
                .filter(p -> filter[p.getPixel().getX()][p.getPixel().getY()] != 0)
                .collect(Collectors.toList());

		for (DenormalizedColorPixel cp: validPixels) {
            DenormalizedColor pixGray = getGreyscale(cp.getColor());
            DenormalizedColor diff = ColorUtils.substractColors(midGray, pixGray);
            if (Math.abs(diff.getRed()) < 0.105) {
                counter += 1;
            }
        }

        if (21 <= counter && counter <= 24) {
            // Border
            return new DenormalizedColor(1.0,0.0,0.0,1.0);
        }
        else if (12 <= counter && counter <= 15) {
            // Corner
            return new DenormalizedColor(0.0,1.0,0.0,1.0);
        }
        return middlePixel.getColor();
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
