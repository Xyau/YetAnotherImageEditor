package backend.utils;

import frontend.TextAreaControlPane;
import frontend.TransformationManagerView;
import frontend.builder.MultiSliderGridPaneBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.omg.CORBA.PUBLIC_MEMBER;
import repositories.StagesRepository;
import transformations.helpers.ActiveBorderHelperTransformation;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class FileUtils {
    public static GridPane getRawImageLoadPane(File file, TransformationManagerView transformationManagerView){
        GridPane gridPane = new GridPane();

        TextAreaControlPane heightPane = new TextAreaControlPane("Height:");
        TextAreaControlPane widthPane = new TextAreaControlPane("Width:");
        Text info = new Text("Please input the width and height of the RAW image");
        Button apply = new Button("Apply");
        apply.setOnMouseClicked((x)->{
            try {
                Integer height = Integer.parseInt(heightPane.getText());
                Integer width = Integer.parseInt(widthPane.getText());

                transformationManagerView.setInitialImage(ImageUtils.readImage(file,height,width));
            } catch (NumberFormatException ignored){}
        });
        gridPane.add(info,0,0,10,1);
        gridPane.add(heightPane,0,1,3,1);
        gridPane.add(widthPane,0,2,3,1);
        gridPane.add(apply,0,3,1,1);
        return gridPane;
    }

    public static List<WritableImage> loadMultipleFiles(Window stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource Files");
        List<File> file = fileChooser.showOpenMultipleDialog(stage);
        List<WritableImage> images = file.stream().map(ImageUtils::readImage).collect(Collectors.toList());
        return images;
    }
}
