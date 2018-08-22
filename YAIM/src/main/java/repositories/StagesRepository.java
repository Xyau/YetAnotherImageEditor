package repositories;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageBuilder;

public class StagesRepository {
    private static Stage drawLineStage;

    public static Stage getDrawLineStage(GridPane gridPane){
        if(drawLineStage == null){
            drawLineStage = new Stage();
            drawLineStage.setTitle("Draw Line");
            drawLineStage.setAlwaysOnTop(true);
            drawLineStage.setScene(new Scene(gridPane, 200, 200));
            drawLineStage.setX(Screen.getPrimary().getBounds().getMaxX() - 450);
        }
        return drawLineStage;
    }
}
