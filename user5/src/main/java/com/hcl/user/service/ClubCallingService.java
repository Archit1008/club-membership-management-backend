package com.hcl.user.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hcl.user.clubconfig.ClubClient;
import com.hcl.user.dto.ClubDto;
@Service
public class ClubCallingService {
	private final ClubClient clubClient;
	public ClubCallingService(ClubClient clubClient) {
		this.clubClient=clubClient;
	}
	
		 
		 public String createClub(ClubDto clubDto){
			 return clubClient.createClub(clubDto);
		 }
		 
		    public String deleteClub(Long id) {
		    	return clubClient.deleteClub(id);
		    }
		
		    public List<ClubDto> fetchAllClubs() {
		    	return clubClient.fetchAllClubs();
		    }
	

}
