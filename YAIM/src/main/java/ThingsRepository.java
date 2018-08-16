import javafx.scene.layout.Pane;

public class ThingsRepository {

    private static ThingsRepository repository = new ThingsRepository();



    public static ThingsRepository getInstance() {
        return repository;
    }
}
