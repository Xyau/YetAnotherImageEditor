package frontend;

import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import backend.transformators.Transformation;
import javafx.scene.layout.GridPane;

public class FrontendUtils {
    public static MenuItem getMenuItemByTranformation(String name, Transformation transformation, TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem(name);
        item.setOnAction(event -> transformationManagerView.addTransformation(transformation));
        return item;
    }

}
