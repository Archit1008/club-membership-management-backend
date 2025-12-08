package com.hcl.guvi.capstonproject.eventmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hcl.guvi.capstonproject.eventmanagement.client.UserMembershipClient;
import com.hcl.guvi.capstonproject.eventmanagement.dto.ClubDto;

import com.hcl.guvi.capstonproject.eventmanagement.entity.ClubEntity;
import com.hcl.guvi.capstonproject.eventmanagement.repository.ClubRepository;
import com.hcl.guvi.capstonproject.eventmanagement.serviceinterface.ClubInterface;
@Service
public class ClubServiceImpl implements ClubInterface {
	private static final Logger logger = LoggerFactory.getLogger(EventService.class);
	private final ModelMapper modelMapper=new ModelMapper();
	@Autowired
	private ClubRepository clubRepository;

	@Override
	public String CreateClub(ClubDto clubDto) {
		
	   ClubEntity clubEntity=modelMapper.map(clubDto, ClubEntity.class);
	   clubRepository.save(clubEntity);
	    logger.info("club created successfully");
		return "Successfully Created Club" ;
		
	}


@Override
public String deleteClub(Long id) {
    if (clubRepository.existsById(id)) {
        clubRepository.deleteById(id);
        return "Successfully deleted club";
    } else {
        return "Club not found";
    }
}


@Override
public List<ClubDto> fetchAllClub() {
    List<ClubEntity> clubsEntity = clubRepository.findAll();
    logger.info("Fetched {} clubs from database", clubsEntity.size());

    List<ClubDto> listOfClubs = clubsEntity.stream()
        .map(entity -> {
            ClubDto club = new ClubDto();
            club.setClubName(entity.getClubName());
            club.setLocation(entity.getLocation());
            club.setId(entity.getId()); // include ID if needed
            club.setOwnerName(entity.getOwnerName());
            return club;
        })
        .collect(Collectors.toList());

    return listOfClubs;
}

}
