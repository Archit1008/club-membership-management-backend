package com.hcl.guvi.capstonproject.eventmanagement.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hcl.guvi.capstonproject.eventmanagement.flexibledate.FlexibleLocalDateTimeDeserializer;

import java.time.LocalDateTime;

public class GetsEvent {
    private Long id;
    private String eventName;
    private Boolean rsvp = Boolean.FALSE;

    @JsonDeserialize(using = FlexibleLocalDateTimeDeserializer.class)
    private LocalDateTime eventDate;
    private String clubName;
    private String location;
}
