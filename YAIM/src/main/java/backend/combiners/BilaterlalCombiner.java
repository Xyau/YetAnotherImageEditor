package backend.combiners;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.Filter;
import backend.utils.ColorUtils;
import backend.utils.Utils;

import javax.swing.text.html.ListView;
import java.util.List;
import java.util.Objects;

import static backend.utils.CombinerUtils.getCenterPixel;

public class BilaterlalCombiner implements Combiner{
	Double colorStd;
	Double spatialStd;

	public BilaterlalCombiner(Double colorStd, Double spatialStd) {
		this.colorStd = colorStd;
		this.spatialStd = spatialStd;
	}

	@Override
	public DenormalizedColor combine(List<DenormalizedColorPixel> colorPixels, Double[][] filter) {
		Double red = 0.0;
		Double blue = 0.0;
		Double green = 0.0;
		Double alpha = null;

		Double totalWeight=0.0;

		DenormalizedColorPixel middlePixel = getCenterPixel(colorPixels,new Filter(filter));
		for (DenormalizedColorPixel cp: colorPixels) {
			Double weight = filter[cp.getPixel().getY()][cp.getPixel().getX()];
			weight *= Math.exp(-getColorWeight(colorStd,middlePixel.getColor(),cp.getColor())
								-getSpatialWeight(spatialStd,middlePixel,cp));
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

	private Double getSpatialWeight(Double spatialStd, DenormalizedColorPixel pixel1, DenormalizedColorPixel pixel2){
		return (difference(pixel1.getPixel().getX(),pixel2.getPixel().getX()) +
				difference(pixel1.getPixel().getY(),pixel2.getPixel().getY()))/(2*spatialStd*spatialStd);
	}

	private Double getColorWeight(Double colorStd, DenormalizedColor color1, DenormalizedColor color2){
		return ColorUtils.getModulus(ColorUtils.substractColors(color1,color2))/(2*colorStd*colorStd);
	}

	public Double difference(Integer n1, Integer n2){
		return Math.pow(n1-n2,2);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BilaterlalCombiner that = (BilaterlalCombiner) o;
        return Objects.equals(colorStd, that.colorStd) &&
                Objects.equals(spatialStd, that.spatialStd);
    }

    @Override
    public int hashCode() {

        return Objects.hash(colorStd, spatialStd);
    }
}
