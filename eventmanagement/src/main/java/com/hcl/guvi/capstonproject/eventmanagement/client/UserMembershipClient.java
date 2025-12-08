
package com.hcl.guvi.capstonproject.eventmanagement.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.hcl.guvi.capstonproject.eventmanagement.dto.MembershipDTO;


@FeignClient(
    name = "user-service",
    url = "${user.service.url:http://localhost:8989}",
    configuration = FeignAuthForwardingConfig.class
)
public interface UserMembershipClient {
    @GetMapping("/api/memberships/active/{username}")
    MembershipDTO getActiveMembership(@PathVariable String username);
}
