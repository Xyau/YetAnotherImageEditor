package backend.combiners;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.Filter;
import backend.utils.ColorUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import static backend.utils.ColorUtils.multiplyColors;

public class TropicCombiner implements Combiner{
	Double std;
	static Double LAMBDA = 0.1;

	private BiFunction<Double,Double,Double> LECLERC_DETECTOR = (intensity, std) -> Math.exp(-intensity*intensity/(std*std));
	private BiFunction<Double,Double,Double> LORENTZIAN_DETECTOR = (intensity, std) -> 1/(1+(intensity*intensity/(std*std)));

	public TropicCombiner(Double std) {
		this.std = std;
	}

	@Override
	public DenormalizedColor combine(List<DenormalizedColorPixel> colorPixels, Double[][] filter) {
		DenormalizedColorPixel middlePixel = getCenterPixel(colorPixels,new Filter(filter));

		//This are the coefficients, we have to multiply this by the difference
		DenormalizedColorPixel upPixel = getPixel(colorPixels,middlePixel.getPixel().getX(),middlePixel.getPixel().getY()-1);
		DenormalizedColorPixel downPixel = getPixel(colorPixels,middlePixel.getPixel().getX(),middlePixel.getPixel().getY()+1);
		DenormalizedColorPixel rightPixel = getPixel(colorPixels,middlePixel.getPixel().getX()+1,middlePixel.getPixel().getY());
		DenormalizedColorPixel leftPixel = getPixel(colorPixels,middlePixel.getPixel().getX()-1,middlePixel.getPixel().getY());

		DenormalizedColor upDiff = ColorUtils.substractColors(upPixel.getColor(),middlePixel.getColor());
		DenormalizedColor downDiff = ColorUtils.substractColors(downPixel.getColor(),middlePixel.getColor());
		DenormalizedColor rightDiff = ColorUtils.substractColors(rightPixel.getColor(),middlePixel.getColor());
		DenormalizedColor leftDiff = ColorUtils.substractColors(leftPixel.getColor(),middlePixel.getColor());

		DenormalizedColor upC = ColorUtils.transform(upDiff, intensity -> LORENTZIAN_DETECTOR.apply(intensity,std));
		DenormalizedColor downC = ColorUtils.transform(downDiff, intensity -> LORENTZIAN_DETECTOR.apply(intensity,std));
		DenormalizedColor rightC = ColorUtils.transform(rightDiff, intensity -> LORENTZIAN_DETECTOR.apply(intensity,std));
		DenormalizedColor leftC = ColorUtils.transform(leftDiff, intensity -> LORENTZIAN_DETECTOR.apply(intensity,std));

		//sum all the differences multiplied by the
		DenormalizedColor raw = ColorUtils.multiplyColors(ColorUtils.addColors(multiplyColors(upC,upDiff)
													,multiplyColors(downC,downDiff)
													,multiplyColors(rightC,rightDiff)
													,multiplyColors(leftC,leftDiff)),LAMBDA);

		DenormalizedColor result = ColorUtils.addColors(middlePixel.getColor(),raw);
		return result;
	}

	public DenormalizedColorPixel getPixel(List<DenormalizedColorPixel> pixels, Integer x, Integer y){
		return pixels.stream()
				.filter( p -> p.getPixel().getX().equals(x) && p.getPixel().getY().equals(y))
				.findFirst()
				.get();
	}


	public DenormalizedColorPixel getCenterPixel(List<DenormalizedColorPixel> pixels, Filter filter){
		Integer x = filter.getWidth()/2;
		Integer y = filter.getHeight()/2;

		return getPixel(pixels,x,y);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TropicCombiner that = (TropicCombiner) o;
		return Objects.equals(std, that.std);
	}

	@Override
	public int hashCode() {

		return Objects.hash(std);
	}
}
