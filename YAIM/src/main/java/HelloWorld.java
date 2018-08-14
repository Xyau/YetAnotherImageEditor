import backend.ImageUtils;
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
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;

public class HelloWorld extends Application {

    private ImageView currentImage;
    private ImageView workingCopy;


    private ImageView getCurrentImageView(){
        ImageView iv1 = new ImageView();
        Image image = new Image("noImage.jpg");
        iv1.setLayoutX(20);
        iv1.setLayoutY(70);
        iv1.setImage(image);
        return iv1;
    }

    private Button getSaveButton(final Stage stage){
        Button btn = new Button();
        btn.setLayoutX(20);
        btn.setLayoutY(20);
        btn.setText("Load Image");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File file = fileChooser.showOpenDialog(stage);
            }
        });
        return btn;
    }

    private void reloadCurrentImage(Image image){
        currentImage.setImage(image);
    }

    private Button getLoadButton(final Stage stage){
        Button btn = new Button();
        btn.setLayoutX(120);
        btn.setLayoutY(20);
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

    @Override
    public void start(Stage primaryStage) {


        Pane root = new Pane();
//        root.setBackground(new Background(new BackgroundFill(Paint.valueOf("red"),CornerRadii.EMPTY,Insets.EMPTY)));
        currentImage = getCurrentImageView();
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