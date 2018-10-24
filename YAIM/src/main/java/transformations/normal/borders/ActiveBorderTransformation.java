package transformations.normal.borders;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static backend.utils.ColorUtils.denormalizeColor;
import static backend.utils.ColorUtils.substractColors;

public class ActiveBorderTransformation implements FullTransformation {

    private DenormalizedColor backgroundAvgColor;
    private DenormalizedColor objectAvgColor;
    private Function<DenormalizedColor, Double> f = x -> Math.log(
                    ColorUtils.getDifference(backgroundAvgColor,x)/
                    ColorUtils.getDifference(objectAvgColor,x));

    private List<DenormalizedColorPixel> binternal = new ArrayList<>();
    private List<DenormalizedColorPixel> bexternal = new ArrayList<>();
    private Integer x1, y1, x2, y2;

    public ActiveBorderTransformation(Integer x1, Integer y1, Integer x2, Integer y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        objectAvgColor = getAverageColor(denormalizedImage,x1,y1,x2,y2);
        binternal.stream().forEach( cpx ->
                denormalizedImage.setColor(cpx.getPixel().getX(),cpx.getPixel().getY(),DenormalizedColor.BLACK)
        );
        return denormalizedImage;
    }

    public DenormalizedColor getAverageColor(DenormalizedImage image, Integer x1, Integer y1, Integer x2, Integer y2){
        Integer count=0;
        DenormalizedColor sumColor= DenormalizedColor.BLACK;
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                if(i == x1 || i == (x2-1) || j == y1 || j == (y2-1)){
                    binternal.add(new DenormalizedColorPixel(i,j,image.getColorAt(i,j)));
                }
                sumColor = ColorUtils.addColors(sumColor,image.getColorAt(i,j));
                count++;
            }
        }
        return ColorUtils.multiplyColors(sumColor,1/(count*1.0));
    }

    @Override
    public String getDescription() {
        return null;
    }
}
