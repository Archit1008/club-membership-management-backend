package com.hcl.user.entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "memberships",
       indexes = {
           @Index(name = "idx_membership_user", columnList = "ownerUserId"),
           @Index(name = "idx_membership_status", columnList = "status")
       })
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class MembershipEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ownerUserId; // from User service identity (e.g., UUID or String)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipStatus status;

    @Column(nullable = false)
    private LocalDateTime appliedAt;

    private LocalDateTime approvedAt;
    private String approvedByAdminUserId;

    // Optional validity period
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Optional notes
    private String adminRemarks;
}
