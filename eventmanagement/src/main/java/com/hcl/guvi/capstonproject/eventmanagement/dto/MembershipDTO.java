package com.hcl.guvi.capstonproject.eventmanagement.dto;




import java.time.LocalDateTime;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MembershipDTO {
    private Long id;
    private String ownerUserId;
    private String type;   // or MembershipType
    private String status; // "APPROVED" etc.
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}