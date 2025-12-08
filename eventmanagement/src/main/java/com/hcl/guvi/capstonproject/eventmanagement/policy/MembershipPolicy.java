
package com.hcl.guvi.capstonproject.eventmanagement.policy;

import com.hcl.guvi.capstonproject.eventmanagement.service.MembershipPlan;

public final class MembershipPolicy {

    public static int maxEventsPerMonth(MembershipPlan plan) {
        if (plan == null) {
            throw new IllegalArgumentException("Membership plan cannot be null");
        }

        int result;
        switch (plan) {
            case SILVER:
                result = 5;
                break;
            case GOLD:
                result = 20;
                break;
            default:
                throw new IllegalArgumentException("Unknown membership plan: " + plan);
        }
        return result;
    }
}
