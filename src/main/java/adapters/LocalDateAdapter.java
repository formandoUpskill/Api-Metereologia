package adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.JsonToken;



import java.io.IOException;
import java.time.LocalDate;

public class LocalDateAdapter extends TypeAdapter<LocalDate> {
    @Override
    public void write(JsonWriter writer, LocalDate localDate) throws IOException {

        if (localDate == null) {
            writer.nullValue();
            return;
        }

        writer.beginObject();

        writer.name("year");
        writer.value(localDate.getYear());

        writer.name("month");
        writer.value(localDate.getMonthValue() );

        writer.name("day");
        writer.value(localDate.getDayOfMonth() );

        writer.endObject();
    }
    @Override
    public LocalDate read(JsonReader reader) throws IOException {


        reader.beginObject();
        String fieldname = null;
        int day=0;
        int month=0;
        int year=0;

        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }


        while (reader.hasNext()) {

            JsonToken token = reader.peek();

            if (token.equals(JsonToken.NAME)) {
                //get the current token
                fieldname = reader.nextName();
            }

            if ("year".equals(fieldname)) {
                //move to next token
                token = reader.peek();
                year= reader.nextInt();
            }

            if("month".equals(fieldname)) {
                //move to next token
                token = reader.peek();
                month= reader.nextInt();
            }

            if("day".equals(fieldname)) {
                //move to next token
                token = reader.peek();
                day= reader.nextInt();
            }
        }
        reader.endObject();


        return LocalDate.of(year, month, day);


    }
}
