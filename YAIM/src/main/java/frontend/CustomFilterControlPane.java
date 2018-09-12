package frontend;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import repositories.FiltersRepository;
import transformations.normal.filters.CustomFilterTransformation;

import java.util.concurrent.atomic.AtomicInteger;

public class CustomFilterControlPane extends GridPane {
    TransformationManagerView transformationManagerView;

    public CustomFilterControlPane(TransformationManagerView transformationManagerView){
        this.transformationManagerView = transformationManagerView;
        AtomicInteger cuarterRotations = new AtomicInteger();
        SliderControl rotationSlider = new SliderControl("Rotation",0.0,4.0,1.0,(old,curr)->{
            if(old.intValue() != curr.intValue()){
                cuarterRotations.set(curr.intValue());
                transformationManagerView.preview(new CustomFilterTransformation(FiltersRepository.KIRSH,cuarterRotations.get()));
            }
        });

        Button applyButton = new Button("Apply");
        applyButton.setOnMouseClicked((x)->{
            transformationManagerView.addTransformation(new CustomFilterTransformation(FiltersRepository.KIRSH,cuarterRotations.get()));
        });

        add(rotationSlider,0,0);
        add(applyButton,0,1);
    }
}
