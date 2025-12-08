package com.hcl.guvi.capstonproject.eventmanagement.serviceinterface;

import java.util.List;

import com.hcl.guvi.capstonproject.eventmanagement.dto.EventDto;

public interface EventInterface {
	public String saveEvent(EventDto event);
	public String updateEvent(EventDto event);
	public List<EventDto> findAllEvent();
	
	public List<EventDto> findrequiredEventByName(String name);
	public String deleteEventByName(String name);
	public String updateRsvp(Long id,Boolean rsvp);

}
