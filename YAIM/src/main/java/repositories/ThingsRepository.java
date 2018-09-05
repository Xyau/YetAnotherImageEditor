package repositories;

import backend.EventManageableImageView;
import backend.FocusablePane;
import backend.ImageUtils;
import backend.Pixel;
import frontend.*;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import transformations.*;
import transformations.colors.DarkenTransformation;
import transformations.common.NoChangeTransformation;

import java.io.File;

public class ThingsRepository {

    private static ThingsRepository repository = new ThingsRepository();

    public static FileChooser.ExtensionFilter EXTENSION_FILTER = new FileChooser
            .ExtensionFilter("The format so save as","png","raw","jpg");

    public static ThingsRepository getInstance() {
        return repository;
    }


    public static FocusablePane getDrawLineAtPanel(EventManageableImageView imageView, TransformationManagerView transformationManagerView){
        PixelPickerControlPane firstPixelPicker = new PixelPickerControlPane(imageView, "First Pixel");
        PixelPickerControlPane secondPixelPicker = new PixelPickerControlPane(imageView, "Second Pixel");

        //This event is triggered whenever the button is clicked
        FocusablePane drawLinePanel = new FocusablePane( img -> {
            img.addActiveEventToQueue(firstPixelPicker.getEventConsumer(),true);
            img.addActiveEventToQueue(secondPixelPicker.getEventConsumer(),true);

            img.setWhenQueueFinished(() -> {
                Pixel first = firstPixelPicker.getChosenPixel().get();
                Pixel second = secondPixelPicker.getChosenPixel().get();

                transformationManagerView.addTransformation(
                        new DrawLineTransformation(first.getX(),first.getY(),second.getX(),second.getY(),Color.RED));
            });
            System.out.println("Draw line at panel recieved focus, added 2 things to the active event queue");
        });

        drawLinePanel.add(firstPixelPicker,0,0);
        drawLinePanel.add(secondPixelPicker,0,1);
        return drawLinePanel;
    }

    public static Button getPreviewButton(TransformationManagerView transformationManagerView){
        Button button = new Button("Preview");
        button.setOnMouseClicked( buttonClickEvent -> transformationManagerView.preview(new NoChangeTransformation()));
        return button;
    }

    public static Button getDeleteButton(TransformationManagerView transformationManagerView){
        Button button = new Button("Delete");
        button.setOnMouseClicked( buttonClickEvent -> transformationManagerView.deleteUnusedTransformations());
        return button;
    }

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
}
