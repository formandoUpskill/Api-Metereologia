import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.Local;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainInput {
    public static void main(String[] args) {

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

//            System.out.println(data);

            Type listType = new TypeToken<ArrayList<Local>>(){}.getType();
            listaLocais = gson.fromJson(data, listType);

//            System.out.println(listaLocais);
//            System.out.println(listaLocais.size());

            for(Local l: listaLocais){
                System.out.println(l);
            }

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}

