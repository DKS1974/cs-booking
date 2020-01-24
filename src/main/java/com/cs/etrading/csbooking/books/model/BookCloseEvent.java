package com.cs.etrading.csbooking.books.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCloseEvent {

    @JsonProperty(required = true)
    @NotNull
    @Positive
    private Long bookId ;

    @JsonProperty(required = true)
    @NotNull
    @Positive
    private BigDecimal price ;

    @JsonProperty(required = true)
    @NotNull
    @Positive
    private Integer quantity ;
}
