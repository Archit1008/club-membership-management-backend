//
//package com.hcl.user.controller;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import com.hcl.user.dto.MembershipDTO;
//import com.hcl.user.entity.MembershipStatus;
//import com.hcl.user.entity.MembershipType;
//import com.hcl.user.serviceImpl.MembershipService;
//
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequestMapping("/api/memberships")
//@RequiredArgsConstructor
//public class MembershipController {
//
//    private final MembershipService membershipService;
//
//    // OWNER applies for membership
//    @PostMapping("/apply")
//    @PreAuthorize("hasAuthority('OWNER')")
//    public MembershipDTO apply(@RequestParam String ownerUserId,
//                               @RequestParam MembershipType type) {
//        return membershipService.apply(ownerUserId, type);
//    }
//
//    // ADMIN approves
//    @PatchMapping("/approve")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public MembershipDTO approve(@PathVariable Long id,
//                                 @RequestParam String adminUserId,
//                                 @RequestParam(required = false) String remarks,
//                                 @RequestParam(required = false)
//                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
//                                 @RequestParam(required = false)
//                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
//        return membershipService.approve(id, adminUserId, remarks, start, end);
//    }
//
//    // ADMIN rejects
//    @PatchMapping("/{id}/reject")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public MembershipDTO reject(@PathVariable Long id,
//                                @RequestParam String adminUserId,
//                                @RequestParam(required = false) String remarks) {
//        return membershipService.reject(id, adminUserId, remarks);
//    }
//
//    // Event service / Owner dashboard uses this to validate
//    @GetMapping("/active/{ownerUserId}")
//    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
//    public MembershipDTO getActive(@PathVariable String ownerUserId) {
//        return membershipService.getActiveMembership(ownerUserId);
//    }
//
//    // ===== NEW: ADMIN views pending approvals =====
//
//    // Simple endpoint to list all PENDING approvals (optionally filter by plan)
//    @GetMapping("/pending")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public List<MembershipDTO> listPending(@RequestParam(required = false) MembershipType type) {
//        return (type == null)
//                ? membershipService.listByStatus(MembershipStatus.PENDING_APPROVAL)
//                : membershipService.listByStatusAndType(MembershipStatus.PENDING_APPROVAL, type);
//    }
//
//    // Optional: generic list by status (e.g., PENDING_APPROVAL / APPROVED / REJECTED)
//    @GetMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public List<MembershipDTO> listByStatus(@RequestParam MembershipStatus status,
//                                            @RequestParam(required = false) MembershipType type) {
//        return (type == null)
//                ? membershipService.listByStatus(status)
//                : membershipService.listByStatusAndType(status, type);
//    }
//}
package com.hcl.user.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.hcl.user.dto.MembershipDTO;
import com.hcl.user.entity.MembershipStatus;
import com.hcl.user.entity.MembershipType;
import com.hcl.user.serviceImpl.MembershipService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/memberships")
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    // OWNER applies for membership
    @PostMapping("/apply")
    @PreAuthorize("hasAuthority('OWNER')") // <-- replaced hasRole with hasAuthority
    public MembershipDTO apply(@RequestParam String ownerUserId,
                               @RequestParam MembershipType type) {
        return membershipService.apply(ownerUserId, type);
    }

    // ADMIN approves (admin derived from Authentication; id in path)
    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('ADMIN')") // <-- replaced hasRole with hasAuthority
    public MembershipDTO approve(@PathVariable Long id,
                                 Authentication authentication,
                                 @RequestParam(required = false) String remarks,
                                 @RequestParam(required = false)
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                 @RequestParam(required = false)
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        String adminUserId = extractUserId(authentication);
        return membershipService.approve(id, adminUserId, remarks, start, end);
    }

    // ADMIN rejects (admin derived from Authentication; id in path)
    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasAuthority('ADMIN')") // <-- replaced hasRole with hasAuthority
    public MembershipDTO reject(@PathVariable Long id,
                                Authentication authentication,
                                @RequestParam(required = false) String remarks) {

        String adminUserId = extractUserId(authentication);
        return membershipService.reject(id, adminUserId, remarks);
    }

    // Active membership lookup for Owner/Event service
    @GetMapping("/active/{ownerUserId}")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')") // <-- replaced hasAnyRole with hasAnyAuthority
    public MembershipDTO getActive(@PathVariable String ownerUserId) {
        return membershipService.getActiveMembership(ownerUserId);
    }

    // ===== ADMIN: view pending approvals (optionally filter by plan) =====
    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('ADMIN')") // <-- replaced hasRole with hasAuthority
    public List<MembershipDTO> listPending(@RequestParam(required = false) MembershipType type) {
        return (type == null)
                ? membershipService.listByStatus(MembershipStatus.PENDING_APPROVAL)
                : membershipService.listByStatusAndType(MembershipStatus.PENDING_APPROVAL, type);
    }

    // Admin: generic list by status (optional filter by plan)
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')") // <-- replaced hasRole with hasAuthority
    public List<MembershipDTO> listByStatus(@RequestParam MembershipStatus status,
                                            @RequestParam(required = false) MembershipType type) {
        return (type == null)
                ? membershipService.listByStatus(status)
                : membershipService.listByStatusAndType(status, type);
    }

    /**
     * Extract current user id from Authentication.
     * Adjust this to your principal/JWT mapping.
     */
    private String extractUserId(Authentication authentication) {
        // If your Authentication's username is the userId:
        return authentication.getName();

        // If you have a custom principal:
        // return ((YourPrincipalType) authentication.getPrincipal()).getUserId();

        // Or parse JWT claims via a JwtUtil if needed.
    }
}

