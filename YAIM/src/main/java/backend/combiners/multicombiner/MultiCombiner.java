package backend.combiners.multicombiner;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.Filter;
import backend.combiners.Combiner;
import java.util.Collections;
import java.util.List;

public interface MultiCombiner extends Combiner {

    DenormalizedColor combineMulti(List<List<DenormalizedColorPixel>> multiColorPixels, Filter filter);

    @Override
    default DenormalizedColor combine(List<DenormalizedColorPixel> colorPixels, Double[][] filter) {
        return combineMulti(Collections.singletonList(colorPixels),new Filter(filter));
    }
}
