package com.hcl.user.clubconfig;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hcl.user.dto.ClubDto;

@FeignClient(name="eventmanagement",contextId = "clubClient",url="http://localhost:8283/clubs")
public interface ClubClient {
	 @PostMapping
	 public String createClub(@RequestBody ClubDto clubDto);
	 @DeleteMapping("/{id}")
	    public String deleteClub(@PathVariable Long id);
	 @GetMapping
	    public List<ClubDto> fetchAllClubs() ;
}
