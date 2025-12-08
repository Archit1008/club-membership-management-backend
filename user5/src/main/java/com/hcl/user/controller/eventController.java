package com.hcl.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.hcl.user.dto.EventDto;
import com.hcl.user.entity.User;
import com.hcl.user.service.EventCallingService;
import com.hcl.user.serviceImpl.AuthService;;

@RestController
@RequestMapping("/user/events")
public class eventController {
	@Autowired
	EventCallingService clubClient;
	@Autowired
	AuthService authService;
	@PostMapping("/saveEvent")
	@PreAuthorize("hasAuthority('OWNER')")
	public ResponseEntity<String>createEvent(@RequestBody EventDto event){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String userName=auth.getName();
    	event.setOwnerName(userName);
		String s=clubClient.saveEvent(event);
		return new ResponseEntity<>(s,HttpStatus.ACCEPTED);
		
	}
	@PutMapping("/updateEvent")
	@PreAuthorize("hasAuthority('OWNER')")
	public ResponseEntity<String>updateEvent(@RequestBody EventDto event){
		String s=clubClient.updateEvent(event);
		return new ResponseEntity<>(s,HttpStatus.ACCEPTED);
		
	}
	@DeleteMapping("/deleteEvent/{name}")
	@PreAuthorize("hasAuthority('OWNER')")
	public ResponseEntity<String>deleteEvent(@PathVariable String name){
		String s=clubClient.deleteEvent(name);
		return new ResponseEntity<>(s,HttpStatus.ACCEPTED);
		
	}
	@GetMapping("/getAllEvent")
	@PreAuthorize("hasAnyAuthority('MEMBER')")
	public ResponseEntity<List<EventDto>> getAllEvent() {
	    List<EventDto> event = clubClient.getEvent();
	    return new ResponseEntity<>(event, HttpStatus.ACCEPTED);
	}

	@GetMapping("/getEventByOwnerName")
	@PreAuthorize("hasAuthority('OWNER')")
	public ResponseEntity<List<EventDto>>getEventById(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String ownerName=auth.getName();
		List<EventDto>event=clubClient.getEvent(ownerName);
		return new ResponseEntity<>(event,HttpStatus.ACCEPTED);
	}
	@PutMapping("/updateRsvp/{id}/{rsvp}")
	@PreAuthorize("hasAuthority('MEMBER')")
	public ResponseEntity<String>updateRsvp(@PathVariable Long id,@PathVariable Boolean rsvp){
		String s=clubClient.updateRsvp(id, rsvp);
		return new ResponseEntity<>(s,HttpStatus.ACCEPTED);
	}
	@GetMapping("/getAllUser")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<User>>getAllUser(){
		List<User>user=authService.getUser();
		return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
		
	}
	
	
	

}
