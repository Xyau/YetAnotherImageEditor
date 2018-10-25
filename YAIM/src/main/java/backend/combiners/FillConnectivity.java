package backend.combiners;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.Filter;
import backend.utils.ColorUtils;
import backend.utils.CombinerUtils;

import java.util.List;

public class FillConnectivity implements Combiner {
    private DenormalizedColor filledColor;
    private DenormalizedColor unfilledColor;
    private Integer minConnectivity;

    public FillConnectivity(DenormalizedColor filledColor, DenormalizedColor unfilledColor, Integer minConnectivity) {
        this.filledColor = filledColor;
        this.unfilledColor = unfilledColor;
        this.minConnectivity = minConnectivity;
    }

    @Override
    public DenormalizedColor combine(List<DenormalizedColorPixel> colorPixels, Double[][] filter) {
        Long connectionsCount = colorPixels.stream()
                .filter(cpx -> filter[cpx.getPixel().getY()][cpx.getPixel().getY()] > 0)
                .map(DenormalizedColorPixel::getColor)
                .filter(c -> ColorUtils.isGreater(c, filledColor))
                .count();

        DenormalizedColor centerColor = CombinerUtils.getCenterPixel(colorPixels, new Filter(filter)).getColor();

        if (ColorUtils.isGreater(centerColor, filledColor) || connectionsCount >= minConnectivity) {
            return filledColor;
        }
        return unfilledColor;
    }
}
