package backend.utils;


import backend.DenormalizedColor;
import backend.image.DenormalizedImage;
import com.sun.javafx.util.Utils;
import javafx.scene.paint.Color;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class ColorUtils {
    public static DenormalizedColor addColors(DenormalizedColor c1, DenormalizedColor... cs){
        Double red = c1.getRed();
        Double blue = c1.getBlue();
        Double green = c1.getGreen();
        Double alpha = (c1.getAlpha());

        for (DenormalizedColor color : cs){
            red += color.getRed();
            blue += color.getBlue();
            green += color.getGreen();
        }

        return new DenormalizedColor(red,blue,green,alpha);
    }

    public static DenormalizedColor addClampColors(DenormalizedColor c1, DenormalizedColor... cs){
        Double red = c1.getRed();
        Double blue = c1.getBlue();
        Double green = c1.getGreen();
        Double alpha = (c1.getAlpha());

        for (DenormalizedColor color : cs){
            red += color.getRed();
            blue += color.getBlue();
            green += color.getGreen();

            red = red > 1.0 ? 1.0 : red;
            blue = blue > 1.0 ? 1.0 : blue;
            green = green > 1.0 ? 1.0 : green;
        }

        return new DenormalizedColor(red,blue,green,alpha);
    }

    public static DenormalizedColor multiplyColors(DenormalizedColor c1, DenormalizedColor c2){
        Double red = (c1.getRed() * c2.getRed());
        Double blue = (c1.getBlue() * c2.getBlue());
        Double green = (c1.getGreen() * c2.getGreen());
        Double alpha = (c1.getAlpha());
        return new DenormalizedColor(red,blue,green,alpha);
    }

    public static DenormalizedColor squareColor(DenormalizedColor c){
        return multiplyColors(c,c);
    }

    public static DenormalizedColor multiplyColors(DenormalizedColor c1, Double c){
        Double red = (c1.getRed() * c);
        Double blue = (c1.getBlue() * c);
        Double green = (c1.getGreen() * c);
        Double alpha = (c1.getAlpha());
        return new DenormalizedColor(red,blue,green,alpha);
    }

    public static DenormalizedColor averagingColors(DenormalizedColor c1, DenormalizedColor c2){
        Double red = (c1.getRed() + c2.getRed())/2;
        Double blue = (c1.getBlue() + c2.getBlue())/2;
        Double green = (c1.getGreen() + c2.getGreen())/2;
        Double alpha = (c1.getAlpha());
        return new DenormalizedColor(red,blue,green,alpha);
    }

    public static DenormalizedColor distanceColors(DenormalizedColor c1, DenormalizedColor c2){
        Double red = Math.sqrt(c1.getRed()*c1.getRed() + c2.getRed()*c2.getRed());
        Double green = Math.sqrt(c1.getGreen()*c1.getGreen() + c2.getGreen()*c2.getGreen());
        Double blue = Math.sqrt(c1.getBlue()*c1.getBlue() + c2.getBlue()*c2.getBlue());
        Double alpha = (c1.getAlpha());
        return new DenormalizedColor(red,blue,green,alpha);
    }

    public static DenormalizedColor substractColors(DenormalizedColor c1, DenormalizedColor c2){
        Double red = (c1.getRed() - c2.getRed());
        Double blue = (c1.getBlue() - c2.getBlue());
        Double green = (c1.getGreen() - c2.getGreen());
        Double alpha = (c1.getAlpha());
        return new DenormalizedColor(red,blue,green,alpha);
    }

    public static DenormalizedColor modulusColors(DenormalizedColor c1, DenormalizedColor c2){
        Double red = Math.abs(c1.getRed() - c2.getRed());
        Double blue = Math.abs(c1.getBlue() - c2.getBlue());
        Double green = Math.abs(c1.getGreen() - c2.getGreen());
        Double alpha = (c1.getAlpha());
        return new DenormalizedColor(red,blue,green,alpha);
    }

    public static DenormalizedColor transform(DenormalizedColor color, Function<Double,Double> transformation){
        return new DenormalizedColor(transformation.apply(color.getRed()),transformation.apply(color.getBlue()),
                transformation.apply(color.getGreen()),color.getAlpha());
    }

    public static DenormalizedColor combineColors(DenormalizedColor c1, DenormalizedColor c2,
                                                  BiFunction<Double, Double, Double> transformation){
        Double red = transformation.apply(c1.getRed(), c2.getRed());
        Double green = transformation.apply(c1.getGreen(), c2.getGreen());
        Double blue = transformation.apply(c1.getBlue(), c2.getBlue());
        return new DenormalizedColor(red,blue,green,c1.getAlpha());
    }

    public static DenormalizedColor combineColors(DenormalizedColor c1, DenormalizedColor c2, DenormalizedColor c3,
                                                  TriFunction<Double, Double> transformation){
        Double red = transformation.compute(c1.getRed(), c2.getRed(),c3.getRed());
        Double green = transformation.compute(c1.getGreen(), c2.getGreen(), c3.getGreen());
        Double blue = transformation.compute(c1.getBlue(), c2.getBlue(), c3.getBlue());
        return new DenormalizedColor(red,blue,green,c1.getAlpha());
    }

    public static Double normalize(Double x, Double min, Double max){
        return (x-min)/(max-min);
    }

    public static Color normalize(DenormalizedColor color, Double min, Double max){
        Double red = normalize(color.getRed(),min,max);
        Double blue = normalize(color.getRed(),min,max);
        Double green = normalize(color.getRed(),min,max);
        Double alpha = normalize(color.getRed(),min,max);
        return new Color(red,blue,green,alpha);
    }

    public static boolean isGreater(DenormalizedColor c1, DenormalizedColor c2) {
        return c1.getRed() >= c2.getRed() ||
                c1.getGreen() >= c2.getGreen() ||
                c1.getBlue() >= c2.getBlue();
    }

    public static DenormalizedColor denormalizeColor(Color color){
        return new DenormalizedColor(color.getRed(),color.getGreen(),color.getBlue(),color.getOpacity());
    }

    public static Double getBrightness(DenormalizedColor color){
        return Utils.RGBtoHSB(color.getRed(), color.getRed(), color.getBlue())[2];
    }

    public static Double getModulus(DenormalizedColor color){
        return Math.sqrt(Math.pow(color.getRed(),2)+Math.pow(color.getGreen(),2)+Math.pow(color.getBlue(),2));
    }

    public static Double getDifference(DenormalizedColor color1, DenormalizedColor color2){
        return getModulus(modulusColors(color1,color2));
    }

    public static DenormalizedColor getGreyscale(DenormalizedColor color){
        double gray = 0.21 * color.getRed() + 0.71 * color.getGreen() + 0.07 * color.getBlue();
        return new DenormalizedColor(gray,gray,gray,color.getAlpha());
    }

    public static DenormalizedColor getBrighter(DenormalizedColor c){
        return new DenormalizedColor(c.getRed()+0.1,c.getGreen()+0.1,c.getBlue()+0.1,c.getAlpha());
    }

    public static DenormalizedColor getMax(DenormalizedColor c1, DenormalizedColor c2){
        return combineColors(c1,c2, Math::max);
    }

    public static DenormalizedColor highlightRed(DenormalizedColor c1, DenormalizedColor c2){
        return c1.equals(DenormalizedColor.RED) || c2.equals(DenormalizedColor.RED)? DenormalizedColor.RED : c1;
    }

    public static DenormalizedColor negative(DenormalizedColor c1){
        return new DenormalizedColor(1-c1.getRed(),1-c1.getGreen(),1-c1.getBlue(), c1.getAlpha());
    }

    public static DenormalizedColor getDarker(DenormalizedColor c){
        return new DenormalizedColor(c.getRed()-0.1,c.getGreen()-0.1,c.getBlue()-0.1,c.getAlpha());
    }

    public static Boolean anyMatchChannel(DenormalizedColor c, Predicate<Double> predicate){
        return predicate.test(c.getRed()) || predicate.test(c.getGreen()) || predicate.test(c.getBlue());
    }
}
