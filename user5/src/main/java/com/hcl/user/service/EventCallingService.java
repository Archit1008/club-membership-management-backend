package com.hcl.user.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hcl.user.dto.EventDto;

import com.hcl.user.clubconfig.EventClient;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class EventCallingService {
	private final EventClient eventClient;
	public EventCallingService(EventClient eventClient) {
		this.eventClient=eventClient;
	}
	public String saveEvent(EventDto event) {
		return eventClient.saveEvent(event);
	}
	public List<EventDto> getEvent() {
		return eventClient.getAllEvent();
	}
	public String updateEvent(EventDto event) {
		return eventClient.upDateEvent(event);
	}
	public List<EventDto> getEvent(String ownerName) {
		return  eventClient.getEventByOwnerName(ownerName);
	}
    public String deleteEvent(String eventName) {
    	return eventClient.deleteEvent(eventName);
    }
    public String updateRsvp(Long id,Boolean rsvp) {
    	return eventClient.updateRsvpEvent(id, rsvp);
    }
	public List<EventDto> getAllEvents(){ return eventClient.getAllEvents().getBody();}
}
