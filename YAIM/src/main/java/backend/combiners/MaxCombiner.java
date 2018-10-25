package backend.combiners;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.Filter;
import backend.combiners.Combiner;
import backend.utils.ColorUtils;
import backend.utils.CombinerUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MaxCombiner implements Combiner {
    private DenormalizedColor minimum = DenormalizedColor.BLACK;

    public MaxCombiner(DenormalizedColor minimum) {
        this.minimum = minimum;
    }

    public MaxCombiner(){}

    @Override
    public DenormalizedColor combine(List<DenormalizedColorPixel> colorPixels, Double[][] filter) {
        List<DenormalizedColor> filtered = colorPixels.stream()
                .filter(cpx -> filter[cpx.getPixel().getY()][cpx.getPixel().getY()] > 0)
                .map(DenormalizedColorPixel::getColor)
                .filter(c -> c.getRed() >= minimum.getRed() ||
                        c.getGreen() >= minimum.getGreen()  ||
                        c.getBlue() >= minimum.getBlue())
                .collect(Collectors.toList());

        Optional<DenormalizedColor> maxOptional = filtered.stream()
                .reduce(ColorUtils::getMax);

        DenormalizedColor max;
        if(maxOptional.isPresent()){
            max = maxOptional.get();
        } else {
            return CombinerUtils.getCenterPixel(colorPixels, new Filter(filter)).getColor();
        }
        return filtered.stream()
                .filter(c -> c.getRed().equals(max.getRed()) ||
                        c.getGreen().equals(max.getGreen())  ||
                        c.getBlue().equals(max.getBlue())).reduce(ColorUtils::getMax).get();
    }
}
