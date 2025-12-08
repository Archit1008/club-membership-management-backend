
package com.hcl.user.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.user.dto.MembershipDTO;
import com.hcl.user.entity.MembershipEntity;
import com.hcl.user.entity.MembershipStatus;
import com.hcl.user.entity.MembershipType;
import com.hcl.user.repository.MembershipRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepository;

    @Transactional
    public MembershipDTO apply(String ownerUserId, MembershipType type) {
        // Prevent duplicate pending/approved
        if (membershipRepository.existsByOwnerUserIdAndStatus(ownerUserId, MembershipStatus.PENDING_APPROVAL) ||
            membershipRepository.existsByOwnerUserIdAndStatus(ownerUserId, MembershipStatus.APPROVED)) {
            throw new IllegalStateException("Existing membership pending/approved for user");
        }
        MembershipEntity entity = MembershipEntity.builder()
                .ownerUserId(ownerUserId)
                .type(type)
                .status(MembershipStatus.PENDING_APPROVAL)
                .appliedAt(LocalDateTime.now())
                .build();
        entity = membershipRepository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public MembershipDTO approve(Long membershipId, String adminUserId, String remarks,
                                 LocalDateTime start, LocalDateTime end) {
        MembershipEntity entity = membershipRepository.findById(membershipId)
            .orElseThrow(() -> new IllegalArgumentException("Membership not found"));

        if (entity.getStatus() != MembershipStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("Only pending memberships can be approved");
        }

        // Optional safety: ensure no other APPROVED membership currently active for owner
        boolean alreadyApproved = membershipRepository
                .existsByOwnerUserIdAndStatus(entity.getOwnerUserId(), MembershipStatus.APPROVED);
        if (alreadyApproved) {
            throw new IllegalStateException("Owner already has an approved membership");
        }

        entity.setStatus(MembershipStatus.APPROVED);
        entity.setApprovedAt(LocalDateTime.now());
        entity.setApprovedByAdminUserId(adminUserId);
        entity.setAdminRemarks(remarks);
        entity.setStartDate(start != null ? start : LocalDateTime.now());
        entity.setEndDate(end); // optional

        entity = membershipRepository.save(entity);
        return toDto(entity);
    }

    @Transactional
    public MembershipDTO reject(Long membershipId, String adminUserId, String remarks) {
        MembershipEntity entity = membershipRepository.findById(membershipId)
            .orElseThrow(() -> new IllegalArgumentException("Membership not found"));

        if (entity.getStatus() != MembershipStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("Only pending memberships can be rejected");
        }

        entity.setStatus(MembershipStatus.REJECTED);
        entity.setApprovedByAdminUserId(adminUserId);
        entity.setAdminRemarks(remarks);
        entity = membershipRepository.save(entity);
        return toDto(entity);
    }

    @Transactional(readOnly = true)
    public MembershipDTO getActiveMembership(String ownerUserId) {
        return membershipRepository
                .findFirstByOwnerUserIdAndStatusOrderByApprovedAtDesc(ownerUserId, MembershipStatus.APPROVED)
                .map(m -> {
                    if (m.getEndDate() != null && m.getEndDate().isBefore(LocalDateTime.now())) {
                        // Treat expired as no active membership
                        return null;
                    }
                    return toDto(m);
                })
                .orElse(null);
    }

    // ===== NEW: Admin listing helpers =====

    @Transactional(readOnly = true)
    public List<MembershipDTO> listByStatus(MembershipStatus status) {
        return membershipRepository.findByStatus(status)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MembershipDTO> listByStatusAndType(MembershipStatus status, MembershipType type) {
        return membershipRepository.findByStatusAndType(status, type)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    // ===== Mapping =====
    private MembershipDTO toDto(MembershipEntity e) {
        if (e == null) return null;
        return MembershipDTO.builder()
                .id(e.getId())
                .ownerUserId(e.getOwnerUserId())
                .type(e.getType())
                .status(e.getStatus())
                .appliedAt(e.getAppliedAt())
                .approvedAt(e.getApprovedAt())
                .approvedByAdminUserId(e.getApprovedByAdminUserId())
                .startDate(e.getStartDate())
                .endDate(e.getEndDate())
                .adminRemarks(e.getAdminRemarks())
                .build();
    }
}
