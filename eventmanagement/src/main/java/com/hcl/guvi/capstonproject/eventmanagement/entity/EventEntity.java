package com.hcl.guvi.capstonproject.eventmanagement.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hcl.guvi.capstonproject.eventmanagement.flexibledate.FlexibleLocalDateTimeDeserializer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="events")
public class EventEntity {
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
	@JoinColumn(name="club_id",nullable=false)
	private ClubEntity club;
    @Column(nullable = false)
    private String ownerName;

	
}
