package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import model.Forecast;
import model.Local;

import java.util.ArrayList;
import java.util.List;

public class ForecastView extends BorderPane {

    private Button buttonFilter;
    private Button buttonForecast;
    private ObservableList<Local> listLocals;
    private ComboBox<Local> comboLocals;
    private GridPane gridForecasts;
    private ToggleGroup groupRegion;

    public ForecastView() {

        doLayout();
    }


    private void doLayout() {
        buttonFilter = new Button("Filtrar");
        buttonForecast = new Button("Ver Previsão");
        listLocals = FXCollections.observableArrayList();

        comboLocals = new ComboBox<>(listLocals);

        groupRegion = new ToggleGroup();

        RadioButton rb1 = new RadioButton("Todos");
        rb1.setToggleGroup(groupRegion);
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton("Continente");
        rb2.setToggleGroup(groupRegion);

        RadioButton rb3 = new RadioButton("Açores");
        rb3.setToggleGroup(groupRegion);

        RadioButton rb4 = new RadioButton("Madeira");
        rb4.setToggleGroup(groupRegion);

        // Top bar
        VBox barraTopo = new VBox(20);
        barraTopo.setPadding(new Insets(20,20,20,20));

        HBox barraFiltro = new HBox(20);
        barraFiltro.setAlignment(Pos.CENTER);

        HBox barraLocal = new HBox(20);
        barraLocal.setAlignment(Pos.CENTER);

        barraLocal.getChildren().addAll(comboLocals, buttonForecast);
        barraFiltro.getChildren().addAll(rb1, rb2, rb3, rb4, buttonFilter);
        barraTopo.getChildren().addAll(barraLocal, barraFiltro);

        setTop(barraTopo);

        // Main grid to display forecast
        gridForecasts = new GridPane();
        //Setting size for the pane
        gridForecasts.setMinSize(600, 400);
        // Setting columns size in percent
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(20);
        gridForecasts.getColumnConstraints().add(column);
        gridForecasts.getColumnConstraints().add(column);
        gridForecasts.getColumnConstraints().add(column);
        gridForecasts.getColumnConstraints().add(column);
        gridForecasts.getColumnConstraints().add(column);
        //Setting the padding
        gridForecasts.setPadding(new Insets(10,10,10,10));
        //Setting the vertical and horizontal gaps between the columns
        gridForecasts.setVgap(5);
        gridForecasts.setHgap(5);

        List<Forecast> lista = new ArrayList<>();
        lista.add(null);
        lista.add(null);
        lista.add(null);
        lista.add(null);
        lista.add(null);

        atualizaPrevisao();

        setCenter(gridForecasts);

        buttonFilter.setOnAction(event -> {
            atualizaLocais();
        });

        buttonForecast.setOnAction(event -> {
            atualizaPrevisao();
        });
    }

    public void atualizaPrevisao() {
        // Obter local selecionado da combobox (Local.getLocalGlobalId())
        // -> Local getSelectedLocal()
        // e obter da API a previsao metereologica para este local.
        // De seguida popular a gridForecasts com os paineis respetivos
    }

    public void atualizaLocais() {
        // Obter lista de locais da API e refrescar modelo da combobox (listLocais)
    }

    public String getRegiaoFiltro() {
        // Retorna:
        // - "Todos"
        // - "Continente"
        // - "Açores"
        // - "Madeira"
        RadioButton selected = (RadioButton) groupRegion.getSelectedToggle();
        return selected.getText();
    }

    public Local getSelectedLocal() {
        return comboLocals.getSelectionModel().getSelectedItem();
    }

    public void showError(String title, String message) {
        System.err.println(String.format("%s - %s", title, message));
    }
}