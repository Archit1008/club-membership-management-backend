package com.hcl.guvi.capstonproject.eventmanagement.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {


       private String message;
        private String details;
        private LocalDateTime timestamp;

        


}
