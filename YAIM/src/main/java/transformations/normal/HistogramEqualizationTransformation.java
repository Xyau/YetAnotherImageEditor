package transformations.normal;

import backend.Histogram;
import backend.transformators.Transformation;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


public class HistogramEqualizationTransformation implements Transformation {

    @Override
    public WritableImage transform(WritableImage writableImage) {
        Histogram histogram =new Histogram(writableImage);

        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                double redAccum = 0.0, greenAccum = 0.0, blueAccum = 0.0;
                for (int k = 0; k < writableImage.getPixelReader().getColor(i,j).getRed() * 255; k++) {
                    redAccum += histogram.getHistogramRed()[k];
                }
                for (int k = 0; k < writableImage.getPixelReader().getColor(i,j).getBlue() * 255; k++) {
                    greenAccum += histogram.getHistogramGreen()[k];
                }
                for (int k = 0; k < writableImage.getPixelReader().getColor(i,j).getGreen() * 255; k++) {
                    blueAccum += histogram.getHistogramBlue()[k];
                }
                writableImage.getPixelWriter().setColor(i, j, new Color(redAccum, greenAccum, blueAccum, writableImage.getPixelReader().getColor(i,j).getOpacity()));
            }
        }
        return writableImage;
    }

    @Override
    public String getDescription() {
        return "Binary";
    }
}
