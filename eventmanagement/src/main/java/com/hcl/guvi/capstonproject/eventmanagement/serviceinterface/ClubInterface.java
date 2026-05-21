package com.hcl.guvi.capstonproject.eventmanagement.serviceinterface;

import java.util.List;

import com.hcl.guvi.capstonproject.eventmanagement.dto.ClubDto;

public interface ClubInterface {
     public String CreateClub(ClubDto clubDto);
     public String deleteClub(Long id);
     public List<ClubDto> fetchAllClub();
     public List<ClubDto>fetchclubsByOwnerUsername(String username);
     
}
