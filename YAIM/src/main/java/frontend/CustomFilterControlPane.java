package frontend;

import backend.Filter;
import backend.utils.Utils;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import repositories.FiltersRepository;
import transformations.normal.filters.RotatedFilter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CustomFilterControlPane extends GridPane {
    TransformationManagerView transformationManagerView;

    public CustomFilterControlPane(TransformationManagerView transformationManagerView){
        this.transformationManagerView = transformationManagerView;
        AtomicInteger cuarterRotations = new AtomicInteger(0);
        AtomicReference<Filter> filter = new AtomicReference<>(new Filter(FiltersRepository.KIRSH,"Kirsh"));

        Text filterVisualization = new Text();
        filterVisualization.setText("Here will appear the filter you chose");

        SliderControl rotationSlider = new SliderControl("Rotation",0.0,4.0,1.0,(old,curr)->{
            if(old.intValue() != curr.intValue()){
                cuarterRotations.set(curr.intValue());
                transformationManagerView.preview(new RotatedFilter(filter.get(),cuarterRotations.get()));
                filterVisualization.setText(Utils.printFilter(FiltersRepository.getRotatedFilter(filter.get().getFilter(),cuarterRotations.get())));
            }
        });

        ListView<Filter> filterView = new ListView<>();
        filterView.getItems().addAll(new Filter(FiltersRepository.KIRSH, "Kirsh")
                ,new Filter(FiltersRepository.SOBEL_HORIZONTAL, "Sobel")
                ,new Filter(FiltersRepository.PREWITT_HORIZONTAL, "Prewit")
                ,new Filter(FiltersRepository.TRIANGLE, "Triangle")
                ,new Filter(FiltersRepository.HORIZONTAL_ZEROES, "Zeroes")
        );


        filterView.getSelectionModel().selectedItemProperty().addListener((l,old,curr) -> {
            if(old != curr){
                filter.set(curr);
                transformationManagerView.preview(new RotatedFilter(filter.get(),cuarterRotations.get()));
                filterVisualization.setText(Utils.printFilter(FiltersRepository.getRotatedFilter(filter.get().getFilter(),cuarterRotations.get())));
            }
        });
        Button applyButton = new Button("Apply");
        applyButton.setOnMouseClicked((x)->{
            transformationManagerView.addTransformation(new RotatedFilter(filter.get(),cuarterRotations.get()));
        });

        add(rotationSlider,0,0);
        add(filterView,0,1,1,1);
        add(filterVisualization,0,3,1,2);
        add(applyButton,0,5);
    }
}
