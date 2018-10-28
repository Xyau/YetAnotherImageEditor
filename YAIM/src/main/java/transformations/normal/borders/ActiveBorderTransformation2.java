package transformations.normal.borders;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.combiners.Combiner;
import backend.combiners.MeanCombiner;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.ColorUtils;
import backend.utils.Utils;
import javafx.scene.image.WritableImage;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanTransformation;
import transformations.denormalized.filter.WindowOperator;
import transformations.helpers.ActiveBorderHelperTransformation;
import transformations.normal.filters.GaussianMeanFilterTransformation;
import transformations.normalizers.MultiChannelRangeNormalizer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static transformations.helpers.ActiveBorderHelperTransformation.paintLine;

public class ActiveBorderTransformation2 implements FullTransformation{

    private DenormalizedImage gamma;
    private DenormalizedColor backgroundAvgColor;
    private DenormalizedColor objectAvgColor;
    private Predicate<DenormalizedColor> twoAverage = x -> Math.log(
                    ColorUtils.getDifference(backgroundAvgColor,x)/
                    ColorUtils.getDifference(objectAvgColor,x)) > 0;

    private Predicate<DenormalizedColor> custom = x -> Math.log10(
                    1-(ColorUtils.getDifference(x,objectAvgColor)-0.5))>0;

    private Predicate<DenormalizedColor> simple = (c) -> (ColorUtils.getDifference(c,objectAvgColor)/3)<4.0;

    private Predicate<DenormalizedColor> usedCondition;
    private Set<DenormalizedColorPixel> internal;
    private Set<DenormalizedColorPixel> external;

    private Integer iterations;
    public ActiveBorderTransformation2(Set<DenormalizedColorPixel> internal, Set<DenormalizedColorPixel> external,
                                       DenormalizedImage gamma, DenormalizedColor objectAvgColor, DenormalizedColor backgroundAvgColor,
                                       Integer iterations){
        this.internal = internal;
        this.external = external;
        this.gamma = gamma;
        this.objectAvgColor = objectAvgColor;
        this.backgroundAvgColor = backgroundAvgColor;
        this.iterations = iterations;
        usedCondition = twoAverage;
    }

    public Set<DenormalizedColorPixel> getNeighborsWithGamma(Integer x, Integer y, DenormalizedColor gammaColor, DenormalizedImage image){
        Set<DenormalizedColorPixel> set = new HashSet<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if(x+i < image.getWidth() && x+i >= 0 && y+j < image.getHeight() && y+j >= 0){
                    if(gamma.getColorAt(x+i,y+j).equals(gammaColor) && (i != 0 || j != 0) && j*i == 0) {
                        set.add(new DenormalizedColorPixel(x+i,y+j,image.getColorAt(x+i,y+j)));
                    }
                }
            }
        }
        return set;
    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        Combiner meanCombiner = new MeanCombiner();

//        backgroundAvgColor = ActiveBorderHelperTransformation.getAverageColor(denormalizedImage,denormalizedImage.getWidth()-50,0,denormalizedImage.getWidth(),50);
//        paintLine(internal,denormalizedImage,DenormalizedColor.BLACK);
//        paintLine(external,denormalizedImage,DenormalizedColor.WHITE);
        updateColors(external,denormalizedImage);
        updateColors(internal,denormalizedImage);
        for (int i = 0; i < iterations; i++) {
            //Find external pixels that should be included
            Set<DenormalizedColorPixel> added = external.stream()
                    .filter(cpx -> usedCondition.test(cpx.getColor()))
                    .collect(Collectors.toSet());
            switchIn(added,gamma,denormalizedImage);
            if(added.size() != 0){
                System.out.println("external to internal:" +added.size() );
            }

            Set<DenormalizedColorPixel> ext = internal.stream().filter(cpx -> !usedCondition.test(cpx.getColor()))
                    .collect(Collectors.toSet());
            switchOut(ext,gamma,denormalizedImage);
            if(ext.size() != 0){
                System.out.println("internal to ext:" +ext.size());
            }

            Set<DenormalizedColorPixel> newInternal = external.stream()
                    .filter( cpx -> meanCombiner.combine(
                            WindowOperator.getNeighborPixels(gamma,cpx.getPixel().getX(), cpx.getPixel().getY(),3,3)
                            ,FiltersRepository.getGaussianMatrixWeight(1.0,1)).getRed() < 0)
                    .collect(Collectors.toSet());
            switchIn(newInternal,gamma,denormalizedImage);
            if(newInternal.size() != 0){
                System.out.println("external to internal gauss:" +newInternal.size());
            }


            Set<DenormalizedColorPixel> newExternal = internal.stream()
                    .filter( cpx -> meanCombiner.combine(
                            WindowOperator.getNeighborPixels(gamma,cpx.getPixel().getX(), cpx.getPixel().getY(),3,3)
                            ,FiltersRepository.getGaussianMatrixWeight(1.0,1)).getRed() > 0)
                    .collect(Collectors.toSet());
            switchOut(newExternal,gamma,denormalizedImage);
            if(newExternal.size() != 0){
                System.out.println("internal to ext gauus:" +newExternal.size());
            }
        }



        for (int i = 0; i < iterations; i++) {

        }
        paintLine(internal,denormalizedImage,DenormalizedColor.BLACK);
        paintLine(external,denormalizedImage,DenormalizedColor.WHITE);

        return denormalizedImage;
    }

    private void updateColors(Set<DenormalizedColorPixel> cpxs, DenormalizedImage image){
        Set<DenormalizedColorPixel> old = new HashSet<>();
        old.addAll(cpxs);
        cpxs.clear();
        old.forEach( cpx -> cpxs.add(
                new DenormalizedColorPixel(cpx.getPixel().getX(), cpx.getPixel().getY()
                        ,image.getColorAt(cpx.getPixel().getX(),cpx.getPixel().getY()))));
    }

    private void switchIn(Set<DenormalizedColorPixel> x, DenormalizedImage gamma, DenormalizedImage image) {
        external.removeAll(x);
        internal.addAll(x);
        setGamma(gamma, x, DenormalizedColor.INTERNAL);
        Set<DenormalizedColorPixel> newExternal = new HashSet<>();
        x.stream().forEach( cpx -> {
            newExternal.addAll(getNeighborsWithGamma(cpx.getPixel().getX(), cpx.getPixel().getY(),
                    DenormalizedColor.BACKGROUND, image));
        });
        external.addAll(newExternal);
        setGamma(gamma,newExternal,DenormalizedColor.EXTERNAL);

        Set<DenormalizedColorPixel> newObject = internal.stream().filter( cpx-> !anyNeighborHasColor(cpx.getPixel().getX(),cpx.getPixel().getY(),
                DenormalizedColor.EXTERNAL,image)).collect(Collectors.toSet());
        internal.removeAll(newObject);
        setGamma(gamma,newObject,DenormalizedColor.OBJECT);
    }

    private void switchOut(Set<DenormalizedColorPixel> x, DenormalizedImage gamma, DenormalizedImage image) {
        internal.removeAll(x);
        external.addAll(x);
        setGamma(gamma, x, DenormalizedColor.EXTERNAL);
        Set<DenormalizedColorPixel> newInternal = new HashSet<>();
        x.stream().forEach( cpx -> {
            newInternal.addAll(getNeighborsWithGamma(cpx.getPixel().getX(), cpx.getPixel().getY(),
                    DenormalizedColor.OBJECT, image));
        });
        internal.addAll(newInternal);
        setGamma(gamma,newInternal,DenormalizedColor.INTERNAL);

        Set<DenormalizedColorPixel> newBackground = external.stream().filter( cpx-> !anyNeighborHasColor(cpx.getPixel().getX(),cpx.getPixel().getY(),
                DenormalizedColor.INTERNAL,image)).collect(Collectors.toSet());
        external.removeAll(newBackground);
        setGamma(gamma,newBackground,DenormalizedColor.BACKGROUND);
    }



    private void setGamma(DenormalizedImage gamma, Set<DenormalizedColorPixel> colorPixels, DenormalizedColor denormalizedColor){
        colorPixels.stream().forEach( cpx -> gamma.setColor(cpx.getPixel().getX(), cpx.getPixel().getY(),
                denormalizedColor));
    }

    @Override
    public WritableImage transform(WritableImage writableImage){
        return FullTransformation.transform(writableImage,this::transformDenormalized,new MultiChannelRangeNormalizer()::normalize);
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
    public String getDescription() {
        return "Internal active borders";
    }
}
