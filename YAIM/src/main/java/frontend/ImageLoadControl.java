package frontend;

import backend.ImageUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import repositories.ImagesRepository;

import java.io.File;
import java.util.Optional;

public class ImageLoadControl extends GridPane {
    private TextAreaControlPane textAreaControlPane;
    private ImageView imageView;
    private Button load;

    public ImageLoadControl(Scene scene) {
        textAreaControlPane = new TextAreaControlPane("Path: ",4);
        imageView = new ImageView();
        imageView.setImage(ImagesRepository.NO_IMAGE);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
        load = new Button("Load Image");

        load.setOnMouseClicked( event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(scene.getWindow());

            textAreaControlPane.setText(file.getPath());
            imageView.setImage(ImageUtils.readImage(file));
        });

        add(textAreaControlPane,0,0,2,1);
        add(load,2,0);
        add(imageView,0,1,5,1);
    }

    public Optional<Image> getImage(){
        if(imageView.getImage() != null){
            return Optional.of(imageView.getImage());
        }
        else return Optional.empty();
    }
}
