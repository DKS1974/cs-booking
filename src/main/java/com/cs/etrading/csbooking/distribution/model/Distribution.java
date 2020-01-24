package com.cs.etrading.csbooking.distribution.model;

import com.cs.etrading.csbooking.orders.model.Order;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name ="DISTRIBUTION")
@JsonIgnoreProperties(value ={"hibernateLazyInitializer"},ignoreUnknown = true)
public class Distribution {

    @Id
    @GeneratedValue
    @Column(name =  "DISTRIBUTION_ID")
    @JsonProperty
    private Long id ;

    @Column(name = "ORDER_STATUS")
    @NotNull
    @JsonProperty
    private OrderStatus status ;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORDER_ID")
    @JsonProperty
    private Order order ;

    @Column(name = "QUANTITY")
    @JsonProperty
    @NotNull
    @PositiveOrZero
    private Integer quantity ;

    @Column(name = "PRICE")
    @JsonProperty
    @NotNull
    @PositiveOrZero
    private BigDecimal price;
}
