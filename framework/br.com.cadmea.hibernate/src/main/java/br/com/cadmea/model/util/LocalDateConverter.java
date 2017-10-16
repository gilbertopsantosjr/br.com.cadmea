package br.com.cadmea.model.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.sql.Date;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {
    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        if(localDate != null)
            return Date.valueOf(localDate);
        return null;
    }

    @Override
    public LocalDate convertToEntityAttribute(Date sqlDate) {
        if(sqlDate != null)
            return sqlDate.toLocalDate();
        return null;
    }
}
