import backend.ImageUtils;
import backend.TransformationManager;
import backend.Utils;
import frontend.RGBChooserView;
import frontend.TransformationManagerView;
import frontend.WritableImageView;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import transformations.DarkenTransformation;
import transformations.DrawLineTransformation;
import transformations.DrawSquareTransformation;
import transformations.Transformation;

import java.awt.image.BufferedImage;
import java.io.File;

public class HelloWorld extends Application {

    private WritableImageView currentImage;

    private RGBChooserView rgbChooserView;

    private EventHandler<? super MouseEvent> nextAction;

    private TransformationManagerView transformationManagerView;

    private WritableImageView getCurrentImageView(){
        WritableImageView iv1 = new WritableImageView();
        iv1.setLayoutX(40);
        iv1.setLayoutY(100);
        iv1.setWritableImage(ImageUtils.copyImage(new Image("noImage.jpg")));
        iv1.setOnMouseClicked( event -> {
            System.out.println("Click event at: (" + event.getX() +
                    "," + event.getY() + ")");

            if(nextAction == null){
                Color c = iv1.getWritableImage().getPixelReader()
                        .getColor(Utils.toInteger(event.getX()),Utils.toInteger(event.getY()));
                rgbChooserView.updateAreasWith(c);
            } else {
                nextAction.handle(event);
            }
        });
        return iv1;
    }

    private Button getSaveButton(final Stage stage){
        Button btn = new Button();
        btn.setLayoutX(20);
        btn.setLayoutY(50);
        btn.setText("Save Image");
        btn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.setSelectedExtensionFilter(ThingsRepository.EXTENSION_FILTER);
            File file = fileChooser.showSaveDialog(stage);
            ImageUtils.writeImage(SwingFXUtils.fromFXImage(currentImage.getWritableImage(),null),file);
        });
        return btn;
    }

    private void reloadCurrentImage(Image image){
        currentImage.setWritableImage(ImageUtils.copyImage(image));
    }

    private Button getLoadButton(final Stage stage){
        Button btn = new Button();
        btn.setLayoutX(120);
        btn.setLayoutY(50);
        btn.setText("Load Image");
        btn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(stage);
            BufferedImage image = ImageUtils.readImage(file.getPath());
            Image fxImage = SwingFXUtils.toFXImage(image,null);
            reloadCurrentImage(fxImage);
        });
        return btn;
    }

    public Button getDrawCircleAtButton(){
        Button button = new Button();
        button.setText("Draw circle");
        button.setOnMouseClicked( buttonClickEvent -> {
            nextAction = imageClickEvent -> {
                Integer x = Utils.toInteger(imageClickEvent.getX());
                Integer y = Utils.toInteger(imageClickEvent.getY());
                Transformation transformation = new DrawSquareTransformation(x,y,50,50,Color.RED);
                transformationManagerView.addTransformation(transformation);
            };
        });
        return button;
    }

    public Button getDarkenButton(){
        Button button = new Button();
        button.setText("Darken");
        button.setOnMouseClicked( buttonClickEvent -> {
            Transformation transformation = new DarkenTransformation();
            transformationManagerView.addTransformation(transformation);
        });
        return button;
    }

    public Button getGradientMenu(){
        Button button = new Button();
        button.setLayoutX(600);
        button.setLayoutY(20);
        button.setText("Draw gradient");
        button.setOnMouseClicked( buttonClickEvent -> {
                nextAction = imageClickEvent -> {
                Integer x1 = Utils.toInteger(imageClickEvent.getX());
                Integer y1 = Utils.toInteger(imageClickEvent.getY());
                nextAction = secondImageClickEvent -> {
                    Integer x2 = Utils.toInteger(secondImageClickEvent.getX());
                    Integer y2 = Utils.toInteger(secondImageClickEvent.getY());

                    Transformation transformation = new DrawLineTransformation(x1,y1,x2,y2,Color.RED);
                    transformationManagerView.addTransformation(transformation);
                    nextAction = null;
                };
            };
        });
        return button;
    }

    @Override
    public void start(Stage primaryStage) {


        GridPane root = new GridPane();
        currentImage = getCurrentImageView();
        rgbChooserView = new RGBChooserView(currentImage);
        TransformationManager transformationManager = new TransformationManager(currentImage.getWritableImage());
        transformationManagerView = new TransformationManagerView(transformationManager,currentImage);

        currentImage.setImage(transformationManager.getImage());
        root.add(transformationManagerView,0,0);
        root.add(getDrawCircleAtButton(),0,1);
        root.add(getGradientMenu(),1,1);
        root.add(rgbChooserView.getPane(),1,2);
        root.add(getSaveButton(primaryStage),2,1);
        root.add(getLoadButton(primaryStage),3,1);
        root.add(getDarkenButton(),4,1);
//        root.add(getCanvas(),2,3);

        root.add(currentImage,0,3,5,5);
        Scene scene = new Scene(root, 1000, 1000);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}