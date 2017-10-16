package br.com.cadmea.spring.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateSerializer {

    private static final ThreadLocal<SimpleDateFormat> sdf = ThreadLocal
            .<SimpleDateFormat>withInitial(() -> {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            });

    private static final DateFormat readFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
    private static final DateFormat writeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static class Serialize extends JsonSerializer<Date> {
        @Override
        public void serialize(final Date value, final JsonGenerator gen,
                              final SerializerProvider serializers)
                throws IOException, JsonProcessingException {
            if (value == null) {
                gen.writeNull();
            } else {
                gen.writeString(sdf.get().format(value));
            }
        }
    }


    public static class Deserialize extends JsonDeserializer<Date> {

        @Override
        public Date deserialize(final JsonParser p, final DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            final String dateAsString = p.getText();
            try {
                if (StringUtils.isEmpty(dateAsString)) {
                    return null;
                } else {
                    Date date = null;
                    String formattedDate = "";
                    try {
                        date = readFormat.parse(dateAsString);
                        if (date != null) {
                            formattedDate = writeFormat.format(date);
                        }
                    } catch (final ParseException e) {
                        e.printStackTrace();
                    }

                    return new Date(sdf.get().parse(formattedDate).getTime());
                }
            } catch (final ParseException pe) {
                throw new RuntimeException(pe);
            }
        }

    }

}
