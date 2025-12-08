
package com.hcl.guvi.capstonproject.eventmanagement.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hcl.guvi.capstonproject.eventmanagement.flexibledate.FlexibleLocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long id;
    private String eventName;
    private Boolean rsvp = Boolean.FALSE;

    @JsonDeserialize(using = FlexibleLocalDateTimeDeserializer.class)
    private LocalDateTime eventDate;
    private String ownerName;
    private Long clubId; // boxed Long is correct
    // Optional convenience:
    // private String clubName;
}
