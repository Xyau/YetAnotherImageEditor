import backend.*;
import frontend.RGBChooserView;
import frontend.TransformationManagerView;
import frontend.WritableImageView;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import repositories.ImagesRepository;
import repositories.MenusRepository;
import repositories.StagesRepository;
import repositories.ThingsRepository;
import transformations.*;

import java.util.concurrent.atomic.AtomicReference;

public class HelloWorld extends Application {

    private EventManageableImageView currentImage;
    private EventManageableImageView previewImage;

    private RGBChooserView rgbChooserView;

    private TransformationManagerView transformationManagerView;
    private MenuBar menuBar;

    private EventManageableImageView setupCurrentImageView(){
        EventManageableImageView iv1 = new EventManageableImageView();
        iv1.setWritableImage(ImageUtils.copyImage(ImagesRepository.NO_IMAGE));
        iv1.addPasiveEvent(event -> {
            System.out.println("Click event at: (" + event.getX() +
                    "," + event.getY() + ")");
        });

        iv1.addPasiveEvent( event -> {
            Color c = transformationManagerView.getImage().getPixelReader()
                    .getColor(Utils.toInteger(event.getX()),Utils.toInteger(event.getY()));
            rgbChooserView.updateAreasWith(c);
        });
        return iv1;
    }

    public Button getDrawCircleAtButton(){
        Button button = new Button();
        button.setText("Draw circle");
        button.setOnMouseClicked( buttonClickEvent -> {
            AtomicReference<Pixel> pixel = new AtomicReference<>();
            currentImage.addActiveEventToQueue( mouseEvent -> {
                pixel.set(Utils.getPixelFromMouseEvent(mouseEvent));
            });

            currentImage.setWhenQueueFinished( ()->{
                Transformation transformation = new DrawSquareTransformation(pixel.get().getX(),
                        pixel.get().getY(),50,50,Color.RED);
                transformationManagerView.addTransformation(transformation);
            });
        });
        return button;
    }

    public Button getDrawLineAtButton(){
        Button button = new Button();
        button.setText("Draw Line");
        //Get the auxiliary panel from the things repository
        FocusablePane focusablePane = ThingsRepository.getDrawLineAtPanel(currentImage,transformationManagerView);
        //When the button is clicked, a new window appears with the auxiliary controls
        button.setOnMouseClicked( buttonClickEvent -> {
            //Get the window where the auxiliary panel will be put
            Stage stage = StagesRepository.getStage("Draw line",focusablePane);

            //Show the window
            stage.show();
            //Trigger the focus checks, so it captures the mouse input events
            focusablePane.recievedFocus(currentImage);
        });
        return button;
    }

    public Button getGradientMenu(){
        Button button = new Button();
        button.setLayoutX(600);
        button.setLayoutY(20);
        button.setText("Draw gradient");
        button.setOnMouseClicked( buttonClickEvent -> {
            AtomicReference<Pixel> first = new AtomicReference<>();
            AtomicReference<Pixel> second = new AtomicReference<>();

            //First get the first pixel (This is if you dont want the aux panel)
            currentImage.addActiveEventToQueue( mouseEvent -> {
                first.set(Utils.getPixelFromMouseEvent(mouseEvent));
            },true);

            //Get the second pixel
            currentImage.addActiveEventToQueue( mouseEvent -> {
                second.set(Utils.getPixelFromMouseEvent(mouseEvent));
            },true);

            //Set what to do when both of the active events are done
            currentImage.setWhenQueueFinished( () ->{
                Transformation transformation = new GradientTransformation(first.get().getX()
                        ,first.get().getY(),second.get().getX(),second.get().getY(),Color.OLIVE, Color.BLUE);
                transformationManagerView.addTransformation(transformation);
            });
        });
        return button;
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        root.setHgap(3.0);
        root.setVgap(3.0);
        currentImage = setupCurrentImageView();
        ScrollPane currentScrollPane = new ScrollPane();
        currentScrollPane.setContent(currentImage);

        previewImage = setupCurrentImageView();
        ScrollPane previewScrollPane = new ScrollPane();
        previewScrollPane.setContent(previewImage);

        rgbChooserView = new RGBChooserView(currentImage);
        TransformationManager transformationManager = new TransformationManager(currentImage.getWritableImage());
        transformationManagerView = new TransformationManagerView(transformationManager,currentImage,previewImage);

        currentImage.setImage(transformationManager.getImage());
        Scene scene = new Scene(root, 1200, 700);

        // Row 0: MenuBar
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(MenusRepository.getFileMenu(primaryStage,transformationManagerView),
                                MenusRepository.getShapesMenu(transformationManagerView),
                                MenusRepository.getColorsMenu(transformationManagerView),
                                MenusRepository.getImageMenu(scene,transformationManagerView),
                                MenusRepository.getFilterMenu(transformationManagerView),
                                MenusRepository.getBordersMenu(transformationManagerView),
                                MenusRepository.getNoiseMenu(transformationManagerView));
        root.add(menuBar,0,0,10,1);


        // Row 1: History
        root.add(transformationManagerView,1,1,10,1);
        root.add(ThingsRepository.getDeleteButton(transformationManagerView),0,1);
        root.setColumnSpan(transformationManagerView, 10);

        // Row 3: Filters
        root.add(getDrawCircleAtButton(),0,3);
        root.add(getGradientMenu(),1,3);
        root.add(getDrawLineAtButton(),2,3);

        // Row 4: Color pane
        root.add(rgbChooserView,0,4,2,1);
        root.add(ThingsRepository.getPreviewButton(transformationManagerView),4,4);

        // Row 5: Images
        HBox hBox = new HBox();
        hBox.getChildren().addAll(currentScrollPane,previewScrollPane);
        root.add(hBox,0,5,15,5);

        primaryStage.setTitle("Yet Another Image Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}