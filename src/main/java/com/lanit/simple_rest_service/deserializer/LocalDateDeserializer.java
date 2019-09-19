package com.lanit.simple_rest_service.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

    public LocalDateDeserializer() {
        this(null);
    }

    public LocalDateDeserializer(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String date = jsonParser.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate local = LocalDate.parse(date, formatter);
        return local;
    }

}
