import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.Forecast;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainForecaste {
    public static void main(String[] args) {

        Gson gson = new GsonBuilder().create();

        List<Forecast> listaForecast = null;
        OkHttpClient client = new OkHttpClient();
        Request getRequest = new Request.Builder()
                .url("https://api.ipma.pt/open-data/forecast/meteorology/cities/daily/1010500.json").build();

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
}

