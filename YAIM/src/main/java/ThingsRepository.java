import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class ThingsRepository {

    private static ThingsRepository repository = new ThingsRepository();

    public static FileChooser.ExtensionFilter EXTENSION_FILTER = new FileChooser
            .ExtensionFilter("The format so save as","png","raw","jpg");

    public static ThingsRepository getInstance() {
        return repository;
    }
}
