package br.com.cadmea.spring.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonDateSerializer {

	private static final ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.<SimpleDateFormat>withInitial(() -> {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	});

	public static class Serialize extends JsonSerializer<Date> {

		@Override
		public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
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
		public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			String dateAsString = p.getText();
            try {
                if (StringUtils.isEmpty(dateAsString)) {
                    return null;
                }
                else {
                    DateFormat readFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
                    DateFormat writeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = null;
                    try {
                       date = readFormat.parse( dateAsString );
                    } catch ( ParseException e ) {
                        e.printStackTrace();
                    }

                    String formattedDate = "";
                    if( date != null ) {
                    	formattedDate = writeFormat.format( date );
                    }

                    return new Date(sdf.get().parse(formattedDate).getTime());
                }
            }
            catch (ParseException pe) {
                throw new RuntimeException(pe);
            }
		}

	}

}
