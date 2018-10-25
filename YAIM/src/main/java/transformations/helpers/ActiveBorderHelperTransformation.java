package transformations.helpers;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.image.DenormalizedImage;
import backend.transformators.Transformation;
import backend.utils.ColorUtils;
import javafx.scene.image.WritableImage;
import transformations.normal.borders.ActiveBorderTransformation;
import transformations.normal.common.PixelByPixelTransformation;
import transformations.normal.image.SetImageTransformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ActiveBorderHelperTransformation implements Transformation {

    private List<WritableImage> images;
    private Integer frameNumber;
    private Integer iterations;
    private Set<DenormalizedColorPixel> external;
    private Set<DenormalizedColorPixel> internal;
    private DenormalizedImage gamma;
    private DenormalizedColor avgColor;

    public ActiveBorderHelperTransformation(List<WritableImage> images, Integer frameNumber, Integer iterations,
                                            Set<DenormalizedColorPixel> internal, Set<DenormalizedColorPixel> external,
                                            DenormalizedImage gamma, DenormalizedColor avgColor) {
        this.images = images;
        this.frameNumber = frameNumber;
        this.iterations = iterations;
        this.internal = internal;
        this.external = external;
        this.avgColor = avgColor;
        this.gamma = gamma;
    }

    public static void setGamma(Integer x1, Integer y1, Integer x2, Integer y2,
                                DenormalizedImage gamma) {
        new PixelByPixelTransformation(c -> DenormalizedColor.BACKGROUND).transformDenormalized(gamma);
        paintSquare(x1-1,y1-1,x2+1,y2+1,gamma,false,DenormalizedColor.EXTERNAL);
        paintSquare(x1,y1,x2,y2,gamma,false,DenormalizedColor.INTERNAL);
        paintSquare(x1+1,y1+1,x2-1,y2-1,gamma,true,DenormalizedColor.OBJECT);
    }

    public static void paintLine(Collection<DenormalizedColorPixel> cpxs, DenormalizedImage denormalizedImage, DenormalizedColor color){
        cpxs.stream().forEach( cpx ->
                denormalizedImage.setColor(cpx.getPixel().getX(),cpx.getPixel().getY(),color)
        );
    }
    @Override
    public WritableImage transform(WritableImage writableImage) {
        new SetImageTransformation(images.get(frameNumber)).transform(writableImage);

        new ActiveBorderTransformation(internal,external, gamma,avgColor, iterations).transform(writableImage);
        return writableImage;
    }

    @Override
    public String getDescription() {
        return "Active Border";
    }

    public static List<DenormalizedColorPixel> getIn(Integer x1, Integer y1, Integer x2, Integer y2, DenormalizedImage image){
        List<DenormalizedColorPixel> bin = new ArrayList<>();
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                if(i == x1 || i == (x2-1) || j == y1 || j == (y2-1)){
                    bin.add(new DenormalizedColorPixel(i,j,image.getColorAt(i,j)));
                }
            }
        }
        return bin;
    }

    public static List<DenormalizedColorPixel> paintSquare(Integer x1, Integer y1, Integer x2, Integer y2, DenormalizedImage image, Boolean filled, DenormalizedColor color){
        List<DenormalizedColorPixel> bin = new ArrayList<>();
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                if(i == x1 || i == (x2-1) || j == y1 || j == (y2-1) || filled) {
                    image.setColor(i,j,color);
                }
            }
        }
        return bin;
    }

    public static List<DenormalizedColorPixel> getOut(Integer x1, Integer y1, Integer x2, Integer y2, DenormalizedImage image){
        return getIn(x1-1,y1-1,x2+1,y2+1,image);
    }

    public static DenormalizedColor getAverageColor(DenormalizedImage image, Integer x1, Integer y1, Integer x2, Integer y2){
        Integer count=0;
        DenormalizedColor sumColor= DenormalizedColor.BLACK;
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                sumColor = ColorUtils.addColors(sumColor,image.getColorAt(i,j));
                count++;
            }
        }
        return ColorUtils.multiplyColors(sumColor,1/(count*1.0));
    }


}
