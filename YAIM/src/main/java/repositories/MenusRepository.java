package repositories;

import backend.ImageUtils;
import frontend.ImageOperationsControl;
import frontend.TransformationManagerView;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import transformations.MedianFilterTransformation;

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
        MenuItem menuItem = new MenuItem("Image Operations");
        ImageOperationsControl imageOperationsControl = new ImageOperationsControl(scene, transformationManagerView);

        menuItem.setOnAction( event -> {
            StagesRepository.getStage("Image Operations",imageOperationsControl).show();
        });

        imageMenu.getItems().add(menuItem);
        return imageMenu;
    }

    public static Menu getFilterMenu( TransformationManagerView transformationManagerView){
        Menu fileMenu = new Menu("Filter");
        fileMenu.getItems().addAll(getMedianFilterMenuItem(transformationManagerView));
        return fileMenu;
    }

    private static MenuItem getMedianFilterMenuItem(TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem("Median Filter 3x3");
        item.setOnAction(event -> {
            transformationManagerView.addTransformation(new MedianFilterTransformation());
        });
        return item;
    }
}
