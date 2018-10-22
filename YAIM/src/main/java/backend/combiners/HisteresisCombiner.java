package backend.combiners;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.Filter;
import backend.utils.ColorUtils;
import backend.utils.CombinerUtils;

import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static backend.utils.CombinerUtils.getPixel;

public class HisteresisCombiner implements Combiner {
    @Override
    public DenormalizedColor combine(List<DenormalizedColorPixel> colorPixels, Double[][] filter) {
        DenormalizedColorPixel middlePixel = CombinerUtils.getCenterPixel(colorPixels,new Filter(filter));
        Integer x = middlePixel.getPixel().getX();
        Integer y = middlePixel.getPixel().getY();
        List<DenormalizedColor> pixels = Arrays.asList(getPixel(colorPixels,x,y-1),
                getPixel(colorPixels,x,y+1),getPixel(colorPixels,x-1,y),getPixel(colorPixels,x+1,y)).stream()
                .map(DenormalizedColorPixel::getColor).collect(Collectors.toList());
        
        Double red=0.0, green=0.0, blue=0.0;
        for (DenormalizedColor color : pixels) {
            if (color.getRed().equals(1.0)) {
                red = 1.0;
            }
            if (color.getBlue().equals(1.0)) {
                blue = 1.0;
            }
            if (color.getGreen().equals(1.0)) {
                green = 1.0;
            }
        }
        return new DenormalizedColor(red,green,blue,middlePixel.getColor().getAlpha());
    }
}
