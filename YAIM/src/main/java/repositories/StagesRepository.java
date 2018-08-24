package repositories;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageBuilder;

import java.util.HashMap;
import java.util.Map;

public class StagesRepository {
    private static Map<String, Stage> stages = new HashMap<>();

    public static Stage getStage(String title, GridPane gridPane) {
        if (stages.containsKey(title)) {
            return stages.get(title);
        } else {
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(gridPane));
            stage.setX(Screen.getPrimary().getBounds().getMaxX() - 500);
            stages.put(title, stage);
            return stage;
        }
    }
}
