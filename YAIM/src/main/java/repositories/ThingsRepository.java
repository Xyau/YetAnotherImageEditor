package repositories;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.image.DenormalizedImage;
import backend.transformators.Transformation;
import backend.utils.FileUtils;
import backend.utils.Utils;
import frontend.EventManageableImageView;
import backend.FocusablePane;
import backend.utils.ImageUtils;
import backend.Pixel;
import frontend.*;
import frontend.builder.MultiSliderGridPaneBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import transformations.helpers.ActiveBorderHelperTransformation;
import transformations.normal.DrawLineTransformation;
import transformations.normal.LineHoughTransformation;
import transformations.normal.borders.ActiveBorderTransformation;
import transformations.normal.common.NoChangeTransformation;

import javax.rmi.CORBA.Util;
import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class ThingsRepository {

    private static ThingsRepository repository = new ThingsRepository();

    public static FileChooser.ExtensionFilter EXTENSION_FILTER = new FileChooser
            .ExtensionFilter("The format so save as","png","raw","jpg");

    public static Border NO_BORDER = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.EMPTY));
    public static Border THICK_BORDER = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

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



    public static MenuItem getVideoGridPane(Window window, TransformationManagerView transformationManagerView,
                                            EventManageableImageView eventManageableImageView){
        MenuItem menuItem = new MenuItem("Active Borders...");

        menuItem.setOnAction( x -> {
            Set<DenormalizedColorPixel> internal = new HashSet<>();
            Set<DenormalizedColorPixel> external = new HashSet<>();
            AtomicReference<DenormalizedColor> objAvgColor = new AtomicReference<>();
            AtomicReference<DenormalizedColor> bckgAvgColor = new AtomicReference<>();
            List<WritableImage> images = FileUtils.loadMultipleFiles(window);
            DenormalizedImage gamma = new DenormalizedImage(images.get(0));
            transformationManagerView.setInitialImage(images.get(0));
            MultiSliderGridPaneBuilder builder = new MultiSliderGridPaneBuilder((list) ->
                    new ActiveBorderHelperTransformation(images, list.get(0).intValue(),
                            list.get(1).intValue(),internal,external,gamma,objAvgColor.get(),
                            bckgAvgColor.get(),list.get(2).doubleValue()),transformationManagerView);

            builder.addSlider("Frame Number",0.0,images.size()*1.0-1,1.0);
            builder.addSlider("Iterations",0.0,50.0,1.0);
            builder.addSlider("Threshold",0.0,1.0,0.01);

            Button resetButton = new Button("Reset");
            resetButton.setOnMouseClicked(y ->{
                System.out.println("clicking");
                AtomicReference<Pixel> p1 = new AtomicReference<>();
                AtomicReference<Pixel> p2 = new AtomicReference<>();
                eventManageableImageView.addActiveEventToQueue( event ->{
                    p1.set(Utils.getPixelFromMouseEvent(event));
                });
                eventManageableImageView.addActiveEventToQueue( event ->{
                    p2.set(Utils.getPixelFromMouseEvent(event));
                });
                eventManageableImageView.setWhenQueueFinished(() -> {
                    System.out.println("reseting");
                    internal.clear();
                    internal.addAll(ActiveBorderHelperTransformation.getIn(p1.get().getX(), p1.get().getY()
                            ,p2.get().getX(), p2.get().getY(), new DenormalizedImage(images.get(0))));
                    external.clear();
                    external.addAll(ActiveBorderHelperTransformation.getOut(p1.get().getX(), p1.get().getY()
                            ,p2.get().getX(), p2.get().getY(), new DenormalizedImage(images.get(0))));

                    objAvgColor.set(ActiveBorderHelperTransformation.getAverageInSquare(new DenormalizedImage(images.get(0)),
                            p1.get().getX(), p1.get().getY() ,p2.get().getX(), p2.get().getY()));

                    bckgAvgColor.set(ActiveBorderHelperTransformation.getBackgroundAvg( new DenormalizedImage(images.get(0)),
                            p1.get().getX(), p1.get().getY() ,p2.get().getX(), p2.get().getY()));

                    ActiveBorderHelperTransformation.setGamma(p1.get().getX(), p1.get().getY()
                            ,p2.get().getX(), p2.get().getY(),gamma);


                    System.out.println("YOU MADE IT");
                });
            });

            GridPane gridPane = builder.build();
            gridPane.add(resetButton,2,3);
            StagesRepository.getStage("Video", gridPane).show();
        });

        return menuItem;
    }
}
