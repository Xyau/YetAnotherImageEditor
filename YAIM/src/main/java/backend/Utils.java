package backend;


import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DeflaterOutputStream;

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
        Double mult = 1/(2*Math.PI*standardDeviation*standardDeviation);
        Double exponent = - (Math.pow(x,2)+Math.pow(y,2))/(2*Math.pow(standardDeviation,2));
        return mult*Math.exp(exponent);
    }

    public static Integer[][] getGaussianMatrixWeight(Double std, Integer filterSize){
        Integer[][] wMatrix = new Integer[2*filterSize+1][2*filterSize+1];
        Double mult = null;
        for (int i = -filterSize; i <= filterSize; i++) {
            for (int j = -filterSize; j <= filterSize; j++) {
                Double temp = getGaussianFilterWeight(std,i,j);
                if(mult == null){
                    mult = 1/temp;
                }
                wMatrix[i+filterSize][j+filterSize]= new Double(mult*temp).intValue();
            }
        }
        return wMatrix;
    }

    public static Double getMax(Double prevMax, DenormalizedColor color){
        prevMax = color.getRed()>prevMax?color.getRed():prevMax;
        prevMax = color.getGreen()>prevMax?color.getGreen():prevMax;
        prevMax = color.getBlue()>prevMax?color.getBlue():prevMax;
        return prevMax;
    }

    public static Double getMin(Double prevMin, DenormalizedColor color){
        prevMin = color.getRed()<prevMin?color.getRed():prevMin;
        prevMin = color.getGreen()<prevMin?color.getGreen():prevMin;
        prevMin = color.getBlue()<prevMin?color.getBlue():prevMin;
        return prevMin;
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
