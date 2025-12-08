package com.hcl.guvi.capstonproject.eventmanagement.entity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.guvi.capstonproject.eventmanagement.entity.EventEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventEntityTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testLombokGettersAndSetters() {
        EventEntity event = new EventEntity();
        event.setId(1L);
        event.setEventName("Test Event");
        event.setRsvp(true);
        event.setEventDate(LocalDateTime.of(2025, 11, 16, 14, 30));

        assertEquals(1L, event.getId());
        assertEquals("Test Event", event.getEventName());
        assertTrue(event.getRsvp());
        assertEquals(LocalDateTime.of(2025, 11, 16, 14, 30), event.getEventDate());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 11, 16, 14, 30);
        EventEntity event = new EventEntity(2L, "Constructor Event", false, dateTime);

        assertEquals(2L, event.getId());
        assertEquals("Constructor Event", event.getEventName());
        assertFalse(event.getRsvp());
        assertEquals(dateTime, event.getEventDate());
    }

    @Test
    void testJsonDeserializationIsoFormat() throws Exception {
        String json = "{\"id\":3,\"eventName\":\"ISO Event\",\"rsvp\":true,\"eventDate\":\"2025-11-16T14:30:00\"}";

        EventEntity event = objectMapper.readValue(json, EventEntity.class);

        assertEquals(3L, event.getId());
        assertEquals("ISO Event", event.getEventName());
        assertTrue(event.getRsvp());
        assertEquals(LocalDateTime.of(2025, 11, 16, 14, 30), event.getEventDate());
    }

    @Test
    void testJsonDeserializationPlainDate() throws Exception {
        String json = "{\"id\":4,\"eventName\":\"Plain Date Event\",\"rsvp\":false,\"eventDate\":\"2025-11-16\"}";

        EventEntity event = objectMapper.readValue(json, EventEntity.class);

        assertEquals(4L, event.getId());
        assertEquals("Plain Date Event", event.getEventName());
        assertFalse(event.getRsvp());
        assertEquals(LocalDateTime.of(2025, 11, 16, 0, 0), event.getEventDate());
    }

    @Test
    void testJsonDeserializationInvalidDate() {
        String json = "{\"id\":5,\"eventName\":\"Invalid Date\",\"rsvp\":true,\"eventDate\":\"invalid-date\"}";

        Exception exception = assertThrows(Exception.class, () -> objectMapper.readValue(json, EventEntity.class));
        assertTrue(exception.getMessage().contains("Unparseable date"));
    }
}