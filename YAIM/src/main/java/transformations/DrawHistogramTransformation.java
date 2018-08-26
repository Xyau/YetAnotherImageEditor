package transformations;

import backend.ColorUtils;
import backend.ImageUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class DrawHistogramTransformation implements Transformation {


    final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";

    @Override
    public WritableImage transform(WritableImage writableImage) {
        double[] histogram = ImageUtils.getHistogram(writableImage);

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<>(xAxis,yAxis);
        bc.setTitle("Histogram");


        XYChart.Series series1 = new XYChart.Series();
        for (int i = 0; i < histogram.length; i++) {
            series1.getData().add(new XYChart.Data(i+"", histogram[i]));
        }




//        final CategoryAxis xAxis = new CategoryAxis();
//        final NumberAxis yAxis = new NumberAxis();
//        final BarChart<String,Number> bc = new BarChart<>(xAxis,yAxis);
//        bc.setTitle("Country Summary");
//        xAxis.setLabel("Country");
//        yAxis.setLabel("Value");
//
//        XYChart.Series series1 = new XYChart.Series();
//        series1.setName("2003");
//        series1.getData().add(new XYChart.Data(austria, 25601.34));
//        series1.getData().add(new XYChart.Data(brazil, 20148.82));
//        series1.getData().add(new XYChart.Data(france, 10000));
//        series1.getData().add(new XYChart.Data(italy, 35407.15));
//        series1.getData().add(new XYChart.Data(usa, 12000));
//
//        XYChart.Series series2 = new XYChart.Series();
//        series2.setName("2004");
//        series2.getData().add(new XYChart.Data(austria, 57401.85));
//        series2.getData().add(new XYChart.Data(brazil, 41941.19));
//        series2.getData().add(new XYChart.Data(france, 45263.37));
//        series2.getData().add(new XYChart.Data(italy, 117320.16));
//        series2.getData().add(new XYChart.Data(usa, 14845.27));
//
//        XYChart.Series series3 = new XYChart.Series();
//        series3.setName("2005");
//        series3.getData().add(new XYChart.Data(austria, 45000.65));
//        series3.getData().add(new XYChart.Data(brazil, 44835.76));
//        series3.getData().add(new XYChart.Data(france, 18722.18));
//        series3.getData().add(new XYChart.Data(italy, 17557.31));
//        series3.getData().add(new XYChart.Data(usa, 92633.68));

        bc.getData().addAll(series1);
        Scene scene  = new Scene(bc,writableImage.getWidth(),writableImage.getHeight());

        return scene.snapshot(writableImage);
//        return null;
    }

    @Override
    public String getDescription() {
        return "Returns the  image of the histogram";
    }
}
