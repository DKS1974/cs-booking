package com.cs.etrading.csbooking.orders.model;

import com.cs.etrading.csbooking.books.model.Book;
import com.cs.etrading.csbooking.orders.model.validator.PriceNotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name ="\"ORDER\"")
@JsonIgnoreProperties(value ={"hibernateLazyInitializer"},ignoreUnknown = true)
@PriceNotNull
public class Order {

    @Id
    @GeneratedValue
    @Column(name =  "ORDER_ID")
    @JsonProperty
    private Long id ;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="BOOK_ID")
    private Book book ;

    @Column(name =  "QUANTITY",  nullable=false )
    @JsonProperty(required = true)
    @NotNull
    @Positive
    private Integer quantity ;

    @Column(name =  "TYPE",  nullable=false )
    @JsonProperty(required = true)
    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderType type ;

    @Column(name =  "PRICE" )
    @JsonProperty
    @Positive
    private BigDecimal price ;
}
