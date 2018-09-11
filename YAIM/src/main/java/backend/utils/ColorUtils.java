package backend.utils;


import backend.DenormalizedColor;
import com.sun.javafx.util.Utils;
import javafx.scene.paint.Color;

public class ColorUtils {
    public static DenormalizedColor addColors(DenormalizedColor c1, DenormalizedColor c2){
        Double red = (c1.getRed() + c2.getRed());
        Double blue = (c1.getBlue() + c2.getBlue());
        Double green = (c1.getGreen() + c2.getGreen());
        Double alpha = (c1.getAlpha());
        return new DenormalizedColor(red,blue,green,alpha);
    }

    public static DenormalizedColor multiplyColors(DenormalizedColor c1, DenormalizedColor c2){
        Double red = (c1.getRed() * c2.getRed());
        Double blue = (c1.getBlue() * c2.getBlue());
        Double green = (c1.getGreen() * c2.getGreen());
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

    public static DenormalizedColor denormalizeColor(Color color){
        return new DenormalizedColor(color.getRed(),color.getGreen(),color.getBlue(),color.getOpacity());
    }

    public static Double getBrightness(DenormalizedColor color){
        return Utils.RGBtoHSB(color.getRed(), color.getRed(), color.getBlue())[2];
    }
}
