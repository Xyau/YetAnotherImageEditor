package repositories;

import backend.ImageUtils;
import backend.SyntheticGenerator;
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
                getSavePreviewMenuItem(stage, transformationManagerView),
                getLoadMenuItem(stage,transformationManagerView));
        return fileMenu;
    }

    private static MenuItem getSaveMenuItem(final Stage stage, TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Save...");
        item.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.setSelectedExtensionFilter(ThingsRepository.EXTENSION_FILTER);
            File file = fileChooser.showSaveDialog(stage);
            ImageUtils.writeImage(SwingFXUtils.fromFXImage(transformationManagerView.getImage(),null),file);
        });
        return item;
    }

    private static MenuItem getSavePreviewMenuItem(final Stage stage, TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Save Preview...");
        item.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.setSelectedExtensionFilter(ThingsRepository.EXTENSION_FILTER);
            File file = fileChooser.showSaveDialog(stage);
            ImageUtils.writeImage(SwingFXUtils.fromFXImage(transformationManagerView.getPreview(),null),file);
        });
        return item;
    }

    private static MenuItem getLoadMenuItem(final Stage stage, TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Load...");
        item.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(stage);
            WritableImage image = ImageUtils.readImage(file);

            transformationManagerView.setInitialImage(image);
        });
        return item;
    }

    public static Menu getShapesMenu(TransformationManagerView transformationManagerView){
        Menu fileMenu = new Menu("Shapes");
        fileMenu.getItems().addAll(getGenerateSquare(transformationManagerView),
                                   getGenerateCircle(transformationManagerView),
                                   getGenerateStripes(transformationManagerView));
        return fileMenu;
    }

    private static MenuItem getGenerateSquare(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Create Square");
        item.setOnAction(event -> {
            WritableImage image = SyntheticGenerator.squareGenerator();
            transformationManagerView.setInitialImage(image);
        });
        return item;
    }

    private static MenuItem getGenerateCircle(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Create Circle");
        item.setOnAction(event -> {
            WritableImage image = SyntheticGenerator.circleGenerator();
            transformationManagerView.setInitialImage(image);
        });
        return item;
    }

    private static MenuItem getGenerateStripes(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Create Stripes");
        item.setOnAction(event -> {
            WritableImage image = SyntheticGenerator.stripesGenerator();
            transformationManagerView.setInitialImage(image);
        });
        return item;
    }

    public static GridPane getBinaryGridPane(TransformationManagerView transformationManagerView) {
        GridPane gridPane = new GridPane();

        SliderControl sliderControl = new SliderControl("Threshold",0.0,255.0,25.5,(x,y)->{
            transformationManagerView.preview(new BinaryTransformation(y.doubleValue()/255.0));
        });

        Button apply = new Button("Apply");
        apply.setOnMouseClicked( applyEvent -> {
            transformationManagerView.addTransformation(new BinaryTransformation(sliderControl.getSelectedValue().get()/255));
        });

        gridPane.add(sliderControl,0,0);
        gridPane.add(apply,0,1);

        return gridPane;
    }

    public static GridPane getGammaGridPane(TransformationManagerView transformationManagerView) {
        GridPane gridPane = new GridPane();
        SliderControl sliderControl = new SliderControl("Gamma",0.0,1.0,0.05,(x,y)->{
            transformationManagerView.preview(new GammaTransformation(y.doubleValue()));
        });
        Button apply = new Button("Apply");
        apply.setOnMouseClicked( event -> {
            transformationManagerView.addTransformation(new GammaTransformation(sliderControl.getSelectedValue().get()));
        });
        gridPane.add(sliderControl,0,0);
        gridPane.add(apply,0,1);

        return gridPane;
    }

    public static Menu getImageMenu(Scene scene, TransformationManagerView transformationManagerView){
        Menu imageMenu = new Menu("Image");
        MenuItem imageOperations = new MenuItem("Image Operations...");
        ImageOperationsControl imageOperationsControl = new ImageOperationsControl(scene, transformationManagerView);

        imageOperations.setOnAction( event -> {
            StagesRepository.getStage("Image Operations",imageOperationsControl).show();
        });

        MenuItem histogram = new MenuItem("Histogram");
        histogram.setOnAction( event -> {
            transformationManagerView.preview(new DrawHistogramTransformation());
        });

        MenuItem equalization = new MenuItem("Equalization");
        equalization.setOnAction( event -> {
            transformationManagerView.addTransformation(new HistogramEqualizationTransformation());
        });

        MenuItem negative = new MenuItem("Negative");
        negative.setOnAction( event -> {
            Transformation transformation = new NegativeTransformation();
            transformationManagerView.addTransformation(transformation);
        });

        MenuItem binary = new MenuItem("Binary...");
        binary.setOnAction( event -> {
            StagesRepository.getStage("Binary", getBinaryGridPane(transformationManagerView)).show();
        });

        MenuItem gamma = new MenuItem("Gamma function...");
        gamma.setOnAction( event -> {
            StagesRepository.getStage("Gamma function", getGammaGridPane(transformationManagerView)).show();
        });



        imageMenu.getItems().add(negative);
        imageMenu.getItems().add(binary);
        imageMenu.getItems().add(gamma);
        imageMenu.getItems().add(histogram);
        imageMenu.getItems().add(equalization);
        imageMenu.getItems().add(getDynamicRangeCompressionMenuItem(transformationManagerView));
        imageMenu.getItems().add(imageOperations);
        return imageMenu;
    }


    private static MenuItem getDynamicRangeCompressionMenuItem(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Dynamic Range Compression");
        item.setOnAction(event -> {
            transformationManagerView.addTransformation(new DynamicRangeCompressionTransformation());
        });
        return item;
    }

    public static Menu getFilterMenu( TransformationManagerView transformationManagerView){
        Menu fileMenu = new Menu("Filter");
        fileMenu.getItems().addAll(getMedianFilterMenuItem(transformationManagerView),
                                    getMeanFilterMenuItem(transformationManagerView),
                                    getWeighedMedianFilterMenuItem(transformationManagerView),
                                    getGaussianFilterMenuItem(transformationManagerView),
                                    getLowpassFilterMenuItem(transformationManagerView),
                                    getHighpassFilterMenuItem(transformationManagerView));
        return fileMenu;
    }

    private static MenuItem getMedianFilterMenuItem(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Median Filter...");

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
            transformationManagerView.addTransformation(new StandardWeighedMedianFilterTransformation());
        });
        return item;
    }

    private static MenuItem getLowpassFilterMenuItem(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Lowpass Filter");

        item.setOnAction(event -> {
            transformationManagerView.addTransformation(new LowpassFilterTransformation());
        });
        return item;
    }

    private static MenuItem getHighpassFilterMenuItem(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Highpass Filter");

        item.setOnAction(event -> {
            transformationManagerView.addTransformation(new HighpassFilterTransformation());
        });
        return item;
    }

    private static MenuItem getMeanFilterMenuItem(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Mean Filter...");

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
        MenuItem item = new MenuItem("Gaussian Filter...");

        GridPane gridPane = new GridPane();
        Button apply = new Button("Apply");
        TextAreaControlPane result = new TextAreaControlPane("Filter Size",2);
        SliderControl filterSizeSlider = new SliderControl("Filter Size",1.0,5.0,1.0,(x, y)->{
            result.setText(String.valueOf(y.intValue()));
            transformationManagerView.preview(new GaussianFilterTransformation(y.intValue(),1.0));
        });

        SliderControl sliderControl = new SliderControl("Sigma",1.0,5.0,1.0,(x, y)->{
            result.setText(String.valueOf(y.intValue()));
            transformationManagerView.preview(new GaussianFilterTransformation(y.intValue(),1.0));
        });
        apply.setOnMouseClicked( event -> {
            transformationManagerView.addTransformation(new GaussianFilterTransformation(sliderControl.getSelectedValue().get().intValue(),1.0));
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
}
