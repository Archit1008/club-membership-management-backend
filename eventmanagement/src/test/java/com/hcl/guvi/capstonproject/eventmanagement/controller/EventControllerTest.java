package com.hcl.guvi.capstonproject.eventmanagement.controller;
import com.hcl.guvi.capstonproject.eventmanagement.controller.EventController;
import com.hcl.guvi.capstonproject.eventmanagement.dto.EventDto;
import com.hcl.guvi.capstonproject.eventmanagement.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    @Test
    void testSaveEvent() {
        EventDto event = new EventDto();
        when(eventService.saveEvent(event)).thenReturn("Event Saved");

        ResponseEntity<String> response = eventController.saveEvent(event);

        assertEquals("Event Saved", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(eventService, times(1)).saveEvent(event);
    }

    @Test
    void testUpdateEvent() {
        EventDto event = new EventDto();
        when(eventService.updateEvent(event)).thenReturn("Event Updated");

        ResponseEntity<String> response = eventController.upDateEvent(event);

        assertEquals("Event Updated", response.getBody());
        
        assertEquals(200, response.getStatusCodeValue());
        verify(eventService, times(1)).updateEvent(event);
    }

    @Test
    void testGetAllEvent() {
        List<EventDto> mockEvents = Arrays.asList(new EventDto(), new EventDto());
        when(eventService.findAllEvent()).thenReturn(mockEvents);

        ResponseEntity<List<EventDto>> response = eventController.getAllEvent();

        assertEquals(2, response.getBody().size());
        assertEquals(200, response.getStatusCodeValue());
        verify(eventService, times(1)).findAllEvent();
    }

//    @Test
//    void testGetEventById() {
//        EventDto event = new EventDto();
//        when(eventService.findEventById(1L)).thenReturn(event);
//
//        ResponseEntity<EventDto> response = eventController.getEventById(1L);
//
//        assertEquals(event, response.getBody());
//        assertEquals(200, response.getStatusCodeValue());
//        verify(eventService, times(1)).findEventById(1L);
//    }

    @Test
    void testDeleteEvent() {
        when(eventService.deleteEventByName("TestEvent")).thenReturn("Deleted");

        ResponseEntity<String> response = eventController.deleteEvent("TestEvent");

        assertEquals("Deleted", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(eventService, times(1)).deleteEventByName("TestEvent");
    }

    @Test
    void testUpdateRsvpEvent() {
        when(eventService.updateRsvp(1L, true)).thenReturn("RSVP Updated");

        ResponseEntity<String> response = eventController.updateRsvpEvent(1L, true);

        assertEquals("RSVP Updated", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(eventService, times(1)).updateRsvp(1L, true);
    }
}