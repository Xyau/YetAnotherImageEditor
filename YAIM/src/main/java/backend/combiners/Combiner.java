package backend.combiners;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;

import java.util.List;

public interface Combiner {
	DenormalizedColor combine(List<DenormalizedColorPixel> colorPixels, Double[][] filter);
}
