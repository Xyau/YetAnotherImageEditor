package backend;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import org.omg.CORBA.MARSHAL;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class Utils {
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

    private static Cache<Pair<Double, Integer>, Double[][]> gaussianCache = CacheBuilder.newBuilder()
            .maximumSize(30)
            .build();

    public static Double[][] getGaussianMatrixWeight(Double std, Integer filterSize) {
        Double[][] weights = null;
        Double roundedStd = roundToRearestFraction(std, 0.05);
        System.out.println("getting gauss cache");
        try {
            weights = gaussianCache.get(new Pair<>(std, filterSize), () -> {
                Double[][] wMatrix = new Double[2 * filterSize + 1][2 * filterSize + 1];
                for (int i = -filterSize; i <= filterSize; i++) {
                    for (int j = -filterSize; j <= filterSize; j++) {
                        wMatrix[i + filterSize][j + filterSize] = getGaussianFilterWeight(roundedStd, i, j);
                    }
                }
                return wMatrix;
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("getted gauss cache");
        if (weights == null) {
            throw new IllegalStateException("Gaussian Cache failed!");
        }
        return weights;
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


    public static Double getStandardDeviation(List<Double> colors) {
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
}
