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
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import repositories.ImagesRepository;
import repositories.StagesRepository;
import repositories.ThingsRepository;
import transformations.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

public class HelloWorld extends Application {

    private EventManageableImageView currentImage;
    private EventManageableImageView previewImage;

    private RGBChooserView rgbChooserView;

    private TransformationManagerView transformationManagerView;
    private MenuBar menuBar;

    private EventManageableImageView setupCurrentImageView(){
        EventManageableImageView iv1 = new EventManageableImageView();
        iv1.setLayoutX(40);
        iv1.setLayoutY(100);
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

    private Button getSaveButton(final Stage stage){
        Button btn = new Button();
        btn.setLayoutX(20);
        btn.setLayoutY(50);
        btn.setText("Save Image");
        btn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.setSelectedExtensionFilter(ThingsRepository.EXTENSION_FILTER);
            File file = fileChooser.showSaveDialog(stage);
            ImageUtils.writeImage(SwingFXUtils.fromFXImage(transformationManagerView.getImage(),null),file);
        });
        return btn;
    }

    private void reloadImage(WritableImageView writableImageView, Image image){
        writableImageView.setWritableImage(ImageUtils.copyImage(image));
    }

    private Button getLoadButton(final Stage stage, int imgIdx){
        Button btn = new Button();
        btn.setText("Load Image " + (imgIdx + 1));
        btn.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(stage);
            WritableImage image = ImageUtils.readImage(file);

            transformationManagerView.setInitialImage(image);
            currentImage.setImage(image);
        });
        return btn;
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
            Stage stage = StagesRepository.getDrawLineStage(focusablePane);
            //Show the window
            stage.show();
            //Trigger the focus checks, so it captures the mouse input events
            focusablePane.recievedFocus(currentImage);
        });
        return button;
    }

    public Button getNegativeButton(){
        Button button = new Button();
        button.setText("Negative");
        button.setOnMouseClicked( buttonClickEvent -> {
            Transformation transformation = new NegativeTransformation();
            transformationManagerView.addTransformation(transformation);
        });
        return button;
    }


    public Button getBinaryButton(){
        Button button = new Button();
        button.setText("Binary");
        button.setOnMouseClicked( buttonClickEvent -> {
            Transformation transformation = new BinaryTransformation(0.5);
            transformationManagerView.addTransformation(transformation);
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
//                    Transformation transformation = new DrawLineTransformation(x1,y1,x2,y2,Color.RED);
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
        previewImage = setupCurrentImageView();
        rgbChooserView = new RGBChooserView(currentImage);
        TransformationManager transformationManager = new TransformationManager(currentImage.getWritableImage());
        transformationManagerView = new TransformationManagerView(transformationManager,currentImage,previewImage);

        currentImage.setImage(transformationManager.getImage());

        // Row 1: History
        root.add(transformationManagerView,0,1);
        root.setColumnSpan(transformationManagerView, 10);

        // Row 2: Load
        root.add(getLoadButton(primaryStage,0),0,2);
        root.add(getLoadButton(primaryStage, 1),1,2);

        // Row 3: Filters
        root.add(getDrawCircleAtButton(),0,3);
        root.add(getGradientMenu(),1,3);
        root.add(getSaveButton(primaryStage),3,3);
        root.add(getNegativeButton(),4,3);
        root.add(ThingsRepository.getDarkenButton(transformationManagerView),5,3);
        root.add(getDrawLineAtButton(),6,3);
        root.add(getNegativeButton(),7,3);
        root.add(getBinaryButton(),9,3);

        // Row 4: Color pane
        Node rgbChooser = rgbChooserView.getPane();
        root.add(rgbChooser,0,4);
        root.setColumnSpan(rgbChooser, 5);

        // Row 5: Images
        root.add(currentImage,0,5, 5,5);
        root.add(previewImage,5,5,5,5);

        Scene scene = new Scene(root, 1000, 1000);
        menuBar = new MenuBar();
        menuBar.getMenus().add(ThingsRepository.getImageMenu(scene,transformationManagerView));
        root.add(menuBar,0,0);
//        ((VBox)     scene.getRoot()).getChildren().addAll(menuBar);
        primaryStage.setTitle("Yet Another Image Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
//        getPopup().show(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }
}