package com.hcl.guvi.capstonproject.eventmanagement.config;




import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "membership.policy")
@Getter @Setter
public class MembershipPolicyProperties {
    private int silverMaxActiveEvents = 3;
    private int goldMaxActiveEvents = 1000; // effectively unlimited
}
