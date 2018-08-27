package repositories;

import backend.ImageUtils;
import frontend.ImageOperationsControl;
import frontend.SliderControl;
import frontend.TextAreaControlPane;
import frontend.TransformationManagerView;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import transformations.DrawHistogramTransformation;
import transformations.DynamicRangeCompressionTransformation;
import transformations.MedianFilterTransformation;
import transformations.*;

import java.io.File;

public class MenusRepository {

    public static Menu getFileMenu(Stage stage, TransformationManagerView transformationManagerView){
        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(getSaveMenuItem(stage,transformationManagerView),
                getLoadMenuItem(stage,transformationManagerView));
        return fileMenu;
    }

    private static MenuItem getSaveMenuItem(final Stage stage, TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Save");
        item.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.setSelectedExtensionFilter(ThingsRepository.EXTENSION_FILTER);
            File file = fileChooser.showSaveDialog(stage);
            ImageUtils.writeImage(SwingFXUtils.fromFXImage(transformationManagerView.getImage(),null),file);
        });
        return item;
    }

    private static MenuItem getLoadMenuItem(final Stage stage, TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Load");
        item.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(stage);
            WritableImage image = ImageUtils.readImage(file);

            transformationManagerView.setInitialImage(image);
        });
        return item;
    }

    public static Menu getImageMenu(Scene scene, TransformationManagerView transformationManagerView){
        Menu imageMenu = new Menu("Image");
        MenuItem imageOperations = new MenuItem("Image Operations");
        ImageOperationsControl imageOperationsControl = new ImageOperationsControl(scene, transformationManagerView);

        imageOperations.setOnAction( event -> {
            StagesRepository.getStage("Image Operations",imageOperationsControl).show();
        });

        MenuItem histogram = new MenuItem("Histogram");
        histogram.setOnAction( event -> {
            transformationManagerView.preview(new DrawHistogramTransformation());
        });

        imageMenu.getItems().add(histogram);
        imageMenu.getItems().add(imageOperations);
        return imageMenu;
    }

    public static Menu getFilterMenu( TransformationManagerView transformationManagerView){
        Menu fileMenu = new Menu("Filter");
        fileMenu.getItems().addAll(getMedianFilterMenuItem(transformationManagerView),
                                    getMeanFilterMenuItem(transformationManagerView),
                                    getWeighedMedianFilterMenuItem(transformationManagerView),
                                    getGaussianFilterMenuItem(transformationManagerView),
                                    getDynamicRangeCompressionMenuItem(transformationManagerView));
        return fileMenu;
    }

    private static MenuItem getMedianFilterMenuItem(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Median Filter");

        GridPane gridPane = new GridPane();
        Button apply = new Button("Apply");
        TextAreaControlPane result = new TextAreaControlPane("Filter Size",2);
        SliderControl sliderControl = new SliderControl("Filter Size",1.0,5.0,1.0,(x, y)->{
            result.setText(String.valueOf(y.intValue()));
            transformationManagerView.preview(new MedianFilterTransformation(y.intValue()));
        });
        apply.setOnMouseClicked( event -> {
            transformationManagerView.addTransformation(new MedianFilterTransformation(sliderControl.getSelectedValue().get().intValue()));
        });
        gridPane.add(sliderControl,0,0);
        gridPane.add(apply,0,1);
        gridPane.add(result,1,1 );
        item.setOnAction(event -> {
            StagesRepository.getStage("Median Filter", gridPane).show();
        });
        return item;
    }

    private static MenuItem getWeighedMedianFilterMenuItem(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Weighed Median Filter");

        item.setOnAction(event -> {
            transformationManagerView.addTransformation(new WeighedMedianFilterTransformation());
        });
        return item;
    }

    private static MenuItem getMeanFilterMenuItem(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Mean Filter");

        GridPane gridPane = new GridPane();
        Button apply = new Button("Apply");
        TextAreaControlPane result = new TextAreaControlPane("Filter Size",2);
        SliderControl sliderControl = new SliderControl("Filter Size",1.0,5.0,1.0,(x, y)->{
            result.setText(String.valueOf(y.intValue()));
            transformationManagerView.preview(new MeanFilterTransformation(y.intValue()));
        });
        apply.setOnMouseClicked( event -> {
            transformationManagerView.addTransformation(new MeanFilterTransformation(sliderControl.getSelectedValue().get().intValue()));
        });
        gridPane.add(sliderControl,0,0);
        gridPane.add(apply,0,1);
        gridPane.add(result,1,1 );
        item.setOnAction(event -> {
            StagesRepository.getStage("Mean Filter", gridPane).show();
        });
        return item;
    }

    private static MenuItem getGaussianFilterMenuItem(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Gaussian Filter");

        GridPane gridPane = new GridPane();
        Button apply = new Button("Apply");
        TextAreaControlPane result = new TextAreaControlPane("Filter Size",2);
        SliderControl sliderControl = new SliderControl("Filter Size",1.0,5.0,1.0,(x, y)->{
            result.setText(String.valueOf(y.intValue()));
            transformationManagerView.preview(new MeanFilterTransformation(y.intValue()));
        });
        apply.setOnMouseClicked( event -> {
            transformationManagerView.addTransformation(new MeanFilterTransformation(sliderControl.getSelectedValue().get().intValue()));
        });
        gridPane.add(sliderControl,0,0);
        gridPane.add(apply,0,1);
        gridPane.add(result,1,1 );
        item.setOnAction(event -> {
            StagesRepository.getStage("Gaussian Filter", gridPane).show();
        });
        return item;
    }

    public static Menu getNoiseMenu(TransformationManagerView transformationManagerView){
        Menu fileMenu = new Menu("Noise");
        fileMenu.getItems().addAll(getSaltAndPepperNoiseTransformation(transformationManagerView));
        return fileMenu;
    }

    private static MenuItem getSaltAndPepperNoiseTransformation(TransformationManagerView transformationManagerView) {
        MenuItem menuItem = new MenuItem("Salt and pepper");

        menuItem.setOnAction( event -> {
            transformationManagerView.addTransformation(new SaltAndPeperTransformation(0L,0.2,0.2));
        });

        return menuItem;
    }
    private static MenuItem getDynamicRangeCompressionMenuItem(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Dynamic Range Compression");
        item.setOnAction(event -> {
            transformationManagerView.addTransformation(new DynamicRangeCompressionTransformation());
        });
        return item;
    }
}
