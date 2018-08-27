package backend;


import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static int toInteger(double d){
        return new Double(Math.floor(d)).intValue();
    }

    public static Pixel getPixelFromMouseEvent(MouseEvent mouseEvent){
        int x = Utils.toInteger(mouseEvent.getX());
        int y = Utils.toInteger(mouseEvent.getY());
        return new Pixel(x,y);
    }

    public static Double getGaussianFilterWeight(Double standardDeviation, Integer x, Integer y){
        Double mult = 1/(Math.sqrt(2*Math.PI)*standardDeviation);
        Double exponent = - (Math.pow(x,2)+Math.pow(y,2))/Math.pow(standardDeviation,2);
        return mult*Math.pow(Math.E,exponent);
    }

    public static Double getStandardDeviation(List<Double> colors){
        Double sum = colors.stream().mapToDouble(x -> x).sum();
        Double mean = sum/colors.size();

        Double standardDeviation = colors.stream()
                .mapToDouble( x-> Math.pow(x-mean,2))
                .sum();

        return Math.sqrt(standardDeviation/colors.size());
    }
}
