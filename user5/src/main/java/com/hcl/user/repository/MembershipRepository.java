
package com.hcl.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.user.entity.MembershipEntity;
import com.hcl.user.entity.MembershipStatus;
import com.hcl.user.entity.MembershipType;

public interface MembershipRepository extends JpaRepository<MembershipEntity, Long> {

    Optional<MembershipEntity> findFirstByOwnerUserIdAndStatusOrderByApprovedAtDesc(
        String ownerUserId, MembershipStatus status
    );

    Optional<MembershipEntity> findFirstByOwnerUserIdOrderByAppliedAtDesc(String ownerUserId);

    boolean existsByOwnerUserIdAndStatus(String ownerUserId, MembershipStatus status);

    // ===== NEW: Admin listing support =====

    /**
     * List memberships by status (e.g., PENDING_APPROVAL, APPROVED, REJECTED).
     */
    List<MembershipEntity> findByStatus(MembershipStatus status);

    /**
     * List memberships by status AND type (e.g., PENDING_APPROVAL + SILVER/GOLD).
     */
    List<MembershipEntity> findByStatusAndType(MembershipStatus status, MembershipType type);
}
