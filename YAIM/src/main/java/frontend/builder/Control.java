package frontend.builder;

import javafx.scene.layout.GridPane;

import java.util.List;

public interface Control {
    Integer getOrder();

    GridPane getPane();

    List<Number> getValues();
}
