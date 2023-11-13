package view;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import model.Forecast;

public class ForecastDayPanel extends VBox {
    private Forecast forecast;

    public ForecastDayPanel(Forecast forecast) {
        this.forecast = forecast;
    }

}
