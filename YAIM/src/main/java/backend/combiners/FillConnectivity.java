package backend.combiners;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.Filter;
import backend.utils.CombinerUtils;

import java.util.List;

public class FillConnectivity implements Combiner {
    private DenormalizedColor filledColor;
    private Integer minConnectivity;

    public FillConnectivity(DenormalizedColor filledColor, Integer minConnectivity) {
        this.filledColor = filledColor;
        this.minConnectivity = minConnectivity;
    }

    @Override
    public DenormalizedColor combine(List<DenormalizedColorPixel> colorPixels, Double[][] filter) {
        Long connectionsCount = colorPixels.stream()
                .filter(cpx -> filter[cpx.getPixel().getY()][cpx.getPixel().getY()] > 0)
                .map(DenormalizedColorPixel::getColor)
                .filter(c -> c.getRed() >= filledColor.getRed() ||
                        c.getGreen() >= filledColor.getGreen() ||
                        c.getBlue() >= filledColor.getBlue())
                .count();

        if (connectionsCount >= minConnectivity) {
            return filledColor;
        }
        return CombinerUtils.getCenterPixel(colorPixels, new Filter(filter)).getColor();
    }
}
