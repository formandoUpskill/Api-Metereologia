package view;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.util.StringConverter;
import model.Forecast;
import model.Local;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ForecastView extends BorderPane {

    private Button buttonFilter;
    private Button buttonForecast;
    private ObservableList<Local> listLocals;
    private ComboBox<Local> comboLocals;
    private GridPane gridForecasts;
    private ToggleGroup groupRegion;


    enum OpRegiao {
        Todos(0),Continente(1), Madeira(2), Acores(3);

        private final int regiao;
        OpRegiao(int value)
        {
            regiao=value;
        }

        public int getOption() {
            return regiao;
        }
    }

    public ForecastView() {

        doLayout();
    }


    private void doLayout() {
        buttonFilter = new Button("Filtrar");
        buttonForecast = new Button("Ver Previsão");
        listLocals = FXCollections.observableArrayList();

        comboLocals = new ComboBox<>(listLocals);

        comboLocals.setConverter(new StringConverter<Local>() {
            @Override
            public String toString(Local local) {
                return (local != null) ? local.getLocal() : null;
            }

            @Override
            public Local fromString(String string) {
                // Convert the string back to your object if needed
                return null;
            }
        });

        groupRegion = new ToggleGroup();


        RadioButton rb1 = new RadioButton(OpRegiao.Todos.toString());
        rb1.setToggleGroup(groupRegion);
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton(OpRegiao.Continente.toString());
        rb2.setToggleGroup(groupRegion);

        RadioButton rb3 = new RadioButton(OpRegiao.Acores.toString());
        rb3.setToggleGroup(groupRegion);

        RadioButton rb4 = new RadioButton(OpRegiao.Madeira.toString());
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

        atualizaLocais();

        setCenter(gridForecasts);



        // Get the selected toggle
        atualizaLocais() ;

        buttonFilter.setOnAction(event -> {

            ToggleGroup finalToggleGroup = groupRegion;
            groupRegion.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                if (finalToggleGroup.getSelectedToggle() != null) {
                    RadioButton selectedRadioButton = (RadioButton) finalToggleGroup.getSelectedToggle();

                }
            });


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

        Local local= comboLocals.getSelectionModel().getSelectedItem();

        String url = "https://api.ipma.pt/open-data/forecast/meteorology/cities/daily/" + local.getGlobalIdLocal();

        Gson gson = new GsonBuilder().create();

        List<Forecast> listaForecast = null;
        OkHttpClient client = new OkHttpClient();
        Request getRequest = new Request.Builder()
                .url(url).build();

        try {
            Response response = client.newCall(getRequest).execute();
            String json = response.body().string();

            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(json);
            JsonArray data = object.getAsJsonArray("data");


            Type listType = new TypeToken<ArrayList<Forecast>>() {
            }.getType();
            listaForecast = gson.fromJson(data, listType);

            for (Forecast l : listaForecast) {
                System.out.println(l);
            }




        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void atualizaLocais() {

        System.out.println(getRegiaoFiltro());


        Gson gson = new GsonBuilder().create();

        List<Local> listaLocais = null;
        OkHttpClient client = new OkHttpClient();
        Request getRequest = new Request.Builder()
                .url("https://api.ipma.pt/open-data/distrits-islands.json") // colocar o URL do endpoint aqui
                .build();

        try {
            Response response = client.newCall(getRequest).execute();
            String json = response.body().string();

            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(json);
            JsonArray data = object.getAsJsonArray("data");

            Type listType = new TypeToken<ArrayList<Local>>(){}.getType();
            listaLocais = gson.fromJson(data, listType);

            //idRegiao: identificador região [1 "Continente", 2 "Arq. Madeira", 3 "Arq. Açores"]

            listLocals.addAll(listaLocais);






        } catch (
                IOException e) {
            e.printStackTrace();
        }
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



    public void showError(String title, String message) {
        System.err.println(String.format("%s - %s", title, message));
    }
}