package com.hcl.user.clubconfig;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hcl.user.dto.EventDto;
import com.hcl.user.securityconfig.FeignAuthForwardingConfig;

//
//@FeignClient(name="eventmanagement",contextId = "eventClient",url="http://localhost:8283/events")

@FeignClient(
    name = "eventmanagement",
    url = "http://localhost:8283/events",
    configuration = FeignAuthForwardingConfig.class
)

public interface EventClient {
    @PostMapping("/saveEvent")
    String saveEvent(@RequestBody EventDto event);
    @PutMapping("/updateEvent")
	public String upDateEvent(@RequestBody EventDto event);
    @GetMapping("/getEvent")
	public List<EventDto>getAllEvent();
    @GetMapping("/getEventBy/{ownerName}")
	public  List<EventDto> getEventByOwnerName(@PathVariable String ownerName);
    @DeleteMapping("/deleteEvent/{name}")
	public String deleteEvent( @PathVariable String name);
    @PutMapping("/changeRsvp/{id}/{rsvp}")
    String updateRsvpEvent(@PathVariable("id") Long id, @PathVariable("rsvp") Boolean rsvp);
    @GetMapping("/getAllEvents")
    public ResponseEntity<List<EventDto>>getAllEvents();

}
