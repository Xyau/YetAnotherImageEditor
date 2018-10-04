package backend.combiners;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.Filter;
import backend.utils.ColorUtils;

import java.util.List;
import java.util.Objects;

public class TropicCombiner implements Combiner{
	Double std;

	public TropicCombiner(Double std) {
		this.std = std;
	}

	@Override
	public DenormalizedColor combine(List<DenormalizedColorPixel> colorPixels, Double[][] filter) {
		Double red = 0.0;
		Double blue = 0.0;
		Double green = 0.0;
		Double alpha = null;

		Double totalWeight=0.0;

		DenormalizedColorPixel middlePixel = getCenterPixel(colorPixels,new Filter(filter));
//		DenormalizedColorPixel northPixel = colorPixels.stream().filter( x -> x.getPixel().equals(new));
//			totalWeight += weight;
//
//			DenormalizedColor color = cp.getColor();
//			red += color.getRed()*weight;
//			blue += color.getBlue()*weight;
//			green += color.getGreen()*weight;
//			alpha = alpha==null?color.getAlpha():alpha;
		if(totalWeight.equals(0.0)){
			totalWeight = 1.0;
		}
		return new DenormalizedColor(red/(totalWeight),
				green/(totalWeight),
				blue/(totalWeight),
				alpha);
	}



	public DenormalizedColorPixel getCenterPixel(List<DenormalizedColorPixel> pixels, Filter filter){
		Integer x = filter.getWidth()/2;
		Integer y = filter.getHeight()/2;

		return pixels.stream()
				.filter( p -> p.getPixel().getX().equals(x) && p.getPixel().getY().equals(y))
				.findFirst()
				.get();
	}
}
