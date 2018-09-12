package frontend;

import backend.transformators.Transformation;
import backend.utils.Utils;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class SingleSliderGridPane extends GridPane {
    private SliderControl sliderControl;
    private Button applyButton;
    private AtomicReference<Number> value;

    public SingleSliderGridPane(String sliderName, Double min, Double max, Double increment,
                                Function<Number,Transformation> transformationBuilder,
                                TransformationManagerView transformationManagerView) {
        value = new AtomicReference<>(min);
        this.sliderControl = new SliderControl(sliderName,min,max,increment,(old,curr)->{
            if(!Utils.roundToRearestFraction(old.doubleValue(), increment).equals(Utils.roundToRearestFraction(curr.doubleValue(), increment))){
                value.set(curr);
                transformationManagerView.preview(transformationBuilder.apply(value.get()));
            }
        });

        applyButton = new Button("Apply");
        applyButton.setOnMouseClicked(e -> {
            transformationManagerView.addTransformation(transformationBuilder.apply(value.get()));
        });

        add(sliderControl,0,0);
        add(applyButton,0,1);
    }
}
