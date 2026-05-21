package com.hcl.guvi.capstonproject.eventmanagement.service;

import com.hcl.guvi.capstonproject.eventmanagement.dto.EventDto;
import com.hcl.guvi.capstonproject.eventmanagement.entity.ClubEntity;
import com.hcl.guvi.capstonproject.eventmanagement.entity.EventEntity;
import com.hcl.guvi.capstonproject.eventmanagement.exception.ResourceNotFoundException;
import com.hcl.guvi.capstonproject.eventmanagement.repository.ClubRepository;
import com.hcl.guvi.capstonproject.eventmanagement.repository.EventRepository;
import com.hcl.guvi.capstonproject.eventmanagement.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {
    @Mock
    private ClubRepository clubRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

//    @Test
//    void testSaveEvent() {
//        // Mock clubRepository behavior
//        ClubEntity club = new ClubEntity("Chess Club", "Delhi", "Archit");
//        when(clubRepository.findById(1L)).thenReturn(Optional.of(club));
//
//        // Prepare event
//        EventEntity event = new EventEntity(null, "Chess Event", true,
//                LocalDateTime.now(), club, "Archit", "Delhi", "Chess Club");
//
//        // Mock eventRepository save
//        when(eventRepository.save(event)).thenReturn(event);
//
//        // Call service
//        String saved = eventService.saveEvent(event);
//
//        // Assertions
//        assertEquals("Chess Event", saved.getEventName());
//        assertEquals("Chess Club", saved.getClubName());
//    }


    @Test
    void testUpdateEvent_NotFound() {
        EventDto dto = new EventDto();
        dto.setId(99L);

        when(eventRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> eventService.updateEvent(dto));
    }

    @Test
    void testFindAllEvent() {
        EventEntity e1 = new EventEntity();
        e1.setId(1L);
        e1.setEventName("Event1");
        EventEntity e2 = new EventEntity();
        e2.setId(2L);
        e2.setEventName("Event2");

        when(eventRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<EventDto> result = eventService.findAllEvent();

        assertEquals(2, result.size());
        assertEquals("Event1", result.get(0).getEventName());
        verify(eventRepository, times(1)).findAll();
    }

//    @Test
//    void testFindEventById_Success() {
//        EventEntity entity = new EventEntity();
//        entity.setId(1L);
//        entity.setEventName("Test Event");
//
//        when(eventRepository.findById(1L)).thenReturn(Optional.of(entity));
//
//        EventDto result = eventService.findEventById(1L);
//
//        assertEquals("Test Event", result.getEventName());
//    }

//    @Test
//    void testFindEventById_NotFound() {
//        when(eventRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> eventService.findEventById(1L));
//    }

    @Test
    void testDeleteEventByName_Success() {
        EventEntity entity = new EventEntity();
        entity.setEventName("TestEvent");

        when(eventRepository.findByEventName("TestEvent")).thenReturn(Arrays.asList(entity));

        String result = eventService.deleteEventByName("TestEvent");

        assertEquals("Successfully deleted event(s)", result);
        verify(eventRepository, times(1)).deleteAll(anyList());
    }

    @Test
    void testDeleteEventByName_NotFound() {
        when(eventRepository.findByEventName("Unknown")).thenReturn(Arrays.asList());

        assertThrows(ResourceNotFoundException.class, () -> eventService.deleteEventByName("Unknown"));
    }

    @Test
    void testUpdateRsvp_Success() {
        EventEntity entity = new EventEntity();
        entity.setId(1L);
        entity.setRsvp(false);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(entity));

        String result = eventService.updateRsvp(1L, true);

        assertEquals("Successfully updated RSVP", result);
        assertTrue(entity.getRsvp());
    }

    @Test
    void testUpdateRsvp_NotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> eventService.updateRsvp(1L, true));
    }
}