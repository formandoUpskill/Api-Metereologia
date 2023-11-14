import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ForecastView;

public class MainView extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ForecastView root = new ForecastView();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
