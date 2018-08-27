package transformations;

import backend.Histogram;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.image.WritableImage;

public class DrawHistogramTransformation implements Transformation {

    @Override
    public WritableImage transform(WritableImage writableImage) {
        Histogram histogram = new Histogram(writableImage);

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> chart = new BarChart<>(xAxis,yAxis);
//        final LineChart<String,Number> chart = new LineChart<>(xAxis,yAxis);
        chart.setTitle("Histogram");


        XYChart.Series redSeries = new XYChart.Series();
        redSeries.setName("R");

        XYChart.Series greenSeries = new XYChart.Series();
        greenSeries.setName("G");
        XYChart.Series blueSeries = new XYChart.Series();
        blueSeries.setName("B");
        for (int i = 0; i < histogram.getHistogramRed().length; i++) {
            redSeries.getData().add(new XYChart.Data(i+"", histogram.getHistogramRed()[i]));
            greenSeries.getData().add(new XYChart.Data(i+"", histogram.getHistogramGreen()[i]));
            blueSeries.getData().add(new XYChart.Data(i+"", histogram.getHistogramBlue()[i]));
        }

        chart.getData().addAll(redSeries, blueSeries, greenSeries);
        Scene scene  = new Scene(chart,writableImage.getWidth(),writableImage.getHeight());

        return scene.snapshot(writableImage);
//        return null;
    }

    @Override
    public String getDescription() {
        return "Returns the  image of the histogram";
    }
}
