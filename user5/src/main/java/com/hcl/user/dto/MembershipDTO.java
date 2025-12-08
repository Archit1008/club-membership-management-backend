package com.hcl.user.dto;




import java.time.LocalDateTime;

import com.hcl.user.entity.MembershipStatus;
import com.hcl.user.entity.MembershipType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class MembershipDTO {
    private Long id;
    private String ownerUserId;
    private MembershipType type;
    private MembershipStatus status;
    private LocalDateTime appliedAt;
    private LocalDateTime approvedAt;
    private String approvedByAdminUserId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String adminRemarks;
}
