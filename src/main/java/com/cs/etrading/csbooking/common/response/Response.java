package com.cs.etrading.csbooking.common.response;


import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response {
    private String transactionId ;
    private ArrayList data ;
    private HttpStatus status ;
    private String errorMessage ;
    private Throwable exception ;
}
