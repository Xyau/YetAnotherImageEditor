package repositories;

import backend.utils.ImageUtils;
import backend.SyntheticGenerator;
import backend.utils.Utils;
import frontend.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import transformations.normal.umbrals.GlobalUmbralizationTransformation;
import transformations.normal.umbrals.MultiChannelBinaryTransformation;
import transformations.normal.DrawHistogramTransformation;
import transformations.normal.DynamicRangeCompressionTransformation;
import transformations.normal.GammaTransformation;
import transformations.normal.HighContrastTransformation;
import transformations.normal.HistogramEqualizationTransformation;
import transformations.normal.NegativeTransformation;
import transformations.normal.RayleighDistributionNoiseTransformation;
import transformations.normal.borders.*;
import transformations.normal.colors.BrightenTransformation;
import transformations.normal.colors.DarkenTransformation;
import transformations.normal.colors.GrayscaleTransformation;
import transformations.normal.colors.ScalarMultiplyTransformation;
import transformations.normal.filters.MedianFilterTransformation;
import transformations.normal.filters.*;
import transformations.normal.noise.AdditiveGaussianNoiseTransformation;
import transformations.normal.noise.ExponentialDistributionNoiseTransformation;
import transformations.normal.noise.SaltAndPeperNoiseTransformation;
import transformations.normal.umbrals.SingleChannelBinaryTransformation;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

import static frontend.FrontendUtils.getMenuItemByTranformation;

public class MenusRepository {
    public static Menu getFileMenu(Stage stage, TransformationManagerView transformationManagerView){
        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(
                getSaveMenuItem(stage,transformationManagerView),
                getSavePreviewMenuItem(stage, transformationManagerView),
                getLoadMenuItem(stage,transformationManagerView)
        );
        return fileMenu;
    }

    public static Menu getShapesMenu(TransformationManagerView transformationManagerView){
        Menu fileMenu = new Menu("Shapes");
        fileMenu.getItems().addAll(
                getGenerateWhiteBlank(transformationManagerView),
                getGenerateGrayBlank(transformationManagerView),
                getGenerateSquare(transformationManagerView),
                getGenerateCircle(transformationManagerView),
                getGenerateStripes(transformationManagerView)
        );
        return fileMenu;
    }

    public static Menu getFilterMenu( TransformationManagerView transformationManagerView){
        Menu fileMenu = new Menu("Filter");
        fileMenu.getItems().addAll(getMedianFilterMenuItem(transformationManagerView)
                ,getMeanFilterMenuItem(transformationManagerView)
                ,getGaussianMeanFilterMenuItem(transformationManagerView)
                ,getMenuItemByTranformation("Weighed Median Filter",new StandardWeighedMedianFilterTransformation(),transformationManagerView)
                ,getMenuItemByTranformation("Laplacian",new LaplacianFilterTransformation(),transformationManagerView)
                ,getMenuItemByTranformation("Lowpass",new LowpassFilterTransformation(),transformationManagerView)
                ,getMenuItemByTranformation("Highpass",new HighpassFilterTransformation(),transformationManagerView)
                ,getCustomFilterMenuItem(transformationManagerView)
        );
        return fileMenu;
    }

    public static Menu getColorsMenu(TransformationManagerView transformationManagerView){
        Menu fileMenu = new Menu("Colors");
        fileMenu.getItems().addAll(
                getMenuItemByTranformation("Grayscale",new GrayscaleTransformation(),transformationManagerView),
                getMenuItemByTranformation("Brighten",new BrightenTransformation(),transformationManagerView),
                getMenuItemByTranformation("Darken",new DarkenTransformation(),transformationManagerView));
        return fileMenu;
    }

    public static Menu getBordersMenu(TransformationManagerView transformationManagerView){
        Menu fileMenu = new Menu("Borders");
        fileMenu.getItems().addAll(
                getMenuItemByTranformation("Sobel",new SobelBorderTransformation(),transformationManagerView)
                ,getMenuItemByTranformation("Prewitt",new PrewittBorderTransformation(),transformationManagerView)
                ,getMenuItemByTranformation("Laplacian",new LaplacianBorderTransformation(),transformationManagerView)
                ,getMenuItemByTranformation("Laplacian of Gaussian",new LaplacianOfGaussianBorderTransformation(),transformationManagerView)
                ,getMenuItemByTranformation("Zero Finding",new ZeroFindingTransformation(),transformationManagerView)
        );
        return fileMenu;
    }

    public static Menu getUmbralsMenu(TransformationManagerView transformationManagerView){
        Menu imageMenu = new Menu("Umbrals");

        MenuItem singleChannelBinary = new MenuItem("Single channel Binary...");
        singleChannelBinary.setOnAction( event -> {
            StagesRepository.getStage("Single channel Binary", new SingleSliderGridPane("Threshold",0.0,1.0,0.02,
                    threashold -> new SingleChannelBinaryTransformation(threashold.doubleValue()),transformationManagerView)).show();
        });

        MenuItem multiChannelBinary = new MenuItem("Multi channel Binary...");
        multiChannelBinary.setOnAction( event -> {
            StagesRepository.getStage("Multi channel Binary", new SingleSliderGridPane("Threshold",0.0,1.0,0.02,
                    threashold -> new MultiChannelBinaryTransformation(threashold.doubleValue()),transformationManagerView)).show();
        });

        imageMenu.getItems().addAll(singleChannelBinary
                ,multiChannelBinary
                ,getMenuItemByTranformation("Global umbralization", new GlobalUmbralizationTransformation(), transformationManagerView)
        );
        return imageMenu;
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

        MenuItem binary = new MenuItem("Binary...");
        binary.setOnAction( event -> {
            StagesRepository.getStage("Binary", new SingleSliderGridPane("Threshold",0.0,1.0,0.02,
                    threashold -> new MultiChannelBinaryTransformation(threashold.doubleValue()),transformationManagerView)).show();
        });

        MenuItem gamma = new MenuItem("Gamma function...");
        gamma.setOnAction( event -> {
            StagesRepository.getStage("Gamma function", new SingleSliderGridPane("Gamma",0.0,1.0,0.05,
                    val -> new GammaTransformation(val.doubleValue()),transformationManagerView)).show();
        });

        imageMenu.getItems().addAll(
                getMenuItemByTranformation("Negative",new NegativeTransformation(),transformationManagerView),
                getMenuItemByTranformation("High contrast",new HighContrastTransformation(),transformationManagerView),
                binary,
                gamma,
                histogram,
                getMenuItemByTranformation("Equalization", new HistogramEqualizationTransformation(),transformationManagerView),
                getDynamicRangeCompressionMenuItem(transformationManagerView),
                imageOperations, getMultiplyByScalarMenuItem(transformationManagerView));
        return imageMenu;
    }

    public static Menu getNoiseMenu(TransformationManagerView transformationManagerView){
        Menu fileMenu = new Menu("Noise");
        fileMenu.getItems().addAll(getSaltAndPepperNoiseTransformation(transformationManagerView),
                getAditiveGaussianNoiseTransformation(transformationManagerView),
                getRayleighDistributionNoiseTransformation(transformationManagerView),
                getExponentialDistributionNoiseTransformation(transformationManagerView));
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

    private static MenuItem getCustomFilterMenuItem(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Custom Median Filter");
        CustomFilterControlPane customFilterControlPane = new CustomFilterControlPane(transformationManagerView);
        item.setOnAction( e -> StagesRepository.getStage("Custom Median Filter",customFilterControlPane).show());
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

    private static MenuItem getGaussianMeanFilterMenuItem(TransformationManagerView transformationManagerView) {
        MenuItem item = new MenuItem("Gaussian Mean Filter...");

        GridPane gridPane = new GridPane();
        Button apply = new Button("Apply");
        AtomicReference<Double> sigma = new AtomicReference<>(1.0);
        AtomicReference<Integer> filterSize = new AtomicReference<>(1);
        SliderControl filterSizeSlider = new SliderControl("Filter Size",1.0,5.0,1.0,(x, y)->{
            filterSize.set(y.intValue());
            transformationManagerView.preview(new GaussianMeanFilterTransformation(filterSize.get(),sigma.get()));
        });

        SliderControl stdSlider = new SliderControl("Sigma",0.5,5.0,0.5,(x, y)->{
            sigma.set(y.doubleValue());
            transformationManagerView.preview(new GaussianMeanFilterTransformation(filterSize.get(),sigma.get()));
        });
        apply.setOnMouseClicked( event -> {
            transformationManagerView.addTransformation(new GaussianMeanFilterTransformation(filterSize.get(),sigma.get()));
        });
        gridPane.add(filterSizeSlider,0,0);
        gridPane.add(stdSlider,0,1);
        gridPane.add(apply,0,2);
        item.setOnAction(event -> {
            StagesRepository.getStage("Gaussian Mean Filter", gridPane).show();
        });
        return item;

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
            transformationManagerView.preview(new SaltAndPeperNoiseTransformation(seed.getText(),salt.get(),pepper.get()));
        });
        saltSlider.setSliderValue(0.0);

        SliderControl pepperSlider = new SliderControl("Pepper",0.0,1.0,0.05,(x, y)->{
            pepper.set(y.doubleValue());
            transformationManagerView.preview(new SaltAndPeperNoiseTransformation(seed.getText(),salt.get(),pepper.get()));
        });
        pepperSlider.setSliderValue(0.0);
        apply.setOnMouseClicked( event -> {
            transformationManagerView.addTransformation(new SaltAndPeperNoiseTransformation(seed.getText(),salt.get(),pepper.get()));
        });
        gridPane.add(saltSlider,0,0);
        gridPane.add(pepperSlider,0,1);
        gridPane.add(seed,0,2);
        gridPane.add(apply,1,2);
        item.setOnAction(event -> {
            StagesRepository.getStage("Salt and pepper noise", gridPane).show();
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
        TextAreaControlPane seed = new TextAreaControlPane("Seed:");
        seed.setText("42");
        SliderControl phiSlider = new SliderControl("Phi",0.0,5.0,0.2,(x, y)->{
            phi.set(y.doubleValue());
            transformationManagerView.preview(new RayleighDistributionNoiseTransformation(phi.get(), noiseLevel.get(),seed.getText()));
        });
        SliderControl noiseLevelSlider = new SliderControl("Noise Level",0.0,1.0,0.05,(x, y)->{
            noiseLevel.set(y.doubleValue());
            transformationManagerView.preview(new RayleighDistributionNoiseTransformation(phi.get(), noiseLevel.get(),seed.getText()));
        });
        apply.setOnMouseClicked( event -> {
            transformationManagerView.addTransformation(new RayleighDistributionNoiseTransformation(phi.get(), noiseLevel.get(), seed.getText()));
        });
        phiSlider.setSliderValue(0.0);
        gridPane.add(phiSlider,0,0);
        gridPane.add(noiseLevelSlider,1,0);
        gridPane.add(seed,0,2);
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
        TextAreaControlPane seed = new TextAreaControlPane("Seed");
        SliderControl phiSlider = new SliderControl("Lambda",0.0,5.0,0.2,(x, y)->{
            lambda.set(y.doubleValue());
            transformationManagerView.preview(new ExponentialDistributionNoiseTransformation(lambda.get(), noiseLevel.get(),seed.getText()));
        });
        SliderControl noiseLevelSlider = new SliderControl("Noise Level",0.0,1.0,0.05,(x, y)->{
            noiseLevel.set(y.doubleValue());
            transformationManagerView.preview(new ExponentialDistributionNoiseTransformation(lambda.get(), noiseLevel.get(),seed.getText()));
        });
        apply.setOnMouseClicked( event -> {
            transformationManagerView.addTransformation(new ExponentialDistributionNoiseTransformation(lambda.get(), noiseLevel.get(),seed.getText()));
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
