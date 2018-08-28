package backend;


import javafx.scene.paint.Color;

import java.util.List;

public class ColorUtils {
    public static Color addColors(Color c1, Color c2){
        Double red = (c1.getRed() + c2.getRed())/2;
        Double blue = (c1.getBlue() + c2.getBlue())/2;
        Double green = (c1.getGreen() + c2.getGreen())/2;
        Double alpha = (c1.getOpacity() + c2.getOpacity())/2;
        return new Color(red,blue,green,alpha);
    }

    public static Color multiplyColors(Color c1, Color c2){
        Double red = (c1.getRed() * c2.getRed());
        Double blue = (c1.getBlue() * c2.getBlue());
        Double green = (c1.getGreen() * c2.getGreen());
        Double alpha = (c1.getOpacity() * c2.getOpacity());
        return new Color(red,blue,green,alpha);
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
}
