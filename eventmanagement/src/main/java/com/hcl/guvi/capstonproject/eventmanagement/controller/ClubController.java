package com.hcl.guvi.capstonproject.eventmanagement.controller;



import com.hcl.guvi.capstonproject.eventmanagement.dto.ClubDto;
import com.hcl.guvi.capstonproject.eventmanagement.serviceinterface.ClubInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clubs")
public class ClubController {

    @Autowired
    private ClubInterface clubService;

    // ✅ Create Club
    @PostMapping
    public ResponseEntity<String> createClub(@RequestBody ClubDto clubDto) {
        String response = clubService.CreateClub(clubDto);
        return ResponseEntity.ok(response);
    }

    // ✅ Delete Club
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClub(@PathVariable Long id) {
        String response = clubService.deleteClub(id);
        return ResponseEntity.ok(response);
    }

    // ✅ Fetch All Clubs
    @GetMapping
    public ResponseEntity<List<ClubDto>> fetchAllClubs() {
        List<ClubDto> clubs = clubService.fetchAllClub();
        return ResponseEntity.ok(clubs);
    }
}