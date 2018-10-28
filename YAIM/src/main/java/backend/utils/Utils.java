package backend.utils;


import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.Pixel;
import backend.image.AnormalizedImage;
import backend.image.AnormalizedImageImpl;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import javax.rmi.CORBA.Util;
import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public class Utils {
    public static AnormalizedImage getAnormalized(Image image){
        return new AnormalizedImageImpl(image);
    }

    public static int toInteger(double d) {
        return new Double(Math.floor(d)).intValue();
    }

    public static Pixel getPixelFromMouseEvent(MouseEvent mouseEvent) {
        int x = Utils.toInteger(mouseEvent.getX());
        int y = Utils.toInteger(mouseEvent.getY());
        return new Pixel(x, y);
    }

    public static Double getGaussianFilterWeight(Double standardDeviation, Integer x, Integer y) {
        Double mult = 1 / (2 * Math.PI * standardDeviation * standardDeviation);
        Double exponent = -(Math.pow(x, 2) + Math.pow(y, 2)) / (2 * Math.pow(standardDeviation, 2));
        return mult * Math.exp(exponent);
    }

    public static Double roundToRearestFraction(Double num, Double fraction) {
        return Math.round(num / fraction) * (fraction);
    }



    public static Double getMax(Double prevMax, DenormalizedColor color) {
        prevMax = color.getRed() > prevMax ? color.getRed() : prevMax;
        prevMax = color.getGreen() > prevMax ? color.getGreen() : prevMax;
        prevMax = color.getBlue() > prevMax ? color.getBlue() : prevMax;
        return prevMax;
    }

    public static Double getMin(Double prevMin, DenormalizedColor color) {
        prevMin = color.getRed() < prevMin ? color.getRed() : prevMin;
        prevMin = color.getGreen() < prevMin ? color.getGreen() : prevMin;
        prevMin = color.getBlue() < prevMin ? color.getBlue() : prevMin;
        return prevMin;
    }

    public static Double getMinRed(Double prevMinRed, DenormalizedColor color){
        return color.getRed() < prevMinRed ? color.getRed() : prevMinRed;
    }
    
    public static Double getMinBlue(Double prevMinBlue, DenormalizedColor color){
        return color.getBlue() < prevMinBlue ? color.getBlue() : prevMinBlue;
    }
    
    public static Double getMinGreen(Double prevMinGreen, DenormalizedColor color){
        return color.getGreen() < prevMinGreen ? color.getGreen() : prevMinGreen;
    }    
    
    public static Double getMaxRed(Double prevMaxRed, DenormalizedColor color){
        return color.getRed() > prevMaxRed ? color.getRed() : prevMaxRed;
    }
    
    public static Double getMaxBlue(Double prevMaxBlue, DenormalizedColor color){
        return color.getBlue() > prevMaxBlue ? color.getBlue() : prevMaxBlue;
    }
    
    public static Double getMaxGreen(Double prevMaxGreen, DenormalizedColor color){
        return color.getGreen() > prevMaxGreen ? color.getGreen() : prevMaxGreen;
    }

    public static String printStats(Collection<Double> values, String name){
        StringBuilder sb = new StringBuilder();
        if(values.size() == 0) {
            return "";
        }
        sb.append("\n").append(name).append(" (").append(values.size()).append(")");
        sb.append("\nmin:").append(values.stream().mapToDouble(Double::doubleValue).min())
                .append(" max:").append(values.stream().mapToDouble(Double::doubleValue).max())
                .append(" std:").append(Utils.getStandardDeviation(values));
        return sb.toString();
    }

    public static String printColorStats(Collection<DenormalizedColorPixel> colors, String name, Function<DenormalizedColorPixel,String> function){
        List<Double> red = new ArrayList<>();
        List<Double> green = new ArrayList<>();
        List<Double> blue = new ArrayList<>();
        Double minRed=Double.MAX_VALUE,maxRed=Double.MIN_VALUE,
                minGreen=Double.MAX_VALUE,maxGreen=Double.MIN_VALUE,
                minBlue=Double.MAX_VALUE,maxBlue=Double.MIN_VALUE;
        if(colors.isEmpty()) {
            return "";
        }
        for (DenormalizedColorPixel color : colors) {
            DenormalizedColor c = color.getColor();
            red.add(c.getRed());
            green.add(c.getGreen());
            blue.add(c.getBlue());
            minRed = getMinRed(minRed, c);
            minGreen = getMinGreen(minGreen, c);
            minBlue = getMinBlue(minBlue, c);
            maxBlue = getMaxBlue(maxBlue, c);
            maxRed = getMaxRed(maxRed, c);
            maxGreen = getMaxGreen(maxGreen,c);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(name).append(" (").append(red.size()).append(") f:");
        sb.append("\nRed:{min:").append(minRed).append(" max:").append(maxRed).append(" std:").append(Utils.getStandardDeviation(red));
        sb.append("\nGreen:{min:").append(minGreen).append(" max:").append(minGreen).append(" std:").append(Utils.getStandardDeviation(green));
        sb.append("\nBlue:{min:").append(minBlue).append(" max:").append(maxBlue).append(" std:").append(Utils.getStandardDeviation(blue));
        return sb.toString();
    }

    public static Double getStandardDeviation(Collection<Double> colors) {
        Double sum = colors.stream().mapToDouble(x -> x).sum();
        Double mean = sum / colors.size();

        Double standardDeviation = colors.stream()
                .mapToDouble(x -> Math.pow(x - mean, 2))
                .sum();

        return Math.sqrt(standardDeviation / colors.size());
    }

    public static int byteToInt(byte b){
        return 0xFF&b;
    }

    public static boolean fileIsRaw(File file) {
        return "RAW".equalsIgnoreCase(file.getName().split("\\.")[1]);
    }

	public static Double getInRange(Double value, Double min, Double max) {
        return value > max? max:(value < min?min:value);
	}

    public static boolean isInRange(Double value, Double min, Double max) {
        return value >= min && value <= max;
    }

    public static String printFilter(Double[][] filter){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filter.length; i++) {
            for (int j = 0; j < filter[0].length; j++) {
                sb.append(Utils.roundToRearestFraction(filter[i][j],0.001)).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

}
