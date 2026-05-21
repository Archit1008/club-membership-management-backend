package com.hcl.guvi.capstonproject.eventmanagement.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hcl.guvi.capstonproject.eventmanagement.flexibledate.FlexibleLocalDateTimeDeserializer;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class GetEventsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column
    private String eventName;
    @Column
    private Boolean rsvp;
    @JsonDeserialize(using = FlexibleLocalDateTimeDeserializer.class)
    LocalDateTime eventDate;
    @ManyToOne
//    @JoinColumn(name="club_id",nullable=false)
//    private ClubEntity club;
    @Column(nullable = false)
    private String location;
    @Column
    private String clubName;

}
