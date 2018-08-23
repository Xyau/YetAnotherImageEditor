package repositories;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageBuilder;

public class StagesRepository {
    private static Stage drawLineStage;
    private static Stage imageOperationsStage;

    public static Stage getDrawLineStage(GridPane gridPane){
        if(drawLineStage == null){
            drawLineStage = new Stage();
            drawLineStage.setTitle("Draw Line");
            drawLineStage.setAlwaysOnTop(true);
            drawLineStage.setScene(new Scene(gridPane));
            drawLineStage.setX(Screen.getPrimary().getBounds().getMaxX() - 450);
        }
        return drawLineStage;
    }

    public static Stage getImageOperationsStage(GridPane gridPane){
        if(imageOperationsStage == null){
            imageOperationsStage = new Stage();
            imageOperationsStage.setTitle("Choose operation");
            imageOperationsStage.setAlwaysOnTop(true);
            imageOperationsStage.setScene(new Scene(gridPane,500,500));
            imageOperationsStage.setX(Screen.getPrimary().getBounds().getMaxX() - 450);
        }
        return imageOperationsStage;
    }
}
