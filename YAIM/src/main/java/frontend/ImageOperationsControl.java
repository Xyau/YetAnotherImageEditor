package frontend;

import backend.utils.ImageUtils;
import backend.transformators.Transformation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import transformations.normal.image.*;

import java.util.function.Function;

public class ImageOperationsControl extends GridPane {
    private ImageLoadControl imageLoadControl;

    private Transformation activeTransformation;

    public ImageOperationsControl(Scene scene, TransformationManagerView transformationManagerView){
        imageLoadControl = new ImageLoadControl(scene);

        Button addButton = getTransformationPreviewButton("Add", AddImageTransformation::new, transformationManagerView);
        Button multiplyButton = getTransformationPreviewButton("Multiply", MultiplyImageTransformation::new, transformationManagerView);
        Button substractButton = getTransformationPreviewButton("Substract", MinusImageTransformation::new, transformationManagerView);
        Button distanceButton = getTransformationPreviewButton("Modulus", DistanceImageTransformation::new, transformationManagerView);
        Button averageButton = getTransformationPreviewButton("Average", AverageImageTransformation::new, transformationManagerView);
        Button modulusButton = getTransformationPreviewButton("Modulus", ModulusImageTransformation::new, transformationManagerView);
        Button highlightButton = getTransformationPreviewButton("Highlight", HighlightRedImageTransformation::new, transformationManagerView);
        Button applyButton = new Button("Apply");
        Button getFromPreview = new Button("Get from preview");

        getFromPreview.setOnMouseClicked((x)->{
            imageLoadControl.setImage(ImageUtils.copyImage(transformationManagerView.getPreview()));
        });

        applyButton.setOnMouseClicked((x) -> {
            transformationManagerView.addTransformation(activeTransformation);
        });

        add(substractButton,0,0);
        add(addButton,1,0);
        add(multiplyButton,2,0);
        add(distanceButton,3,0);
        add(averageButton,4,0);
        add(modulusButton,5,0);
        add(highlightButton,6,0);
        add(applyButton,0,1);
        add(getFromPreview,1,1,2,1);
        add(imageLoadControl,0,2,5,2);
    }

    private Button getTransformationPreviewButton(String name, Function<Image,Transformation> transformationBuilder, TransformationManagerView transformationManagerView){
        Button button = new Button(name);

        button.setOnMouseClicked((x) -> {
            if(imageLoadControl.getImage().isPresent()){
                activeTransformation = transformationBuilder.apply(imageLoadControl.getImage().get());
                transformationManagerView.preview(activeTransformation);
            }
        });
        return button;
    }
}
