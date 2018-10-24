package repositories;

import backend.FocusablePane;
import backend.Pixel;
import backend.utils.ImageUtils;
import backend.SyntheticGenerator;
import backend.utils.Utils;
import frontend.*;
import frontend.builder.MultiSliderGridPaneBuilder;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import transformations.normal.*;
import transformations.normal.canny.CannyTransformation;
import transformations.normal.canny.NonMaximalBorderSupressionTransformation;
import transformations.normal.canny.OrthogonalAngleDirectionTransformation;
import transformations.normal.difusion.AnisotropicDifusionTransformation;
import transformations.normal.difusion.IsotropicDifusionTransformation;
import transformations.normal.umbrals.GlobalUmbralizationTransformation;
import transformations.normal.umbrals.MultiChannelBinaryTransformation;
import transformations.normal.umbrals.*;
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

import java.beans.EventHandler;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

import static frontend.FrontendUtils.getMenuItemByGridpane;
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
                ,getBilateralFilterMenuItem(transformationManagerView)
                ,getMenuItemByTranformation("Weighed Median Filter",new StandardWeighedMedianFilterTransformation(),transformationManagerView)
                ,getMenuItemByTranformation("Laplacian",new LaplacianFilterTransformation(),transformationManagerView)
                ,getMenuItemByTranformation("Lowpass",new LowpassFilterTransformation(),transformationManagerView)
                ,getMenuItemByTranformation("Highpass",new HighpassFilterTransformation(),transformationManagerView)
                ,getMenuItemByGridpane("Custom Median Filter",new CustomFilterControlPane(transformationManagerView))
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

    public static Menu getBordersMenu(TransformationManagerView transformationManagerView, EventManageableImageView imageView){
        Menu fileMenu = new Menu("Borders");


        fileMenu.getItems().addAll(
                getMenuItemByTranformation("Sobel",new SobelBorderTransformation(),transformationManagerView)
                ,getLineHoughMenuItem(transformationManagerView)
                ,getCircleHoughMenuItem(transformationManagerView)
                ,getMenuItemByTranformation("Orthogonal",new OrthogonalAngleDirectionTransformation(),transformationManagerView)
                ,getMenuItemByTranformation("Non Maximal",new NonMaximalBorderSupressionTransformation(),transformationManagerView)
                ,getMenuItemByTranformation("Canny",new CannyTransformation(),transformationManagerView)
                ,getMenuItemByTranformation("Prewitt",new PrewittBorderTransformation(),transformationManagerView)
                ,getMenuItemByTranformation("Susan",new SusanFilterTransformation(),transformationManagerView)
                ,getMenuItemByTranformation("Laplacian",new LaplacianBorderTransformation(),transformationManagerView)
                ,getMenuItemByTranformation("Laplacian of Gaussian",new LaplacianOfGaussianBorderTransformation(),transformationManagerView)
                ,getMenuItemByTranformation("Zero Finding",new ZeroFindingTransformation(),transformationManagerView)
                ,getAnisotropicDiffusionFilterMenuItem(transformationManagerView)
                ,getIsotropicDiffusionFilterMenuItem(transformationManagerView)
                ,getActiveBordersPanel(imageView, transformationManagerView)
        );
        return fileMenu;
    }

    public static Menu getUmbralsMenu(TransformationManagerView transformationManagerView){
        Menu imageMenu = new Menu("Umbrals");

        MenuItem singleChannelBinary = getMenuItemByGridpane("Single channel Binary" ,new SingleSliderGridPane("Threshold",0.0,1.0,0.02,
                    threashold -> new SingleChannelBinaryTransformation(threashold.doubleValue()),transformationManagerView));

        MenuItem multiChannelBinary = getMenuItemByGridpane("Multi channel Binary", new SingleSliderGridPane("Threshold",0.0,1.0,0.02,
                        threashold -> new MultiChannelBinaryTransformation(threashold.doubleValue()),transformationManagerView));

        imageMenu.getItems().addAll(singleChannelBinary
                ,multiChannelBinary
                ,getTernaryUmbralizationMenuItem(transformationManagerView)
                ,getHisteresisUmbralizationMenuItem(transformationManagerView)
                ,getMenuItemByTranformation("Border expand", new BorderExpandUmbralizationTransformation(),transformationManagerView)
                ,getMenuItemByTranformation("Global umbralization", new GlobalUmbralizationTransformation(), transformationManagerView)
                ,getMenuItemByTranformation("Otsu umbralization", new OtsuUmbralizationTransformation(), transformationManagerView)
        );

        return imageMenu;
    }

    private static MenuItem getTernaryUmbralizationMenuItem(TransformationManagerView transformationManagerView) {
        MultiSliderGridPaneBuilder sliderGridPaneBuilder = new MultiSliderGridPaneBuilder(l ->
                new MultiChannelTernaryTransformation(l.get(0).doubleValue(),l.get(1).doubleValue()),transformationManagerView);

        sliderGridPaneBuilder.addSlider("Min ",0.0,1.0,0.025);
        sliderGridPaneBuilder.addSlider("Max",0.0,1.0,0.025);
        return sliderGridPaneBuilder.buildAndGetMenuItem("Ternary Umbralization");
    }

    private static MenuItem getHisteresisUmbralizationMenuItem(TransformationManagerView transformationManagerView) {
        MultiSliderGridPaneBuilder sliderGridPaneBuilder = new MultiSliderGridPaneBuilder(l ->
                new HisteresisUmbralizationTransformation(l.get(0).doubleValue(),l.get(1).doubleValue()),transformationManagerView);

        sliderGridPaneBuilder.addSlider("Min",0.0,1.0,0.025);
        sliderGridPaneBuilder.addSlider("Max",0.0,1.0,0.025);
        return sliderGridPaneBuilder.buildAndGetMenuItem("Histeresis Umbralization");
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

        MenuItem gamma = new MenuItem("Gamma function...");
        gamma.setOnAction( event -> {
            StagesRepository.getStage("Gamma function", new SingleSliderGridPane("Gamma",0.0,1.0,0.05,
                    val -> new GammaTransformation(val.doubleValue()),transformationManagerView)).show();
        });

        imageMenu.getItems().addAll(
                getMenuItemByTranformation("Negative",new NegativeTransformation(),transformationManagerView),
                getMenuItemByTranformation("High contrast",new HighContrastTransformation(),transformationManagerView),
                gamma,
                histogram,
                getMenuItemByTranformation("Equalization", new HistogramEqualizationTransformation(),transformationManagerView),
                getMenuItemByTranformation("Dynamic Range Compression", new DynamicRangeCompressionTransformation(),transformationManagerView),
                imageOperations, getMultiplyByScalarMenuItemV2(transformationManagerView),
                getVideo());
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

    private static MenuItem getMultiplyByScalarMenuItemV2(TransformationManagerView transformationManagerView) {
        MultiSliderGridPaneBuilder builder = new MultiSliderGridPaneBuilder(list->
                new ScalarMultiplyTransformation(list.get(0).doubleValue(),list.get(1).doubleValue(),list.get(2).doubleValue()),transformationManagerView);
        builder.addSlider("Red",0.0,1.0,0.05);
        builder.addSlider("Green",0.0,1.0,0.05);
        builder.addSlider("Blue",0.0,1.0,0.05);
        return builder.buildAndGetMenuItem("Multiply by scalar");
    }

    private static MenuItem getMedianFilterMenuItem(TransformationManagerView transformationManagerView){
        MultiSliderGridPaneBuilder builder = new MultiSliderGridPaneBuilder((list) ->
                new MedianFilterTransformation(list.get(0).intValue()),transformationManagerView);

        builder.addSlider("Filter Size",1.0,5.0,1.0);
        return builder.buildAndGetMenuItem("Median Filter");
    }

    private static MenuItem getMeanFilterMenuItem(TransformationManagerView transformationManagerView){
        MultiSliderGridPaneBuilder builder = new MultiSliderGridPaneBuilder((list) ->
                new MeanFilterTransformation(list.get(0).intValue()),transformationManagerView);

        builder.addSlider("Filter Size",1.0,5.0,1.0);
        return builder.buildAndGetMenuItem("Mean Filter");
    }

    private static MenuItem getLineHoughMenuItem(TransformationManagerView transformationManagerView){
        MultiSliderGridPaneBuilder builder = new MultiSliderGridPaneBuilder((list) ->
                new LineHoughTransformation(list.get(0).intValue()),transformationManagerView);

        builder.addSlider("Max lines",1.0,40.0,1.0);
        return builder.buildAndGetMenuItem("Hough");
    }

    private static MenuItem getCircleHoughMenuItem(TransformationManagerView transformationManagerView){
        MultiSliderGridPaneBuilder builder = new MultiSliderGridPaneBuilder((list) ->
                new CircleHoughTransformation(list.get(0).doubleValue(), list.get(1).intValue()),transformationManagerView);

        builder.addSlider("Epsilon",1.0,30.0,1.0);
        builder.addSlider("Max circles",1.0,40.0,1.0);
        return builder.buildAndGetMenuItem("Hough (Circles)");
    }

    private static MenuItem getGaussianMeanFilterMenuItem(TransformationManagerView transformationManagerView) {
        MultiSliderGridPaneBuilder sliderGridPaneBuilder = new MultiSliderGridPaneBuilder(l ->
                new GaussianMeanFilterTransformation(l.get(0).intValue(),l.get(1).doubleValue()),transformationManagerView);

        sliderGridPaneBuilder.addSlider("Filter Size",1.0,5.0,1.0);
        sliderGridPaneBuilder.addSlider("Sigma",0.5,5.0,0.5);
        return sliderGridPaneBuilder.buildAndGetMenuItem("Gaussian Mean Filter");
    }

    private static MenuItem getAnisotropicDiffusionFilterMenuItem(TransformationManagerView transformationManagerView) {
        MultiSliderGridPaneBuilder sliderGridPaneBuilder = new MultiSliderGridPaneBuilder(l ->
                new AnisotropicDifusionTransformation(l.get(0).doubleValue(),l.get(1).intValue()),transformationManagerView);

        sliderGridPaneBuilder.addSlider("Sigma",0.0,0.5,0.01);
        sliderGridPaneBuilder.addSlider("Iterations",5.0,75.0,5.0);
        return sliderGridPaneBuilder.buildAndGetMenuItem("Anisotropic Diffusion");
    }

    private static MenuItem getIsotropicDiffusionFilterMenuItem(TransformationManagerView transformationManagerView) {
        MultiSliderGridPaneBuilder sliderGridPaneBuilder = new MultiSliderGridPaneBuilder(l ->
                new IsotropicDifusionTransformation(l.get(0).doubleValue(),l.get(1).intValue()),transformationManagerView);

        sliderGridPaneBuilder.addSlider("Sigma",0.0,0.5,0.01);
        sliderGridPaneBuilder.addSlider("Iterations",5.0,75.0,5.0);
        return sliderGridPaneBuilder.buildAndGetMenuItem("Isotropic Diffusion");
    }

    public static MenuItem getActiveBordersPanel(EventManageableImageView imageView, TransformationManagerView transformationManagerView){
        PixelPickerControlPane firstPixelPicker = new PixelPickerControlPane(imageView, "Top left cornet");
        PixelPickerControlPane secondPixelPicker = new PixelPickerControlPane(imageView, "Bottom right corner");

        //This event is triggered whenever the button is clicked
        FocusablePane drawLinePanel = new FocusablePane(img -> {
            img.addActiveEventToQueue(firstPixelPicker.getEventConsumer(),true);
            img.addActiveEventToQueue(secondPixelPicker.getEventConsumer(),true);

            img.setWhenQueueFinished(() -> {
                Pixel first = firstPixelPicker.getChosenPixel().get();
                Pixel second = secondPixelPicker.getChosenPixel().get();

                transformationManagerView.addTransformation(
                        new ActiveBorderTransformation(first.getX(),first.getY(),second.getX(),second.getY()));
            });
            System.out.println("Draw line at panel recieved focus, added 2 things to the active event queue");
        });

        drawLinePanel.add(firstPixelPicker,0,0);
        drawLinePanel.add(secondPixelPicker,0,1);
        return FrontendUtils.getMenuItemByGridpane("Active Borders",drawLinePanel);
    }



    private static MenuItem getBilateralFilterMenuItem(TransformationManagerView transformationManagerView) {
        MultiSliderGridPaneBuilder sliderGridPaneBuilder = new MultiSliderGridPaneBuilder(l ->
                new BilateralFilterTransformation(l.get(0).doubleValue(),l.get(1).doubleValue()),transformationManagerView);

        sliderGridPaneBuilder.addSlider("Color STD",1.0,30.0,1.0);
        sliderGridPaneBuilder.addSlider("Spatial STD",1.0,5.0,0.5);
        return sliderGridPaneBuilder.buildAndGetMenuItem("Bilateral Filter");
    }


    private static MenuItem getVideo(){

        MenuItem item = new MenuItem("Get Video");
        GridPane root = new GridPane();

//        MediaPlayer player = new MediaPlayer( new Media("https://www.youtube.com/watch?v=vKQmeCl0PWc"));
        Media media = new Media("http://localhost:8090/camera.mjpeg");
        Media media2 = new Media("https://www.youtube.com/watch?v=vKQmeCl0PWc");
//        Media media3 = ImagesRepository.OVER;
        MediaPlayer player = new MediaPlayer( media);
        System.out.println("PAso");
        MediaView mediaView = new MediaView(player);
        root.add(mediaView,0,1);
        Button b = new Button("Play");
        b.setOnMouseClicked( e-> player.play());
        root.add(b,0,0);
        item.setOnAction(event ->{
            StagesRepository.getStage("VideoStream ",root).show();
            player.play();
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