package com.driver.ms.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Journey entity
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "t_journey")
public class Journey extends IdEntity {

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column
    private String city;

    @Column
    private String address;

    @Column
    private Integer nbrOfPlaces;

    @Column
    private LocalDateTime startAt;

    @Column
    private LocalDateTime endAt;
}
