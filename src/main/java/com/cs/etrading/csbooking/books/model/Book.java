package com.cs.etrading.csbooking.books.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table (name ="BOOK")
@JsonIgnoreProperties(value ={"hibernateLazyInitializer"},ignoreUnknown = true)
public class Book {

    @Id
    @GeneratedValue
    @Column(name =  "BOOK_ID")
    @JsonProperty
    @Builder.Default
    private Long bookId  = null ;

    @Column(name =  "BOOK_NAME", unique=true, nullable=false, length=100, updatable = false)
    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    @Builder.Default
    private String bookName = null ;

    @Column(name = "OPENED_DT" , updatable = false)
    @JsonProperty(value = "opened_date", required = false)
    @Builder.Default
    private Date openedDt = null;

    @Column(name = "CLOSED_DT", insertable = false)
    @JsonProperty("closed_date")
    @Builder.Default
    private Date closedDt = null ;

    @Column(name ="ALLOCATED" , insertable = false, columnDefinition="BOOLEAN DEFAULT false")
    @JsonProperty
    @Builder.Default
    private Boolean allocated = null ;

    @PrePersist
    public void setCreateTimeStamp() {
        this.setOpenedDt(new Date());
    }

}
