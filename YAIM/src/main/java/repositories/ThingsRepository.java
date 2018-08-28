package repositories;

import backend.EventManageableImageView;
import backend.FocusablePane;
import backend.Pixel;
import frontend.*;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import transformations.*;

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


    public static Button getDarkenButton(TransformationManagerView transformationManagerView){
        Button button = new Button();
        button.setText("Darken");
        button.setOnMouseClicked( buttonClickEvent -> {
            Transformation transformation = new DarkenTransformation();
            transformationManagerView.addTransformation(transformation);
        });
        return button;
    }

    public static Button getPreviewButton(TransformationManagerView transformationManagerView){
        Button button = new Button();
        button.setText("Preview");
        button.setOnMouseClicked( buttonClickEvent -> {
            transformationManagerView.preview(new NoChangeTransformation());
        });
        return button;
    }
}
