package repositories;

import backend.ImageUtils;
import backend.SyntheticGenerator;
import backend.Utils;
import com.sun.javafx.scene.SceneUtils;
import frontend.ImageOperationsControl;
import frontend.SliderControl;
import frontend.TextAreaControlPane;
import frontend.TransformationManagerView;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import transformations.DrawHistogramTransformation;
import transformations.DynamicRangeCompressionTransformation;
import transformations.MedianFilterTransformation;
import transformations.*;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

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
            WritableImage image;
            if(Utils.fileIsRaw(file)){
                GridPane gridPane = ThingsRepository.getRawImageLoadPane(file,transformationManagerView);
                StagesRepository.getStage("Read raw",gridPane).show();
            } else {
                image = ImageUtils.readImage(file);
                transformationManagerView.setInitialImage(image);
            }
        });
        return item;
    }

    private static MenuItem getLoadRawMenuItem(final Stage stage, TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Load RAW...");
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
        fileMenu.getItems().addAll(getGenerateWhiteBlank(transformationManagerView),
                                   getGenerateGrayBlank(transformationManagerView),
                                   getGenerateSquare(transformationManagerView),
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

    private static MenuItem getGenerateWhiteBlank(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Create White");
        item.setOnAction(event -> {
            WritableImage image = SyntheticGenerator.blankGenerator(new Color(1,1,1,1));
            transformationManagerView.setInitialImage(image);
        });
        return item;
    }

    private static MenuItem getGenerateGrayBlank(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Create Gray");
        item.setOnAction(event -> {
            WritableImage image = SyntheticGenerator.blankGenerator(new Color(.5,.5,.5,1));
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
        SliderControl sliderControl = new SliderControl("Gamma",0.0,10.0,0.05,(x,y)->{
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

        MenuItem highContrast = new MenuItem("High Contrast");
        highContrast.setOnAction( event -> {
            Transformation transformation = new HighContrastTransformation();
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

        imageMenu.getItems().addAll(negative,highContrast,binary,gamma,histogram,equalization,
                                    getDynamicRangeCompressionMenuItem(transformationManagerView),
                                    imageOperations, getMultiplyByScalarMenuItem(transformationManagerView));
        return imageMenu;
    }


    private static MenuItem getDynamicRangeCompressionMenuItem(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Dynamic Range Compression");
        item.setOnAction(event -> {
            transformationManagerView.addTransformation(new DynamicRangeCompressionTransformation());
        });
        return item;
    }

    private static MenuItem getMultiplyByScalarMenuItem(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Multiply by scalar");
        GridPane gridPane = new GridPane();
        AtomicReference<Double> red = new AtomicReference<>(1.0);
        AtomicReference<Double> green = new AtomicReference<>(1.0);
        AtomicReference<Double> blue = new AtomicReference<>(1.0);

        SliderControl redSlider = new SliderControl("Red",0.0,1.0,0.05,(x,y)->{
            red.set(y.doubleValue());
            transformationManagerView.preview(new ScalarMultiplyTransformation(red.get(),green.get(),blue.get()));
        });
        SliderControl greenSlider = new SliderControl("Green",0.0,1.0,0.05,(x,y)->{
            green.set(y.doubleValue());
            transformationManagerView.preview(new ScalarMultiplyTransformation(red.get(),green.get(),blue.get()));
        });
        SliderControl blueSlider = new SliderControl("Blue",0.0,1.0,0.05,(x,y)->{
            blue.set(y.doubleValue());
            transformationManagerView.preview(new ScalarMultiplyTransformation(red.get(),green.get(),blue.get()));
        });
        Button button = new Button("Apply");
        button.setOnAction(event -> {
            transformationManagerView.addTransformation(new ScalarMultiplyTransformation(red.get(),green.get(),blue.get()));
        });
        gridPane.add(redSlider,0,0);
        gridPane.add(greenSlider,0,1);
        gridPane.add(blueSlider,0,2);
        gridPane.add(button,0,3);

        item.setOnAction(event -> {
            StagesRepository.getStage("Multiply by scalar",gridPane).show();
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
        SliderControl sliderControl = new SliderControl("Filter Size",1.0,5.0,1.0,(x, y)->{
            transformationManagerView.preview(new MedianFilterTransformation(y.intValue()));
        });
        apply.setOnMouseClicked( event -> {
            transformationManagerView.addTransformation(new MedianFilterTransformation(sliderControl.getSelectedValue().get().intValue()));
        });
        gridPane.add(sliderControl,0,0);
        gridPane.add(apply,0,1);
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
        SliderControl sliderControl = new SliderControl("Filter Size",1.0,5.0,1.0,(x, y)->{
            transformationManagerView.preview(new MeanFilterTransformation(y.intValue()));
        });
        apply.setOnMouseClicked( event -> {
            transformationManagerView.addTransformation(new MeanFilterTransformation(sliderControl.getSelectedValue().get().intValue()));
        });
        gridPane.add(sliderControl,0,0);
        gridPane.add(apply,0,1);
        item.setOnAction(event -> {
            StagesRepository.getStage("Mean Filter", gridPane).show();
        });
        return item;
    }

    private static MenuItem getGaussianFilterMenuItem(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Gaussian Filter...");

        GridPane gridPane = new GridPane();
        Button apply = new Button("Apply");
        AtomicReference<Double> sigma = new AtomicReference<>(1.0);
        AtomicReference<Integer> filterSize = new AtomicReference<>(1);
        SliderControl filterSizeSlider = new SliderControl("Filter Size",1.0,5.0,1.0,(x, y)->{
            filterSize.set(y.intValue());
            transformationManagerView.preview(new GaussianFilterTransformation(filterSize.get(),sigma.get()));
        });

        SliderControl stdSlider = new SliderControl("Sigma",0.5,5.0,1.0,(x, y)->{
            sigma.set(y.doubleValue());
            transformationManagerView.preview(new GaussianFilterTransformation(filterSize.get(),sigma.get()));
        });
        stdSlider.setSliderValue(1.0);
        apply.setOnMouseClicked( event -> {
            transformationManagerView.addTransformation(new GaussianFilterTransformation(filterSize.get(),sigma.get()));
        });
        gridPane.add(filterSizeSlider,0,0);
        gridPane.add(stdSlider,0,1);
        gridPane.add(apply,0,2);
        item.setOnAction(event -> {
            StagesRepository.getStage("Gaussian Filter", gridPane).show();
        });
        return item;
    }

    public static Menu getNoiseMenu(TransformationManagerView transformationManagerView){
        Menu fileMenu = new Menu("Noise");
        fileMenu.getItems().addAll(getSaltAndPepperNoiseTransformation(transformationManagerView),
                getAditiveGaussianNoiseTransformation(transformationManagerView),
                getRayleighDistributionNoiseTransformation(transformationManagerView),
                getExponentialDistributionNoiseTransformation(transformationManagerView));
        return fileMenu;
    }

    private static MenuItem getSaltAndPepperNoiseTransformation(TransformationManagerView transformationManagerView) {
        MenuItem item = new MenuItem("Salt and pepper...");

        GridPane gridPane = new GridPane();
        Button apply = new Button("Apply");
        AtomicReference<Double> salt = new AtomicReference<>(0.1);
        AtomicReference<Double> pepper = new AtomicReference<>(0.1);
        TextAreaControlPane seed = new TextAreaControlPane("Seed",2);
        seed.setText("42");
        SliderControl saltSlider = new SliderControl("Salt",0.0,1.0,0.05,(x, y)->{
            salt.set(y.doubleValue());
            transformationManagerView.preview(new SaltAndPeperTransformation(seed.getText(),salt.get(),pepper.get()));
        });
        saltSlider.setSliderValue(0.0);

        SliderControl pepperSlider = new SliderControl("Pepper",0.0,1.0,0.05,(x, y)->{
            pepper.set(y.doubleValue());
            transformationManagerView.preview(new SaltAndPeperTransformation(seed.getText(),salt.get(),pepper.get()));
        });
        pepperSlider.setSliderValue(0.0);
        apply.setOnMouseClicked( event -> {
            transformationManagerView.addTransformation(new SaltAndPeperTransformation(seed.getText(),salt.get(),pepper.get()));
        });
        gridPane.add(saltSlider,0,0);
        gridPane.add(pepperSlider,0,1);
        gridPane.add(seed,0,2);
        gridPane.add(apply,1,2);
        item.setOnAction(event -> {
            StagesRepository.getStage("Gaussian Filter", gridPane).show();
        });
        return item;
    }

    private static MenuItem getAditiveGaussianNoiseTransformation(TransformationManagerView transformationManagerView) {
        MenuItem item = new MenuItem("Additive Gaussian Noise...");

        GridPane gridPane = new GridPane();
        Button apply = new Button("Apply");
        AtomicReference<Double> mean = new AtomicReference<>(0.1);
        AtomicReference<Double> sigma = new AtomicReference<>(0.1);
        TextAreaControlPane seed = new TextAreaControlPane("Seed",2);
        seed.setText("42");
        SliderControl meanSlider = new SliderControl("Mean",0.0,1.0,0.05,(x, y)->{
            mean.set(y.doubleValue());
            transformationManagerView.preview(new AdditiveGaussianNoiseTransformation(seed.getText(),mean.get(),sigma.get()));
        });
        meanSlider.setSliderValue(0.0);

        SliderControl sigmaSlider = new SliderControl("Sigma",0.0,1.0,0.05,(x, y)->{
            sigma.set(y.doubleValue());
            transformationManagerView.preview(new AdditiveGaussianNoiseTransformation(seed.getText(),mean.get(),sigma.get()));
        });
        sigmaSlider.setSliderValue(0.0);
        apply.setOnMouseClicked( event -> {
            transformationManagerView.addTransformation(new AdditiveGaussianNoiseTransformation(seed.getText(),mean.get(),sigma.get()));
        });
        gridPane.add(meanSlider,0,0);
        gridPane.add(sigmaSlider,0,1);
        gridPane.add(seed,0,2);
        gridPane.add(apply,1,2);
        item.setOnAction(event -> {
            StagesRepository.getStage("Gaussian Noise", gridPane).show();
        });
        return item;
    }

    private static MenuItem getRayleighDistributionNoiseTransformation(TransformationManagerView transformationManagerView) {
        MenuItem item = new MenuItem("Rayleigh Distribution Noise...");

        GridPane gridPane = new GridPane();
        Button apply = new Button("Apply");
        AtomicReference<Double> phi = new AtomicReference<>(0.1);
        AtomicReference<Double> noiseLevel = new AtomicReference<>(0.1);
        SliderControl phiSlider = new SliderControl("Phi",0.0,5.0,0.2,(x, y)->{
            phi.set(y.doubleValue());
            transformationManagerView.preview(new RayleighDistributionNoiseTransformation(phi.get(), noiseLevel.get()));
        });
        SliderControl noiseLevelSlider = new SliderControl("Noise Level",0.0,1.0,0.05,(x, y)->{
            noiseLevel.set(y.doubleValue());
            transformationManagerView.preview(new RayleighDistributionNoiseTransformation(phi.get(), noiseLevel.get()));
        });
        apply.setOnMouseClicked( event -> {
            transformationManagerView.addTransformation(new RayleighDistributionNoiseTransformation(phi.get(), noiseLevel.get()));
        });
        phiSlider.setSliderValue(0.0);
        gridPane.add(phiSlider,0,0);
        gridPane.add(noiseLevelSlider,1,0);
        gridPane.add(apply,1,2);
        item.setOnAction(event -> {
            StagesRepository.getStage("Rayleigh Noise", gridPane).show();
        });
        return item;
    }

    private static MenuItem getExponentialDistributionNoiseTransformation(TransformationManagerView transformationManagerView) {
        MenuItem item = new MenuItem("Exponential Distribution Noise...");

        GridPane gridPane = new GridPane();
        Button apply = new Button("Apply");
        AtomicReference<Double> lambda = new AtomicReference<>(0.1);
        AtomicReference<Double> noiseLevel = new AtomicReference<>(0.1);
        SliderControl phiSlider = new SliderControl("Lambda",0.0,5.0,0.2,(x, y)->{
            lambda.set(y.doubleValue());
            transformationManagerView.preview(new ExponentialDistributionNoiseTransformation(lambda.get(), noiseLevel.get()));
        });
        SliderControl noiseLevelSlider = new SliderControl("Noise Level",0.0,1.0,0.05,(x, y)->{
            noiseLevel.set(y.doubleValue());
            transformationManagerView.preview(new ExponentialDistributionNoiseTransformation(lambda.get(), noiseLevel.get()));
        });
        apply.setOnMouseClicked( event -> {
            transformationManagerView.addTransformation(new ExponentialDistributionNoiseTransformation(lambda.get(), noiseLevel.get()));
        });
        phiSlider.setSliderValue(0.0);
        gridPane.add(phiSlider,0,0);
        gridPane.add(noiseLevelSlider,1,0);
        gridPane.add(apply,1,2);
        item.setOnAction(event -> {
            StagesRepository.getStage("Exponential Noise", gridPane).show();
        });
        return item;
    }
}
