package view;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.StringConverter;
import model.Forecast;
import model.Local;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ForecastView extends BorderPane {

    private Button buttonFilter;
    private Button buttonForecast;
    private ObservableList<Local> listLocals;
    private ComboBox<Local> comboLocals;
    private GridPane gridForecasts;
    private ToggleGroup groupRegion;
    private Forecast forecast;
    private Label lblPrecipitacao;
    private Label lblTempMin;
    private Label lblTempMax;
    private Label lblDate;


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
            //lblDate.setText("Data:" + forecast.getForecastDate());

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
            List<Forecast> listaForecast = gson.fromJson(data, listType);

            Platform.runLater(() -> exibirPrevisao(listaForecast));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void exibirPrevisao(List<Forecast> listaForecast){

        for(int i = 0; i < listaForecast.size(); i++){
            Forecast forecast = listaForecast.get(i);

            LocalDate date = LocalDate.parse(forecast.getForecastDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String diaSmn = date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt"));

            Label lbldiaS = new Label(diaSmn);
            gridForecasts.add(lbldiaS, i, 0);

            lblDate = new Label ((i + 1) + forecast.getForecastDate());
            lblTempMin = new Label(forecast.gettMin() + " C");
            lblTempMax = new Label(forecast.gettMax() + " C");
            lblPrecipitacao = new Label(forecast.getPrecipitaProb() + " %");

            lblTempMin.setStyle("-fx-text-fill: blue;");
            lblTempMax.setStyle("-fx-text-fill: red;");

            gridForecasts.add(lblDate, i, 1);
            gridForecasts.add(lblTempMin, i, 2);
            gridForecasts.add(lblTempMax, i, 3);
            gridForecasts.add(lblPrecipitacao, i, 4);

            gridForecasts.setHalignment(lbldiaS, HPos.CENTER.CENTER);
            gridForecasts.setHalignment(lblDate, HPos.CENTER.CENTER);
            gridForecasts.setHalignment(lblTempMin, HPos.CENTER.CENTER);
            gridForecasts.setHalignment(lblTempMax, HPos.CENTER.CENTER);
            gridForecasts.setHalignment(lblPrecipitacao, HPos.CENTER.CENTER);

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