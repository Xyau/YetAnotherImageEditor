package backend.combiners.multicombiner;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.Filter;
import backend.Pixel;
import backend.utils.ColorUtils;
import backend.utils.CombinerUtils;

import java.util.List;

public class CannyMultiCombiner implements MultiCombiner {
    @Override
    public DenormalizedColor combineMulti(List<List<DenormalizedColorPixel>> multiColorPixels, Filter filter) {
        List<DenormalizedColorPixel> intensities = multiColorPixels.get(0);
        List<DenormalizedColorPixel> angles = multiColorPixels.get(1);

        DenormalizedColorPixel intensity = CombinerUtils.getCenterPixel(intensities,filter);
        DenormalizedColorPixel angle = CombinerUtils.getCenterPixel(angles,filter);

//        if(ColorUtils.getBrightness(angle.getColor()==))
        return null;
    }
}
