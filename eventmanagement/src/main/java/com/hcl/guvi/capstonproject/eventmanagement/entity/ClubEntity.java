package com.hcl.guvi.capstonproject.eventmanagement.entity;

import java.util.ArrayList;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Clubs")
@Entity
public class ClubEntity {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	@Column
	private String clubName;
	@Column
	private String location;
	@OneToMany(mappedBy="club",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<EventEntity>events=new ArrayList<>();
    @Column(nullable = false)
    private String ownerName;

	
   
}
