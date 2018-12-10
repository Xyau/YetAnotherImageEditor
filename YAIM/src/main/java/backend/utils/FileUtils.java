package backend.utils;

import backend.cascading_features.Classifier;
import backend.cascading_features.Feature;
import backend.cascading_features.Rectangle;
import backend.cascading_features.Stage;
import frontend.TextAreaControlPane;
import frontend.TransformationManagerView;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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

    public static List<Feature> loadFeatures(){
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("./YAIM/src/main/resources/haarCascading/stage_rects.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<Feature> features = new ArrayList<>();
        while (scanner.hasNextInt()){
            Integer size = scanner.nextInt();
            List<Rectangle> rectangles = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                rectangles.add(new Rectangle(scanner.nextInt(),
                        scanner.nextInt(),scanner.nextInt(),
                        scanner.nextInt(),scanner.nextDouble()));
            }
            features.add(new Feature(rectangles,features.size()));
        }
        return features;
    }

    public static List<Stage> loadStages(){
        Scanner scannerStages = null;
        Scanner scannerClassifiers = null;
        List<Feature> features = loadFeatures();
        try {
            scannerClassifiers = new Scanner(new File("./YAIM/src/main/resources/haarCascading/stage_thresholds.txt"));
            scannerStages = new Scanner(new File("./YAIM/src/main/resources/haarCascading/stage_sizes.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Stage> stages = new ArrayList<>();
        while (scannerClassifiers.hasNextInt()){
            Integer size = scannerClassifiers.nextInt();
            List<Classifier> classifiers = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                classifiers.add(new Classifier(features.get(scannerClassifiers.nextInt()),scannerClassifiers.nextDouble(),scannerClassifiers.nextDouble(),scannerClassifiers.nextDouble()));
            }
            scannerStages.nextInt();
            stages.add(new Stage(classifiers, scannerStages.nextDouble()));
        }
        return stages;
    }

}
