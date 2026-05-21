package com.hcl.user.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data

public class EventDto {
	private Long id;
	private String eventName;
	private Boolean rsvp;
	LocalDateTime eventDate;
	private Long clubId;
	private String ownerName;
	private String location;
	private String clubName;
}
