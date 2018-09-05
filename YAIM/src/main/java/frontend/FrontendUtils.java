package frontend;

import javafx.scene.control.MenuItem;
import transformations.Transformation;

public class FrontendUtils {
    public static MenuItem getMenuItemByTranformation(String name, Transformation transformation, TransformationManagerView transformationManagerView){
        MenuItem item = new MenuItem(name);
        item.setOnAction(event -> transformationManagerView.addTransformation(transformation));
        return item;
    }

}
