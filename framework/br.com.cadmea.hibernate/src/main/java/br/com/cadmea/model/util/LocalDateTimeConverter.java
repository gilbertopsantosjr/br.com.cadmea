package br.com.cadmea.model.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

  @Override
  public Timestamp convertToDatabaseColumn(LocalDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    return Timestamp.valueOf(dateTime);
  }

  @Override
  public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
    if (timestamp == null) {
      return null;
    }
    return timestamp.toLocalDateTime();
  }
}