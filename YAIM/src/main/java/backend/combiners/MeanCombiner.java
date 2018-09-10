package backend.combiners;

import backend.ColorPixel;
import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Objects;

public class MeanCombiner implements Combiner{

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

		for (DenormalizedColorPixel cp: colorPixels) {
			Double weight = filter[cp.getPixel().getX()][cp.getPixel().getY()];
			totalWeight += weight;

			DenormalizedColor color = cp.getColor();
			red += color.getRed()*weight;
			blue += color.getBlue()*weight;
			green += color.getGreen()*weight;
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
