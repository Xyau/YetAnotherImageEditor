import backend.ImageUtils;
import backend.Images;
import backend.TransformationManager;
import backend.Utils;
import frontend.PixelPickerControlPane;
import frontend.RGBChooserView;
import frontend.TransformationManagerView;
import frontend.WritableImageView;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import transformations.*;

import java.awt.image.BufferedImage;
import java.io.File;

public class HelloWorld extends Application {

    private WritableImageView currentImage;
    private WritableImageView currentImage2;

    private RGBChooserView rgbChooserView;

    private EventHandler<? super MouseEvent> nextAction;

    private TransformationManagerView transformationManagerView;

    private WritableImageView getCurrentImageView(){
        WritableImageView iv1 = new WritableImageView();
        iv1.setLayoutX(40);
        iv1.setLayoutY(100);
        iv1.setWritableImage(ImageUtils.copyImage(Images.NO_IMAGE));
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

    private void reloadImage(WritableImageView writableImageView, Image image){
        writableImageView.setWritableImage(ImageUtils.copyImage(image));
    }

    private Button getLoadButton(final Stage stage, int imgIdx){
        Button btn = new Button();
        btn.setLayoutX(120);
        btn.setLayoutY(50);
        btn.setText("Load Image " + (imgIdx + 1));
        btn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(stage);
            BufferedImage image = ImageUtils.readImage(file.getPath());
            Image fxImage = SwingFXUtils.toFXImage(image,null);
            switch (imgIdx) {
                case 0:
                    reloadImage(currentImage, fxImage);
                    break;
                case 1:
                    reloadImage(currentImage2, fxImage);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid image index");
            }
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

//                    Transformation transformation = new DrawLineTransformation(x1,y1,x2,y2,Color.RED);
                    Transformation transformation = new GradientTransformation(x1,y1,x2,y2,Color.OLIVE, Color.BLUE);
                    transformationManagerView.addTransformation(transformation);
                    nextAction = null;
                };
            };
        });
        return button;
    }

    Popup getPopup(){
        Popup popup = new Popup();
        popup.getContent().add(rgbChooserView.getPane());
        popup.setX(300);
        popup.setY(200);
//        popup.getContent().addAll(new Circle(25, 25, 50, Color.AQUAMARINE));


        return popup;
    }

    @Override
    public void start(Stage primaryStage) {


        GridPane root = new GridPane();
        root.setHgap(3.0);
        root.setVgap(3.0);
        currentImage = getCurrentImageView();
        currentImage2 = getCurrentImageView();
        rgbChooserView = new RGBChooserView(currentImage);
        TransformationManager transformationManager = new TransformationManager(currentImage.getWritableImage());
        transformationManagerView = new TransformationManagerView(transformationManager,currentImage);

        currentImage.setImage(transformationManager.getImage());

        // History
        root.add(transformationManagerView,0,0);
        root.setColumnSpan(transformationManagerView, 10);

        // Load
        root.add(getLoadButton(primaryStage,0),0,1);
        root.add(getLoadButton(primaryStage, 1),1,1);
        root.add(new PixelPickerControlPane(currentImage),2,1);

        // Filters
        root.add(getDrawCircleAtButton(),0,2);
        root.add(getGradientMenu(),1,2);
        root.add(getSaveButton(primaryStage),3,2);
        root.add(getDarkenButton(),4,2);

        // Color pane
        Node rgbChooser = rgbChooserView.getPane();
        root.add(rgbChooser,0,3);
        root.setColumnSpan(rgbChooser, 5);

        // Images
        root.add(currentImage,0,4, 5,5);
        root.add(currentImage2,5,4,5,5);
        Scene scene = new Scene(root, 1000, 1000);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
//        getPopup().show(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }
}