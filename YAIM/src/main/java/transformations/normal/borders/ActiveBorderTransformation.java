package transformations.normal.borders;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.ColorUtils;
import transformations.helpers.ActiveBorderHelperTransformation;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static transformations.helpers.ActiveBorderHelperTransformation.paintLine;

public class ActiveBorderTransformation implements FullTransformation {

    private DenormalizedImage gamma;
    private DenormalizedColor backgroundAvgColor;
    private DenormalizedColor objectAvgColor;
    private Function<DenormalizedColor, Double> f = x -> Math.log(
                    ColorUtils.getDifference(backgroundAvgColor,x)/
                    ColorUtils.getDifference(objectAvgColor,x));

    private Function<DenormalizedColor, Double> fsimple = x -> Math.log10(
                    1-(ColorUtils.getDifference(x,objectAvgColor)-0.5));

    private Function<DenormalizedColor, Double> fsupersimple = x ->
            (ColorUtils.getDifference(x,objectAvgColor)/3);

    private Set<DenormalizedColorPixel> internal;
    private Set<DenormalizedColorPixel> external;

    private Integer iterations;
    public ActiveBorderTransformation(Set<DenormalizedColorPixel> internal, Set<DenormalizedColorPixel> external,
                                      DenormalizedImage gamma, DenormalizedColor objectAvgColor, Integer iterations){
        this.internal = internal;
        this.external = external;
        this.gamma = gamma;
        this.objectAvgColor = objectAvgColor;
        this.iterations = iterations;
        fsimple = fsupersimple;
    }

    public Set<DenormalizedColorPixel> getNeighborsWithGamma(Integer x, Integer y, DenormalizedColor gammaColor, DenormalizedImage image){
        Set<DenormalizedColorPixel> set = new HashSet<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if(x+i < image.getWidth() && x+i >= 0 && y+j < image.getHeight() && y+j >= 0){
                    if(gamma.getColorAt(x+i,y+j).equals(gammaColor) && (i != 0 || j != 0) && j*i == 0) {
                        set.add(new DenormalizedColorPixel(x+i,y+i,image.getColorAt(x+i,y+i)));
                    }
                }
            }
        }
        return set;
    }

    public Boolean anyNeighborHasColor(Integer x, Integer y, DenormalizedColor gammaColor, DenormalizedImage image){
        Set<DenormalizedColorPixel> set = new HashSet<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if(x+i < image.getWidth() && x+i >= 0 && y+j < image.getHeight() && y+j >= 0){
                    if(gamma.getColorAt(x+i,y+j).equals(gammaColor) && (i != 0 || j != 0) && j*i == 0){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {

        backgroundAvgColor = ActiveBorderHelperTransformation.getAverageColor(denormalizedImage,denormalizedImage.getWidth()-50,0,denormalizedImage.getWidth(),50);

//        paintLine(internal,denormalizedImage,DenormalizedColor.BLACK);
//        paintLine(external,denormalizedImage,DenormalizedColor.WHITE);
        for (int i = 0; i < iterations; i++) {
            //Find external pixels that should be included
            Set<DenormalizedColorPixel> added = external.stream().filter(cpx -> fsimple.apply(cpx.getColor())>0)
                    .collect(Collectors.toSet());
            List<Double> nums = external.stream().map(cpx -> fsimple.apply(cpx.getColor()))
                    .collect(Collectors.toList());
            nums.sort(Double::compareTo);
            System.out.println(nums.stream().max(Double::compareTo));
            System.out.println(nums.stream().min(Double::compareTo));
            System.out.println(nums);

            external.removeAll(added); //Take them out of externals+
            internal.addAll(added); //Add them to internals
            setGamma(gamma,added,DenormalizedColor.INTERNAL);

            Set<DenormalizedColorPixel> newExternal = new HashSet<>();
            //Find the pixels that now border internal that were part of the background but now are external
            for(DenormalizedColorPixel cpx:added){
                newExternal.addAll(getNeighborsWithGamma(cpx.getPixel().getX(), cpx.getPixel().getY(),
                        DenormalizedColor.BACKGROUND, denormalizedImage));
            }

            setGamma(gamma,newExternal,DenormalizedColor.EXTERNAL);
            external.addAll(newExternal);

            //Find the pixels that used to border internal, but now dont, and are part of the object
            Set<DenormalizedColorPixel> newObjectPixels = internal.stream()
                    .filter(cpx-> !anyNeighborHasColor(cpx.getPixel().getX(), cpx.getPixel().getY(),
                            DenormalizedColor.EXTERNAL, denormalizedImage))
                            .collect(Collectors.toSet());
            internal.removeAll(newObjectPixels);
            setGamma(gamma,newObjectPixels,DenormalizedColor.OBJECT);

            //Find internal pixels that should be removed form internal to external
            Set<DenormalizedColorPixel> extraInternals = internal.stream().filter(cpx -> fsimple.apply(cpx.getColor())<0)
                    .collect(Collectors.toSet());
            internal.removeAll(extraInternals);
            external.addAll(extraInternals);
            setGamma(gamma,extraInternals,DenormalizedColor.EXTERNAL);

            //Find the pixels that used to border internal, but now dont, and are part of the object
            Set<DenormalizedColorPixel> newInternalPixels = extraInternals.stream()
                    .filter(cpx-> !anyNeighborHasColor(cpx.getPixel().getX(), cpx.getPixel().getY(),
                            DenormalizedColor.OBJECT, denormalizedImage))
                    .collect(Collectors.toSet());
            internal.addAll(newInternalPixels);
            setGamma(gamma,newInternalPixels,DenormalizedColor.INTERNAL);

            Set<DenormalizedColorPixel> newBackgroundPixels = external.stream()
                    .filter(cpx-> !anyNeighborHasColor(cpx.getPixel().getX(), cpx.getPixel().getY(),
                    DenormalizedColor.INTERNAL, denormalizedImage))
                    .collect(Collectors.toSet());
            external.removeAll(newBackgroundPixels);
            setGamma(gamma,newBackgroundPixels,DenormalizedColor.BACKGROUND);
        }

        paintLine(internal,denormalizedImage,DenormalizedColor.BLACK);
        paintLine(external,denormalizedImage,DenormalizedColor.WHITE);

        return denormalizedImage;
    }

    private void setGamma(DenormalizedImage gamma, Set<DenormalizedColorPixel> colorPixels, DenormalizedColor denormalizedColor){
        colorPixels.stream().forEach( cpx -> gamma.setColor(cpx.getPixel().getX(), cpx.getPixel().getY(),
                denormalizedColor));
    }

    @Override
    public String getDescription() {
        return "Internal active borders";
    }
}
