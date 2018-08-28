package backend;

import javafx.scene.image.WritableImage;

public class Histogram {
    private double[] histogramRed = new double[256];
    private double[] histogramGreen = new double[256];
    private double[] histogramBlue = new double[256];

    public Histogram(WritableImage writableImage) {
        Long pixelCount = new Double(writableImage.getHeight()).longValue() * new Double(writableImage.getWidth()).longValue();
        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                histogramRed[new Double(writableImage.getPixelReader().getColor(i,j).getRed() * 255).intValue()] += 1;
                histogramGreen[new Double(writableImage.getPixelReader().getColor(i,j).getGreen() * 255).intValue()] += 1;
                histogramBlue[new Double(writableImage.getPixelReader().getColor(i,j).getBlue() * 255).intValue()] += 1;
            }
        }

        for (int i = 0; i < histogramBlue.length; i++) {
            histogramRed[i] /= pixelCount;
            histogramGreen[i] /= pixelCount;
            histogramBlue[i] /= pixelCount;
        }
    }

    public double[] getHistogramRed() {
        return histogramRed;
    }

    public double[] getHistogramGreen() {
        return histogramGreen;
    }

    public double[] getHistogramBlue() {
        return histogramBlue;
    }
}
