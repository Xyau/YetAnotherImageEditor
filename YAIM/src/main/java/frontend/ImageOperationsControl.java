package frontend;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import transformations.AddImageTransformation;
import transformations.MultiplyImageTransformation;
import transformations.SubstractImageTransformation;
import transformations.Transformation;

public class ImageOperationsControl extends GridPane {
    private ImageLoadControl imageLoadControl;
    private Button addButton;
    private Button multiplyButton;
    private Button substractButton;

    private Button applyButton;

    private Transformation activeTransformation;

    public ImageOperationsControl(Scene scene, TransformationManagerView transformationManagerView){
        imageLoadControl = new ImageLoadControl(scene);

        addButton = new Button("Add");
        multiplyButton = new Button("Multiply");
        substractButton = new Button("Substract");
        applyButton = new Button("Apply");

        addButton.setOnMouseClicked((x) -> {
            if(imageLoadControl.getImage().isPresent()){
                activeTransformation = new AddImageTransformation(imageLoadControl.getImage().get());
                transformationManagerView.preview(activeTransformation);
            }
        });

        substractButton.setOnMouseClicked((x) -> {
            if(imageLoadControl.getImage().isPresent()){
                activeTransformation = new SubstractImageTransformation(imageLoadControl.getImage().get());
                transformationManagerView.preview(activeTransformation);
            }
        });

        multiplyButton.setOnMouseClicked((x) -> {
            if(imageLoadControl.getImage().isPresent()){
                activeTransformation = new MultiplyImageTransformation(imageLoadControl.getImage().get());
                transformationManagerView.preview(activeTransformation);
            }
        });

        applyButton.setOnMouseClicked((x) -> {
            transformationManagerView.addTransformation(activeTransformation);
        });

        add(substractButton,0,0);
        add(addButton,1,0);
        add(multiplyButton,2,0);
        add(applyButton,3,0);
        add(imageLoadControl,0,1,5,1);
    }
}
