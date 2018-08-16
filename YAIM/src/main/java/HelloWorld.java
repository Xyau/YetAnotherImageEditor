import backend.ImageUtils;
import backend.Utils;
import com.sun.javafx.tk.Toolkit;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class HelloWorld extends Application {

    private WritableImageView currentImage;

    private RGBChooser rgbChooser;

    private WritableImageView getCurrentImageView(){
        WritableImageView iv1 = new WritableImageView();
        iv1.setLayoutX(40);
        iv1.setLayoutY(100);
        iv1.setWritableImage(ImageUtils.copyImage(new Image("noImage.jpg")));
        iv1.setOnMouseClicked( event -> {
            Color c = iv1.getWritableImage().getPixelReader()
                    .getColor(Utils.toInteger(event.getX()),Utils.toInteger(event.getY()));
            rgbChooser.updateAreasWith(c);
        });
        return iv1;
    }

    private Button getSaveButton(final Stage stage){
        Button btn = new Button();
        btn.setLayoutX(20);
        btn.setLayoutY(50);
        btn.setText("Save Image");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File file = fileChooser.showSaveDialog(stage);
                ImageUtils.writeImage(SwingFXUtils.fromFXImage(currentImage.getWritableImage(),null),file);
            }
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
        btn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File file = fileChooser.showOpenDialog(stage);
                BufferedImage image = ImageUtils.readImage(file.getPath());
                Image fxImage = SwingFXUtils.toFXImage(image,null);
                reloadCurrentImage(fxImage);
            }
        });
        return btn;
    }

    public Button getDrawCircleAtButton(){
        Button button = new Button();
        button.setLayoutX(500);
        button.setLayoutY(20);
        button.setOnMouseClicked( event -> {
            for (int i = 0; i < 50; i++) {
                for (int j = 0; j < 50; j++) {
                    currentImage.getWritableImage().getPixelWriter().setColor(i,j,new Color(0.4,0,0.4,1));
                }
            }
        });
        return button;
    }

    @Override
    public void start(Stage primaryStage) {


        Pane root = new Pane();
        currentImage = getCurrentImageView();
        rgbChooser = new RGBChooser(currentImage);
        root.getChildren().add(getDrawCircleAtButton());
        root.getChildren().add(rgbChooser.getPane());
        root.getChildren().add(getSaveButton(primaryStage));
        root.getChildren().add(currentImage);
        root.getChildren().add(getLoadButton(primaryStage));
        Scene scene = new Scene(root, 1000, 1000);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}