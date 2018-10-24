package transformations.normal.umbrals;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.combiners.Combiner;
import backend.combiners.HisteresisCombiner;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.ColorUtils;
import backend.utils.Utils;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowOperator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class HisteresisUmbralizationTransformation implements FullTransformation {
    private double min;
    private double max;

    public HisteresisUmbralizationTransformation(double min, double max) {
        this.max = max;
        this.min = min;
    }

//    @Override
//    public DenormalizedImage transformDenormalized(DenormalizedImage anormalizedImage) {
//        new MultiChannelTernaryTransformation(min,max).transformDenormalized(anormalizedImage);
//        List<DenormalizedColorPixel> middlePixels = new ArrayList<>();
//        for (int i = 0; i < anormalizedImage.getWidth(); i++) {
//            for (int j = 0; j < anormalizedImage.getHeight(); j++) {
//                DenormalizedColor binarized = ColorUtils.transform(anormalizedImage.getColorAt(i,j),
//                        ch -> ch < min ? 0.0 : ch > max ? 1.0 : ch);
//                anormalizedImage.setColor(i,j,binarized);
//                if(!ColorUtils.anyMatchChannel(binarized, ch -> ch.equals( 1.0) || ch.equals(0.0))){
//                    middlePixels.add(new DenormalizedColorPixel(i,j,binarized));
//                }
//            }
//        }
//        List<DenormalizedColorPixel> otherList = new ArrayList<>();
//        Integer prevSize = 0;
//        //Temporary skip until we figure out what 4-conexo means
//            for (DenormalizedColorPixel dcpx : middlePixels){
//                Integer x=dcpx.getPixel().getX(),y=dcpx.getPixel().getY();
//                Double red=dcpx.getColor().getRed(), green=dcpx.getColor().getGreen(), blue=dcpx.getColor().getBlue();
//                Boolean colored = false;
//                for(DenormalizedColor dc :Arrays.asList(anormalizedImage.getColorAt(x+1,y),anormalizedImage.getColorAt(x-1,y),anormalizedImage.getColorAt(x,y+1),anormalizedImage.getColorAt(x,y-1))){
//                    if(dc.getRed().equals(1.0)) {
//                        red = 1.0;
//                        colored = true;
//                    }
//                    if(dc.getGreen().equals(1.0)) {
//                        green = 1.0;
//                        colored = true;
//                    }
//                    if(dc.getBlue().equals(1.0)) {
//                        blue = 1.0;
//                        colored = true;
//                    }
//                }
//                if(!colored){
//                    otherList.add(dcpx);
//                } else {
//                    anormalizedImage.setColor(dcpx.getPixel().getX(),dcpx.getPixel().getY(),
//                            new DenormalizedColor(red,green,blue,dcpx.getColor().getAlpha()));
//                }
//        }
//
//        return anormalizedImage;
//    }

    @Override
    public String getDescription() {
        return "Histeresis Umbralization, min: " + Utils.roundToRearestFraction(min,0.01) +
                " max: " + Utils.roundToRearestFraction(max,0.01);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HisteresisUmbralizationTransformation that = (HisteresisUmbralizationTransformation) o;
        return Double.compare(that.min, min) == 0 &&
                Double.compare(that.max, max) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), min, max);
    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        new MultiChannelTernaryTransformation(min,max).transformDenormalized(denormalizedImage);
        return new BorderExpandUmbralizationTransformation().transformDenormalized(denormalizedImage);
    }
}
