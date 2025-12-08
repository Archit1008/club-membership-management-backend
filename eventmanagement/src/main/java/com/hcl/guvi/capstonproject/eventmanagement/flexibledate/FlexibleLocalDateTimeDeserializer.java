package com.hcl.guvi.capstonproject.eventmanagement.flexibledate;



import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class FlexibleLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    // Common date-time formats
    private static final List<DateTimeFormatter> FORMATTERS = Arrays.asList(
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,       // 2025-11-16T14:30:00
            DateTimeFormatter.ISO_DATE,                 // 2025-11-16
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    );

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String date = p.getText().trim();

        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                // If it's just a date without time, convert to start of day
                if (!date.contains(":") && !date.contains("T")) {
                    return LocalDate.parse(date, formatter).atStartOfDay();
                }
                return LocalDateTime.parse(date, formatter);
            } catch (DateTimeParseException ignored) {
                // Try next format
            }
        }

        throw new IOException("Unparseable date: " + date);
    }
}